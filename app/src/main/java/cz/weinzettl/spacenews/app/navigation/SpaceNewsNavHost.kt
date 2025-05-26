package cz.weinzettl.spacenews.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.weinzettl.spacenews.feature.detail.ui.DetailScreen
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
                onArticleClick = { id, isEnhancedOn ->
                    if (isEnhancedOn) {
                        navController.navigate(
                            Destination.DetailV2(id),
                        )
                    } else {
                        navController.navigate(
                            Destination.Detail(id),
                        )
                    }

                }
            )
        }

        composable<Destination.Detail> {
            DetailScreen {
                navController.navigateUp()
            }
        }

        composable<Destination.DetailV2> {
            DetailV2Screen {
                navController.navigateUp()
            }
        }
    }
}
