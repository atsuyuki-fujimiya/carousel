package com.example.carousel.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.carousel.state.AutoScrollPagerState
import com.example.carousel.state.rememberLoopAutoScrollPagerState

@Composable
fun HorizontalPagerIndicator(
    state: AutoScrollPagerState,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        state = rememberLazyListState(),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val pageCount = state.pageCount
        repeat(pageCount) { index ->
            val color = if (state.currentPage == index) Color.Blue else Color.LightGray
            item {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color, CircleShape)
                        .size(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun PreviewHorizontalPagerIndicator() {
    HorizontalPagerIndicator(state = rememberLoopAutoScrollPagerState(pageCount = 4))
}
