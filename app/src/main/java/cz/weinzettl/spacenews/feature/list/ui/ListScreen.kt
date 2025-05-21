package cz.weinzettl.spacenews.feature.list.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.weinzettl.spacenews.feature.list.presentation.ListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen() {
    val viewmodel = koinViewModel<ListViewModel>()

    LazyColumn(Modifier.fillMaxSize()) {

    }
}