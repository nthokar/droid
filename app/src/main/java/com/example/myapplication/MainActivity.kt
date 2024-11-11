package com.example.myapplication

import MoviesMenuScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.screens.FavoriteScreen
import com.example.myapplication.screens.HomeScreen
import com.example.myapplication.screens.MovieDetailScreen
import com.example.myapplication.screens.MovieDetailViewModel
import com.example.myapplication.screens.MoviesViewModel
import com.example.myapplication.services.imp.MockMovieFinder
import com.example.myapplication.ui.theme.MyApplicationTheme

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int?
)


class MainActivity : ComponentActivity() {

    val items = listOf(
        BottomNavigationItem(
        "menu",
        Icons.Filled.Menu,
        Icons.Outlined.Menu,
            false,
            null),
        BottomNavigationItem(
            "home",
            Icons.Filled.Home,
            Icons.Outlined.Home,
            false,
            null),
        //
        BottomNavigationItem(
            "favorite",
            Icons.Filled.Favorite,
            Icons.Outlined.Favorite,
            false,
            null),
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val navigationController = rememberNavController()
                var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
                val viewModel: MoviesViewModel by viewModels()

                val movieFinder = MockMovieFinder()
                val movieDetailsViewModel: MovieDetailViewModel by viewModels()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar =  {
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navigationController.navigate(item.title)
                                        },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if (item.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon,
                                                contentDescription = item.title
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController = navigationController, startDestination = items[0].title, modifier = Modifier.padding(innerPadding)) {
                        //Список всех фильмов

                        composable(items[0].title ) { MoviesMenuScreen(navigationController, viewModel) }

                        //Лента рекомендаций
                        composable(items[1].title ) { HomeScreen() }

                        //Отложенные
                        composable(items[2].title ) { FavoriteScreen() }

                        composable(
                            route = "movie_detail/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getInt("id")
                            if (movieId != null) {

                                movieDetailsViewModel.movie = movieFinder.findMovie(movieId)

                                MovieDetailScreen(movieDetailsViewModel, navigationController)
                            } else {
                                Text(text = "Ошибка загрузки фильма", style = MaterialTheme.typography.headlineLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}