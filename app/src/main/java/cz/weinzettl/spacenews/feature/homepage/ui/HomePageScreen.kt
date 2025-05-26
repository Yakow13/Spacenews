@file:OptIn(ExperimentalMaterial3Api::class)

package cz.weinzettl.spacenews.feature.homepage.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import cz.weinzettl.spacenews.R
import cz.weinzettl.spacenews.feature.article.model.Article
import cz.weinzettl.spacenews.feature.homepage.presentation.HomePageViewModel
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiEvent
import cz.weinzettl.spacenews.feature.homepage.presentation.model.HomePageUiState
import cz.weinzettl.spacenews.sdk.theme.ui.SpaceNewsTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomePageScreen(
    onArticleClick: (Int, Boolean) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val viewmodel = koinViewModel<HomePageViewModel>()
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val isEnhancedOn = uiState.isEnhancedDesignOn

    HomePageContent(
        uiState = uiState,
        isEnhanced = isEnhancedOn,
        onArticleClick = {
            onArticleClick(it, isEnhancedOn)
        },
        onEnhancedChange = {
            viewmodel.setEnhancedDesignEnabled(it)
        }
    )

    LaunchedEffect(Unit) {
        viewmodel.uiEvent.collect { event ->
            when (event) {
                is HomePageUiEvent.Error -> {
                    snackbarHostState.showSnackbar(message = context.getString(event.messageRes))
                }
            }
        }
    }
}

@Composable
fun HomePageContent(
    uiState: HomePageUiState,
    isEnhanced: Boolean,
    onArticleClick: (Int) -> Unit,
    onEnhancedChange: (Boolean) -> Unit,
) {
    val isRefreshing = uiState is HomePageUiState.Loading

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HomeTopAppBar(isEnhanced, onEnhancedChange) },
    ) { paddingValues ->
        when (val state = uiState) {
            HomePageUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomePageUiState.Idle -> {
                val articles = state.articles.collectAsLazyPagingItems()
                PullToRefreshBox(
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        articles.refresh()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn {
                        items(
                            count = articles.itemCount,
                            key = articles.itemKey { it.id },
                            contentType = articles.itemContentType { "article" }
                        ) { index ->
                            val article = articles[index]
                            if (article != null) {
                                ArticleItem(article = article) {
                                    onArticleClick(article.id)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ArticleItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imagePainter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.imageUrl)
                    .crossfade(true)
                    .build()
            )
            Image(
                painter = imagePainter,
                contentDescription = "Article Image",
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .size(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            VerticalDivider(
                modifier = Modifier
                    .width(1.dp)
                    .padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = article.title, style = SpaceNewsTheme.typography.titleMedium)
                Text(
                    text = article.summary,
                    style = SpaceNewsTheme.typography.bodyMedium,
                    maxLines = 3
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    isEnhanced: Boolean,
    onEnhancedChange: (Boolean) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = stringResource(R.string.settings_content_description)
                )
            }
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Show Enhanced Design")
                            Spacer(Modifier.weight(1f))
                            Checkbox(
                                checked = isEnhanced,
                                onCheckedChange = {
                                    onEnhancedChange(it)
                                }
                            )
                        }
                    },
                    onClick = {
                        onEnhancedChange(!isEnhanced)
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomePageContent(
        uiState = HomePageUiState.Loading,
        isEnhanced = false,
        onArticleClick = { },
        onEnhancedChange = { }
    )
}