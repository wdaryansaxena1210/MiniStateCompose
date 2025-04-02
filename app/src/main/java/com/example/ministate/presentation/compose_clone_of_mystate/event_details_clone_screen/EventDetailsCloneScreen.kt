package com.example.ministate.presentation.compose_clone_of_mystate.event_details_clone_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ministate.data.local.realm.Event
import java.io.InvalidObjectException

@Composable
fun EventDetailsCloneScreen(
    modifier: Modifier = Modifier,
    id : String?,
    getEventById : (id:String) -> Event?,
    popBackStack : () -> Unit
) {

    if(id==null){
        throw InvalidObjectException("id does not exist")
    }

    val event = getEventById(id)
        ?: throw InvalidObjectException("id was not null but event with that id does not exist")


    Scaffold(
        topBar = { TopBarComposable() }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            TitleRow(
                modifier = Modifier.padding( horizontal = 8.dp, vertical = 8.dp),
                event = event
            )

            WhenRow(event)

            WhereRow(event)

            EventDescriptionRow(event)
        }
    }
}

@Composable
fun EventDescriptionRow(event: Event) {
    Text(
        text = "Event Description",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(horizontal = 32.dp, vertical = 8.dp)
    )
    Text(
        text = event.shortDesc,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun WhereRow(event: Event) {
    Column {
        Text(
            text = "Where",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
        Text(
            text = event.location,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun WhenRow(event: Event) {
    Column {

        Text(
            text= "When",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(horizontal = 32.dp, vertical = 8.dp)
        )
        Text(
            text = event.eventDate,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

//        println("rendering text ${event.eventDate}")

    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TitleRow(modifier: Modifier = Modifier, event : Event) {
    Text(
        text = event.subject,
        style = MaterialTheme.typography.displaySmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
        )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComposable(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {Text("")},

        navigationIcon = {
            IconButton(onClick = { /* Handle calendar click */ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Calendar"
                )
            }
        },

        actions = { Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "i icon"
        ) },

        modifier = Modifier.border(width = 1.dp, color = Color.DarkGray)
    )
}