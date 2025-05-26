package cz.weinzettl.spacenews.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.weinzettl.spacenews.app.navigation.SpaceNewsNavHost
import cz.weinzettl.spacenews.sdk.theme.SpaceNewsTheme

class SpaceNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpaceNewsTheme {
                SpaceNewsNavHost()
            }
        }
    }
}