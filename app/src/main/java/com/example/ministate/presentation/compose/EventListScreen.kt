package com.example.ministate.presentation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ministate.data.local.realm.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    event: List<Event>?,
    categoryId: String,
    onClick: (String) -> Unit,
    onRefresh: (MutableState<Boolean>) -> Unit,
    getCategory: (String) -> String
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = getCategory(categoryId)) })
        }
    ) { paddingValues ->

        val refreshState = rememberPullToRefreshState()
        val isRefreshing = remember { mutableStateOf(false) }

        PullToRefreshBox(
            isRefreshing = isRefreshing.value,
            onRefresh = { onRefresh(isRefreshing) },
            state = refreshState,
        ) {

            if(isRefreshing.value){
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.Start) {
                    Text("loading")
                }
            }
            else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(8.dp)
                ) {
                    event?.let {
                        items(it) { event ->
                            if (event.category == categoryId)
                                EventCard(event, onClick)
                            }
                        }
                    }
                }
            }
        }
    }

@Composable
fun EventCard(event: Event, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(event.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(12.dp))
                .padding(16.dp)

        ) {
            Text(
                text=event.subject,
                style = MaterialTheme.typography.titleLarge
                )
            Spacer(Modifier.height(4.dp))

            Text(
                text = event.location,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))

            Text(
                text = event.shortDesc,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )


        }
    }
}

