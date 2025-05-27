@file:OptIn(ExperimentalMaterial3Api::class)

package cz.weinzettl.spacenews.feature.detailv2.ui

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import cz.weinzettl.spacenews.R
import cz.weinzettl.spacenews.feature.article.domain.model.ArticleDetailV2
import cz.weinzettl.spacenews.feature.article.domain.model.Author
import cz.weinzettl.spacenews.feature.detailv2.presentation.DetailV2ViewModel
import cz.weinzettl.spacenews.feature.detailv2.presentation.model.DetailV2UiState
import cz.weinzettl.spacenews.sdk.theme.SpaceNewsTheme
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


/**
 * Composable function for displaying the detail screen of a space news article.
 * This screen provides an enhanced user experience for viewing article details,
 * building upon the original detail screen functionality.
 * The big-picture idea and then the design has been done using Gemini 2.5 Flash
 *
 * The prompt:
 * This is the sample of detail data. I displayed that in Webview but I don't like the UX. How to improve?
 * { "id": 31315, "title": "China to launch Tianwen-2 asteroid sampling mission on May 28", "authors": [ { "name": "Andrew Jones", "socials": null }
 * ], "url": "https://spacenews.com/china-to-launch-tianwen-2-asteroid-sampling-mission-on-may-28/", "image_url": "https://i0.wp.com/spacenews.com/wp-content/uploads/2020/08/ZhengHe-probe-2019-CNSA.jpg?fit=1024%2C686&quality=89&ssl=1",
 * "news_site": "SpaceNews", "summary": "HELSINKI — China is set to launch its second Tianwen deep space exploration mission late May, targeting both a near Earth asteroid and a main belt comet. The Tianwen-2 spacecraft […]\r\nThe post China to launch Tianwen-2 asteroid sampling mission on May 28 appeared first on SpaceNews.",
 * "published_at": "2025-05-19T12:00:46Z", "updated_at": "2025-05-20T06:32:25.625683Z", "featured": false, "launches": [ { "launch_id": "66be38aa-d9d9-4b71-8522-45e06677b2e0", "provider": "Launch Library 2" }
 * ], "events": []}
 *
 * It utilizes [DetailV2ViewModel] to manage its state and interactions.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailV2Screen(
    onNavigateUp: () -> Unit,
) {
    val viewModel = koinViewModel<DetailV2ViewModel>()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DetailV2TopAppBar(onNavigateUp)
            {
                uiState.articleDetail?.let { article ->
                    val shareIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, article.url)
                        type = "text/plain"
                    }
                    val title = context.getString(R.string.share_article_via)
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            title
                        )
                    )
                }

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            when (val state = uiState) {
                DetailV2UiState.Empty -> {
                    Text(stringResource(R.string.empty_placeholder))
                }

                is DetailV2UiState.Idle -> {
                    DetailV2Content(state.articleDetail)
                }

                DetailV2UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailV2Content(article: ArticleDetailV2) {
    val context = LocalContext.current

    article.imageUrl?.let { imageUrl ->
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            //FIXME all content desc
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(MaterialTheme.shapes.medium)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Crop
        )
    }

    Text(
        text = article.title,
        style = SpaceNewsTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    if (article.authors.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Author icon",
                modifier = Modifier.size(20.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = article.authors.joinToString(", ") { it.name },
                style = SpaceNewsTheme.typography.bodyMedium,
            )
        }
        Spacer(Modifier.height(8.dp))
    }

    val publishedDateTime = remember(article.publishedAt) {
        ZonedDateTime.parse(article.publishedAt)
    }
    val formatter =
        remember { DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM) }
    Text(
        text = "${article.newsSite} • ${publishedDateTime.format(formatter)}",
        style = SpaceNewsTheme.typography.bodySmall,
        modifier = Modifier.padding(bottom = 16.dp)
    )

    val cleanSummary = article.summary.substringBefore("The post ").trim()
    Text(
        text = cleanSummary,
        style = SpaceNewsTheme.typography.bodyLarge,
        modifier = Modifier.padding(bottom = 24.dp)
    )

    Button(
        onClick = {
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(context, article.url.toUri())
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "Open article in browser tab",
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text("Read Full Article on ${article.newsSite}")
    }
}

@Composable
private fun DetailV2TopAppBar(onNavigateUp: () -> Unit, onShareClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.Bold,
                color = SpaceNewsTheme.color.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = onShareClick) {
                Icon(Icons.Default.Share, contentDescription = "Share article")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ArticleDetailScreenPreview() {
    SpaceNewsTheme {
        DetailV2Content(
            article = ArticleDetailV2(
                title = "Ingenuity helicopter completes 50th Red Planet flight",
                url = "https://spaceflightnow.com/2023/04/16/ingenuity-helicopter-completes-50th-red-planet-flight/",
                imageUrl = "https://spaceflightnow.com/wp-content/uploads/2023/04/52814838638_c635293290_k.jpg",
                newsSite = "Spaceflight Now",
                summary = "NASA’s Ingenuity helicopter flew for the 50th time on Mars Thursday, covering a distance of more than 1,000 feet and setting a new altitude record of nearly 60 feet. The small rotorcraft has exceeded all expectations, operating on Mars for more than two years after arriving with the Perseverance rover in February 2021.\n\nThe post Ingenuity helicopter completes 50th Red Planet flight appeared first on Spaceflight Now.",
                publishedAt = "2023-04-16T19:00:46Z",
                authors = listOf(
                    Author(
                        name = "Stephen Clark"
                    )
                )
            )
        )
    }
}

