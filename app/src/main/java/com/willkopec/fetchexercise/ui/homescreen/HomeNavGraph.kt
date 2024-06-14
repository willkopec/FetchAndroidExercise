package com.willkopec.fetchexercise.ui.homescreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.willkopec.fetchexercise.ui.listscreen.ProductListScreen

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
){
    NavHost(
        navController = navController,
        route = "homenavgraph",
        startDestination = "homescreen"
    ) {

        composable(route = "homescreen") {
            HomeScreen(navController, viewModel)
        }

        composable(route = "listscreen") {
            ProductListScreen(navController, viewModel)
        }

    }

}