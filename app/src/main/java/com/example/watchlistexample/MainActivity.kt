package com.example.watchlistexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watchlistexample.ui.viewmodel.ForexWatchlistViewModel
import com.example.watchlistexample.ui.theme.WatchlistExampleTheme
import com.example.watchlistexample.ui.view.PortfolioScreen
import com.example.watchlistexample.ui.view.WatchlistScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: ForexWatchlistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            vm.equityFlow.collectLatest {
                Log.d("MainActivity", "Equity: $it")
            }
        }
        vm.updateFxPair(listOf("USDEUR","USDGBP","USDAUD","USDNZD","USDCAD","USDCHF","USDJPY"))
        setContent {
            MainScreen()
        }
    }
    
    override fun onPause() {
        super.onPause()
        vm.onPause()
    }

    override fun onResume() {
        super.onResume()
        vm.onResume()
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
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    NavHost(modifier = modifier, navController = navController, startDestination = "watchlist") {
        composable("watchlist") {
            // provide the viewModelStoreOwner to the WatchlistScreen so that it can reuse the same viewmodel
            CompositionLocalProvider(
                LocalViewModelStoreOwner provides viewModelStoreOwner
            ) {
                WatchlistScreen()
            }
        }
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
        backgroundColor = Color(0xFF274191),
        contentColor = Color(0xFF0694B8)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(text = item.title,
                        color =
                        when (currentRoute) {
                            item.screenRoute -> Color.White
                            else -> Color(0xFF0694B8)
                        },
                        fontSize = 9.sp)
                },
                selectedContentColor = Color(0xFF0694B8),
                unselectedContentColor = Color(0xFF0694B8),
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
