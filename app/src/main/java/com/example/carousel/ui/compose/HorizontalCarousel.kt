package com.example.carousel.ui.compose

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.carousel.state.AutoScrollPagerState
import com.example.carousel.state.rememberInfiniteAutoScrollPagerState
import com.example.carousel.state.rememberLoopAutoScrollPagerState
import com.example.carousel.ui.theme.CarouselTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCarousel(
    modifier: Modifier = Modifier,
    state: AutoScrollPagerState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 1,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    flingBehavior: SnapFlingBehavior = PagerDefaults.flingBehavior(state = state.pagerState),
    userScrollEnable: Boolean = true,
    reversLayout: Boolean = false,
    key: ((index: Int) -> Any)? = null,
    pageNestedScrollConnection: NestedScrollConnection = remember(key1 = state.pagerState) {
        PagerDefaults.pageNestedScrollConnection(state.pagerState, Orientation.Horizontal)
    },
    content: @Composable BoxScope.(Int) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        HorizontalPager(
            state = state.pagerState,
            contentPadding = contentPadding,
            pageSize = pageSize,
            beyondBoundsPageCount = beyondBoundsPageCount,
            pageSpacing = pageSpacing,
            verticalAlignment = verticalAlignment,
            flingBehavior = flingBehavior,
            userScrollEnabled = userScrollEnable,
            reverseLayout = reversLayout,
            key = key,
            pageNestedScrollConnection = pageNestedScrollConnection,
        ) { page ->
            Box(modifier = modifier) {
                val index = page.mod(state.pageCount) + 1
                content(index)
            }
        }
        HorizontalPagerIndicator(state = state)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewLoopHorizontalCarousel() {
    val context = LocalContext.current
    CarouselTheme {
        Surface {
            HorizontalCarousel(
                state = rememberLoopAutoScrollPagerState(pageCount = 3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
            ) {
                Card(
                    onClick = {
                        Toast.makeText(context, "Page $it tapped", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Page $it",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(20F, TextUnitType.Sp),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewInfiniteHorizontalCarousel() {
    val context = LocalContext.current
    CarouselTheme {
        Surface {
            HorizontalCarousel(
                state = rememberInfiniteAutoScrollPagerState(pageCount = 3),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
            ) {
                Card(
                    onClick = {
                        Toast.makeText(context, "Page $it tapped", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    border = BorderStroke(1.dp, Color.DarkGray),
                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Page $it",
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(20F, TextUnitType.Sp),
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}
