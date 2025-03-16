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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCatagoryScreen(
    onClick: (String) -> Unit,
    categories: List<EventCategory>?,
    onRefresh : ( toggleIsRefreshing : () -> Unit ) -> Unit
) {
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {onRefresh( {isRefreshing = !isRefreshing} )},
        state = refreshState,
        contentAlignment = Alignment.BottomEnd,
    ) {
        if(isRefreshing){
            Text("loading...")
        }
        else{

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
    }
}


/*
 * toggleIsRefreshing : (boolean) -> boolean
 * mai tumhe ek esa function de rha hu jisme TUM MUJHE ek boolean doge (can be anything, can be isloading
 * can be isRefreshing), aur mai uss boolean ko leke ek alag boolean mei map kar dunga
 *
 * toggleIsRefreshing: (Boolean) -> Unit
 * mai tumhe ek esa function de rha hu, jisko agar tum use karna chahate ho toh TUMHE PEHLE
 * ek boolean bnana hoga (vo boolean bnana tumhari zimedari) aur tum fir vo boolean iss function ko doge
 * aur ye function usko Unit/nothing mei map karega
 *
 * toggleIsRefreshing : () -> Unit
 * mai tumhe ek esa function dera hu jisko agar tum use karna chahte ho, toh tum bina koi variable/value bnaye
 * uss function ko use kar sakte ho, and vo function nothing lega aur nothing mei map kar dega
 */