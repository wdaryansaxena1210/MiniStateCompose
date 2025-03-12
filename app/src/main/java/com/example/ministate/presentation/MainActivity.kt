package com.example.ministate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ministate.common.TestFunctions
import com.example.ministate.presentation.ui.theme.MiniStateTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val testFunctions = TestFunctions(this)

        CoroutineScope(Dispatchers.IO).launch {
//            testFunctions.testEventRepositoryGetEventCatagories()
            testFunctions.testEventRepositoryGetEventDetailsList()

        }


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

                    }
                }
            }
        }
    }
}

