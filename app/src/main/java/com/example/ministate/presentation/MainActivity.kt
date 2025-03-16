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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ministate.presentation.ui.theme.MiniStateTheme

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
                            .fillMaxSize()
                            .padding(innerPadding)
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
        NavHost(navController = navController, startDestination = "event_category_screen") {
            composable("event_category_screen") {
                EventCatagoryScreen(
                    onClick = {category -> navController.navigate("events/${category}")},
                    categories = viewModel.state.collectAsState().value.eventCategories,
                    onRefresh = viewModel::onRefresh
                )
            }
            composable("events/{category}") { it ->
                val category = it.arguments?.getString("category")?: "no category"

                EventListScreen(
                    event = viewModel.state.collectAsState().value.eventList,
                    categoryId = category,
                    onClick = { it -> navController.navigate("events/${category}/${it}") },
                    onRefresh = viewModel::onRefreshEvents,
                    getCategory = viewModel::getEventCategoryById
                    )
            }

            composable("events/{category}/{event_id}") {it->
                println("inside event/cat/id")
                val eventId = it.arguments?.getString("event_id")
                val event = eventId?.let { it1 -> viewModel.getEventById(it1) }
                println("before calling event screen : event = $event")

                event?.let {
                    EventScreen(it)
                }
            }
        }
    }
}

