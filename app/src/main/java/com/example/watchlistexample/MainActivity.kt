package com.example.watchlistexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watchlistexample.domain.ForexWatchlistUseCase
import com.example.watchlistexample.ui.theme.WatchlistExampleTheme
import com.example.watchlistexample.ui.view.PortfolioScreen
import com.example.watchlistexample.ui.view.WatchlistScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var forexWatchlistUseCase: ForexWatchlistUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            val result = forexWatchlistUseCase.getForex(listOf("EURUSD", "GBPUSD"))
            Log.e("MainActivity", "result: $result")
        }
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    WatchlistExampleTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Watchlist") },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    })
            },
            bottomBar = {
                BottomNavigation(navController = navController)
            },
            content = { padding ->
                NavigationGraph(Modifier.padding(padding), navController)
            }
        )
    }
}

@Composable
fun NavigationGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    NavHost(modifier = modifier, navController = navController, startDestination = "watchlist") {
        composable("watchlist") { WatchlistScreen() }
        composable("portfolio") { PortfolioScreen() }
        composable("tab3") { Text(text = "TODO") }
        composable("setting") { Text(text = "TODO") }

    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem("Market", Icons.Default.AreaChart, "market", "watchlist"),
        BottomNavItem("Market", Icons.Default.AccountBalanceWallet, "market", "portfolio"),
        BottomNavItem("Market", Icons.Default.ChatBubbleOutline, "market", "tab3"),
        BottomNavItem("Market", Icons.Default.AccountCircle, "market", "setting"),
    )
    BottomNavigation(
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(text = item.title,
                        fontSize = 9.sp)
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val contentDescription: String, val screenRoute: String)

@Preview
@Composable
fun HomeScreenPreview() {
    MainScreen()
}
