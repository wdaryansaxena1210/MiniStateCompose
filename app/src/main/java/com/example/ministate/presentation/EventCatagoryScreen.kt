package com.example.ministate.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ministate.data.local.realm.EventCategory

@Composable
fun EventCatagoryScreen(
    onClick: (String) -> Unit,
    categories: List<EventCategory>?
) {
    LazyColumn() {
        categories?.let { categories->
            items(categories){ it ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(it.id) }
                        .clip(RoundedCornerShape(20.dp))
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text=it.short_title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.border(
                                width = 2.dp,
                                brush = Brush.linearGradient(colors = listOf(Color.Cyan, Color.Black)),
                                shape = RoundedCornerShape(20.dp),
                            ).padding(12.dp),
                            fontSize = 24.sp,
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}