package cz.weinzettl.spacenews.sdk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.weinzettl.spacenews.feature.detail.ui.DetailScreen
import cz.weinzettl.spacenews.feature.homepage.ui.HomePageScreen

@Composable
fun SpaceNewsNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Destination.Home
    ) {
        composable<Destination.Home> {
            HomePageScreen(
                onArticleClick = {
                    navController.navigate(
                        Destination.Detail(it),
                    )
                }
            )
        }

        composable<Destination.Detail> { backStackEntry ->
            val detailDestination: Destination.Detail = backStackEntry.toRoute()
            DetailScreen(detailDestination.articleId)
        }
    }
}
