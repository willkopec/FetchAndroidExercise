package com.willkopec.fetchexercise.ui.listscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.willkopec.fetchexercise.ui.homescreen.MainViewModel

@Composable
fun ProductListScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {

    val scrollState = rememberLazyListState()
    val loadError by viewModel.loadError.collectAsState()
    val productList by viewModel.currentProductListData.collectAsState()

    Scaffold(
        topBar = {
            BackButtonTopBar(navController = navController)
        }
    ){scaffoldPadding->



        Box(
            modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding)
            .consumeWindowInsets(scaffoldPadding)
            .systemBarsPadding(),
            contentAlignment = Alignment.Center
        )
        {
            if(loadError == ""){

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.weight(1f)
                    ) {
                        val itemCount = productList.size

                        items(itemCount) {
                            val currentProductInfo = productList.get(it)

                            if(currentProductInfo.name != "" && currentProductInfo.name != "null" && currentProductInfo.name != null){
                                ProductListItem(
                                    navController,
                                    currentProductInfo
                                )
                            }

                        }
                    }

                }

            } else {

            }
        }
        }



}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackButtonTopBar(navController: NavHostController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(text = "Fetch Product List")
        }
    )
}