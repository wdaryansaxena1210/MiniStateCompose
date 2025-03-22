package com.example.ministate.presentation.compose_clone_of_mystate.event_list_clone_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ministate.data.local.realm.Event
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventListCloneScreen(modifier: Modifier = Modifier, events: List<Event>?) {
    val groupedEvents: Map<LocalDate, List<Event>>

    //if this composable is called when events is null, don't even bother going thru the rest of the code
    //just show loading and return
    if(events==null){
        Text("Loading...")
        return
        //NOTE : just because we returned DOESN'T mean that "Loading" text is NOT shown
        //we return AFTER the composable funciton "Text("Loading...")" has finished executing
        //i.e. first the composable function makes changes to the screen's hardware,
        //then we return AFTER the screen has been altered
        //take the return statement above the Text Composable and no screen alteration would happen, we would just return
    }

        groupedEvents = remember(events) { groupEventsByDate(events) }
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        val date = LocalDate.parse("2025-03-23", formatter)
//        Log.d("GROUPED EVENTS", groupedEvents[LocalDate.parse("2025-03-23", DateTimeFormatter.ofPattern("yyyy-MM-dd"))].toString())



    Scaffold(
        topBar = { TopBarComposable() }
    ) {
        Column (
            modifier = Modifier.padding(it)
        ){
            EventsList(
                events = events,
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun groupEventsByDate(events: List<Event>): Map<LocalDate, List<Event>> {
    return events.groupBy { event ->
        parseEventDate(event.eventDate).first
    }.toSortedMap()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatTime(time: LocalTime): String {
    return time.format(DateTimeFormatter.ofPattern("h:mm a"))
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseEventDate(dateString: String): Pair<LocalDate, LocalTime> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val dateTime = LocalDateTime.parse(dateString, formatter)
    return Pair(dateTime.toLocalDate(), dateTime.toLocalTime())
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateHeader(date: LocalDate): String {
    val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val monthDay = date.format(DateTimeFormatter.ofPattern("MMMM d"))
    return "$dayOfWeek, $monthDay"
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopBarComposable(modifier: Modifier = Modifier.padding(2.dp)) {
    TopAppBar(
        title = {
            Text(
                text = "Events",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle menu click */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            Text(
                text = "CATEGORIES",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(end = 16.dp)
            )
            IconButton(onClick = { /* Handle calendar click */ }) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Calendar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateHeader(date: LocalDate, modifier: Modifier = Modifier) {
    Text(
        text = formatDateHeader(date),
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(4.dp),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventItem(event: Event, modifier: Modifier = Modifier) {
    val (_, time) = parseEventDate(event.eventDate)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                /*navigation logic here*/
            },
    ) {
        // Time box on the left
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.Start,

        ) {
            Text(
                text = "Start:",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = formatTime(time),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Event details on the right
        Column(
            modifier = Modifier
                .weight(4f)
                .padding(end = 16.dp)
        ) {
            Text(
                text = event.subject,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = event.shortDesc,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
    HorizontalDivider()
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventsList(events: List<Event>, modifier: Modifier = Modifier) {
    val groupedEvents = remember(events) { groupEventsByDate(events) }

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        groupedEvents.forEach { (date, eventsForDate) ->

            stickyHeader {
                DateHeader(date)
            }

            items(eventsForDate) { event ->
                EventItem(event)
            }
        }
    }
}