package com.example.ministate.presentation.compose_clone_of_mystate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ministate.presentation.common.EventViewModel
import com.example.ministate.presentation.compose_clone_of_mystate.event_list_clone_screen.EventListCloneScreen
import com.example.ministate.presentation.ui.theme.MiniStateTheme

class MainActivityClone : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = EventViewModel(application = application)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniStateTheme {
                Navigation(viewModel)
                }
            }
        }

    @Composable
    private fun Navigation(viewModel: EventViewModel) {
        val navController = rememberNavController()
        NavHost(
                navController = navController,
                startDestination = "event_list_clone_screen"
            ){
            composable("event_list_clone_screen") {
                EventListCloneScreen(events = viewModel.state.collectAsState().value.eventList)
            }
        }
    }
}
