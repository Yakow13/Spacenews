package cz.weinzettl.spacenews.feature.list.ui

import androidx.compose.runtime.Composable
import cz.weinzettl.spacenews.feature.list.presentation.ListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen() {
    val viewmodel = koinViewModel<ListViewModel>()


//    Scaffold(modifier = Modifier.fillMaxSize()) {
//
//    }
}