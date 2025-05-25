package cz.weinzettl.spacenews.sdk.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.weinzettl.spacenews.feature.detailv2.ui.DetailV2Screen
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
                onArticleClick = { id ->
                    navController.navigate(
                        Destination.Detail(id),
                    )
                }
            )
        }

        composable<Destination.Detail> {
            DetailV2Screen()
        }
    }
}
