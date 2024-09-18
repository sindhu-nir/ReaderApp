package com.elahee.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.elahee.readerapp.JetReraderApp
import com.elahee.readerapp.screens.ReaderSplashScreen
import com.elahee.readerapp.screens.details.BookDetailScreen
import com.elahee.readerapp.screens.home.HomeScreen
import com.elahee.readerapp.screens.home.HomeScreenViewModel
import com.elahee.readerapp.screens.login.LoginScreen
import com.elahee.readerapp.screens.search.BooksSearchViewModel
import com.elahee.readerapp.screens.search.SearchScreen
import com.elahee.readerapp.screens.stats.StatsScreen
import com.elahee.readerapp.screens.update.UpdateScreen

@Composable
fun ReaderNavigation(navController: NavHostController,
                     onBottomBarVisibilityChanged: (Boolean) -> Unit={}) {
    //val navController: NavHostController  = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name ){

        composable(ReaderScreens.SplashScreen.name) {
            onBottomBarVisibilityChanged(false)
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            onBottomBarVisibilityChanged(false)
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.JetReraderApp.name) {
            onBottomBarVisibilityChanged(true)
            JetReraderApp(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            onBottomBarVisibilityChanged(true)
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            StatsScreen(navController = navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name) {
            onBottomBarVisibilityChanged(true)
            val homeViewModel = hiltViewModel<HomeScreenViewModel>()
            HomeScreen(navController = navController, viewModel = homeViewModel)
        }

        composable(ReaderScreens.SearchScreen.name) {
            onBottomBarVisibilityChanged(true)
            val searchViewModel = hiltViewModel<BooksSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }



        val detailName = ReaderScreens.DetailScreen.name
        composable("$detailName/{bookId}", arguments = listOf(navArgument("bookId"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                onBottomBarVisibilityChanged(false)
                BookDetailScreen(navController = navController, bookId = it.toString())
            }
        }
//
        val updateName = ReaderScreens.UpdateScreen.name
        composable("$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("bookItemId").let {
                onBottomBarVisibilityChanged(false)
                UpdateScreen(navController = navController, bookItemId = it.toString())
            }

        }

    }

}