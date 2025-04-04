package com.example.ministate.presentation.compose_clone_of_mystate

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ministate.presentation.common.EventViewModel
import com.example.ministate.presentation.compose_clone_of_mystate.event_details_clone_screen.EventDetailsCloneScreen
import com.example.ministate.presentation.compose_clone_of_mystate.event_list_clone_screen.EventListCloneScreen
import com.example.ministate.presentation.ui.theme.MiniStateTheme

class MainActivityClone : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    private fun Navigation(viewModel: EventViewModel) {
//        println(viewModel.state.collectAsState().value.eventList?.get(0)?.id)
        val navController = rememberNavController()
        NavHost(
                navController = navController,
                startDestination = "event_list_clone_screen"
            ){
            composable("event_list_clone_screen") {
                EventListCloneScreen(
                    eventList = viewModel.state.collectAsState().value.eventList,
                    eventCategories = viewModel.state.collectAsState().value.eventCategories,
                    onEventClick = navController::navigate,
                )
            }
            composable("event_details_clone_screen/{id}") {
                val id = it.arguments?.getString("id")
                EventDetailsCloneScreen(
                    id = id,
                    getEventById = viewModel::getEventById,
                    popBackStack = { navController.popBackStack() },
                    )
            }
        }
    }
}
