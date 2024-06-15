package com.willkopec.fetchexercise.ui.listscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.willkopec.fetchexercise.ui.homescreen.MainViewModel

@Composable
fun ProductListScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    // State variables
    var sortByName by remember { mutableStateOf(false) }
    var sortByID by remember { mutableStateOf(true) }

    val scrollState = rememberLazyListState()
    val loadError by viewModel.loadError.collectAsState()
    val productList by viewModel.currentProductListData.collectAsState()


    val arrowSortIndicator: ImageVector = Icons.Default.ArrowDropDown

    Scaffold(
        topBar = {
            BackButtonTopBar(navController = navController)
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .consumeWindowInsets(scaffoldPadding)
                .systemBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            if (loadError == "") {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Legend Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // List ID - Not clickable - Grouped by
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp),
                            text = "List ID",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Name
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    // Toggle sorting by Name
                                    sortByName = true
                                    sortByID = false
                                    // Call sorting function based on sortByName state
                                    viewModel.sortDataByIdAndNameAlphabetically()
                                },
                            text = "Name",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Name sorting indicator icon
                        if(sortByName){
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Sort Indicator",
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                        // ID
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    // Toggle sorting by ID
                                    sortByID = true
                                    sortByName = false // Reset sortByName
                                    // Call sorting function based on sortByID state
                                    viewModel.sortDataByIdAndNameNumerically()
                                },
                            text = "ID",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if(sortByID){
                            Icon(
                                imageVector = arrowSortIndicator,
                                contentDescription = "Sort Indicator",
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }

                    }

                    // Product List
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier.weight(1f)
                    ) {
                        items(productList.size) { index ->
                            val currentProductInfo = productList[index]

                            if (currentProductInfo.name != "" &&
                                currentProductInfo.name != "null" &&
                                currentProductInfo.name != null
                            ) {
                                ProductListItem(navController, currentProductInfo)
                            }
                        }
                    }
                }
            } else {
                RetrySection(error = loadError) {
                    viewModel.getFetchProductList()
                }
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

@Composable
fun RetrySection(error: String, onRetry: () -> Unit) {
    Column {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = error,
            color = Color.Red,
            fontSize = 18.sp,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { onRetry() }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}