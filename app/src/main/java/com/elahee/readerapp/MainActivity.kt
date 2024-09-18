package com.elahee.readerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.elahee.readerapp.components.ReaderScaffold
import com.elahee.readerapp.navigation.ReaderNavigation
import com.elahee.readerapp.navigation.ReaderScreens
import com.elahee.readerapp.ui.theme.ReaderAppTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MAINACTIVITY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReaderAppTheme {
                ReaderApp()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReaderApp() {
    val navController: NavHostController = rememberNavController()

    val currentRoute = navController?.currentDestination?.route
    var buttonsVisible by remember { mutableStateOf(true) }
    ReaderScaffold(
        bottomBar = {
            if (buttonsVisible) {
                BottomNavigation(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 2.dp,
                    backgroundColor = colorResource(id = R.color.teal_200))
                {
                    BottomNavigationBar_2(navController = navController,state = buttonsVisible)
                }
//                BottomBar(
//                    navController = navController,
//                    state = buttonsVisible,
//                    modifier = Modifier
//                )
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
fun GreetingPreview() {

}