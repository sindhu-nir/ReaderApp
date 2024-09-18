package com.elahee.readerapp

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.elahee.readerapp.components.ReaderScaffold
import com.elahee.readerapp.components.ReaderSurface
import com.elahee.readerapp.navigation.ReaderNavigation
import com.elahee.readerapp.navigation.ReaderScreens
import com.elahee.readerapp.screens.home.HomeScreen
import com.elahee.readerapp.ui.theme.ReaderAppTheme
import java.util.Locale

@Composable
fun JetReraderApp(navController: NavHostController) {

    ReaderAppTheme {
        MainContainer(
            navController,
            onSnackSelected = {}
        )

    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onSnackSelected: () -> Unit
) {

//    val sharedTransitionScope = LocalSharedTransitionScope.current
//        ?: throw IllegalStateException("No SharedElementScope found")
//    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
//        ?: throw IllegalStateException("No SharedElementScope found")
    val currentRoute = navController?.currentDestination?.route
    var buttonsVisible by remember { mutableStateOf(true) }

    ReaderScaffold(
        bottomBar = {
            if (buttonsVisible) {
                ReaderBottomBar(
                    navController = navController,
                    //   tabs = HomeSections.entries.toTypedArray(),
                    state = buttonsVisible,
                    currentRoute = currentRoute ?: ReaderScreens.ReaderHomeScreen.name,
                    navigateToRoute = {},
                    modifier = Modifier
//                        .animateEnterExit(
//                            enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
//                                spatialExpressiveSpring()
//                            ) {
//                                it
//                            },
//                            exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
//                                spatialExpressiveSpring()
//                            ) {
//                                it
//                            }
//                        )
                )
            }

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            ReaderNavigation(navController=navController) { isVisible ->
                buttonsVisible = isVisible
            }
        }

    }
}

@Composable
fun ReaderBottomBar(
    navController: NavController,
    // tabs: Array<HomeSections>,
    state: Boolean=true,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier
//    color: Color = ReaderAppTheme.colors.iconPrimary,
//    contentColor: Color = ReaderAppTheme.colors.iconInteractive
) {
//    val routes = remember { tabs.map { it.route } }
//    val currentSection = tabs.first { it.route == currentRoute }
    ReaderSurface(
        modifier = modifier
//        color = color,
//        contentColor = contentColor
    ) {
        val screens = listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Profile
        )

        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(item.label) },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray.copy(alpha = 0.4f)
                )
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(ReaderScreens.ReaderHomeScreen.name, Icons.Outlined.Home, "Home")
    object Search : BottomNavItem(ReaderScreens.SearchScreen.name, Icons.Outlined.Search, "Search")
    object Profile : BottomNavItem(ReaderScreens.ReaderStatsScreen.name, Icons.Outlined.Person, "Stats")
    object Test : BottomNavItem(ReaderScreens.ReaderStatsScreen.name, Icons.Outlined.Person, "Text")
}

enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FEED(R.string.home_feed, Icons.Outlined.Home, ReaderScreens.ReaderHomeScreen.name),
    SEARCH(R.string.home_search, Icons.Outlined.Search, ReaderScreens.SearchScreen.name),
    CART(R.string.home_cart, Icons.Outlined.ShoppingCart, ReaderScreens.DetailScreen.name),
    PROFILE(R.string.home_profile, Icons.Outlined.AccountCircle, ReaderScreens.UpdateScreen.name)
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }


@Composable
fun BottomBar(
    navController: NavHostController,
    state: Boolean,
    modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile,
        BottomNavItem.Test,

    )

    NavigationBar(
        modifier = modifier,
        containerColor = Color.LightGray,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            NavigationBarItem(
                label = {
                    Text(text = screen.label!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray,
                    selectedTextColor = Color.Black,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Black,
                    indicatorColor = Color.White
                ),
            )
        }
    }
}

@Composable
fun BottomNavigationBar_2(navController: NavController,state: Boolean) {
    //Navigation bar contents goes here. Follow content from step 4 to 8.
    val items  = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile,
        BottomNavItem.Test

    )
    val selectedItem = remember { mutableStateOf(0) }
   // val currentRoute = remember { mutableStateOf(BottomNavItem.Home.route) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White, modifier = Modifier.fillMaxWidth())
    {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            items.forEachIndexed { index, item ->

               // val isSelected = index == selectedItem.value
                val isSelected=currentRoute == item.route

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        // To show top bar on selected item
                        if (isSelected) {
                            Divider(
                                color = colorResource(id = R.color.teal_200),
                                thickness = 3.dp,
                                modifier = Modifier
                                    .width(48.dp)
                                    .align(Alignment.CenterHorizontally)
                            )
                        }

                        // For navigation bar
                        this@NavigationBar.BottomNavigationItem(
                            alwaysShowLabel = true,
                            icon = {
                                Image(
                                    imageVector= item.icon!!,
                                    contentDescription = item.label,
                                    modifier = Modifier
                                        .height(19.dp)
                                        .width(19.dp),
                                    colorFilter = if (isSelected) {
                                        ColorFilter.tint(colorResource(id = R.color.black))
                                    } else {
                                        ColorFilter.tint(colorResource(id = R.color.purple_200))
                                    }
                                )
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 14.sp,
                                    fontWeight = if (isSelected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                            },
                            selected = isSelected,
                            onClick = {
                                selectedItem.value = index
                               // currentRoute.value = item.route
                                navController.navigate(item.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selectedContentColor = colorResource(R.color.black),
                            unselectedContentColor = colorResource(R.color.white),
                        )

                    }
                    //Index check for not adding divider to the last item.
                    if (index != 2) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(verticalArrangement = Arrangement.SpaceEvenly) {
                            Divider(
                                color = colorResource(id = R.color.black).copy(alpha = 0.1f),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(2.dp)
                                    .padding(top = 10.dp, bottom = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
