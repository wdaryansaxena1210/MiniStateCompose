package com.example.ministate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ministate.common.TestFunctions
import com.example.ministate.data.remote.repository.EventRepositoryImpl
import com.example.ministate.presentation.ui.theme.MiniStateTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = EventViewModel(application = application)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniStateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Navigation(viewModel)
                    }
                }
            }
        }

//        val testFunctions = TestFunctions(this)
//
//        CoroutineScope(Dispatchers.Main).launch {
//            run any test functions here to confirm that api calls are working
//
//            testFunctions.testEventRepositoryGetEventCatagories()
//            testFunctions.testEventRepositoryGetEventDetailsList()
//
//        }
    }

    @Composable
    private fun Navigation(viewModel: EventViewModel) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "event_catagory_screen") {
            composable("event_catagory_screen") {
                EventCatagoryScreen(
                    onClick = { println("navigate to $it")},

                )
            }
        }
    }
}

