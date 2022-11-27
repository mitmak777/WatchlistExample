package com.example.watchlistexample

import android.os.Bundle
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watchlistexample.ui.theme.WatchlistExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen(){
    val navController = rememberNavController()
    WatchlistExampleTheme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            topBar = {
                TopAppBar( title = { Text(text = "Watchlist") },
                    // title with watchtlist and search icon at right

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
                Surface(modifier = Modifier.padding(padding), color = MaterialTheme.colors.background) {

                }
            }
        )
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem("Market", Icons.Default.AreaChart, "market", "market"),
        BottomNavItem("Market", Icons.Default.AccountBalanceWallet, "market" , "market2"),
        BottomNavItem("Market", Icons.Default.ChatBubbleOutline, "market", "market3"),
        BottomNavItem("Market", Icons.Default.AccountCircle, "market", "market4"),
    )
    BottomNavigation(
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
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

data class BottomNavItem(val title:String, val icon:ImageVector, val contentDescription: String, val screenRoute:String)

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}
