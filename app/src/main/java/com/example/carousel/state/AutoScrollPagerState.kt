package com.example.carousel.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
data class AutoScrollPagerState(
    val pageCount: Int,
    val initPage: Int,
    val currentPage: Int,
    val scrollDurationMillis: Long,
    val pagerState: PagerState,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberInfiniteAutoScrollPagerState(
    pageCount: Int,
    initialPage: Int = 0,
    scrollDurationMillis: Long = 3000L,
    onPageChanged: ((Int) -> Unit)? = null,
): AutoScrollPagerState {
    val startIndex = pageCount + initialPage
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { pageCount * 3 })
    val currentPage = pagerState.currentPage.mod(pageCount)
    val scope = rememberCoroutineScope()
    val scrollTo = { state: PagerState, page: Int ->
        scope.launch {
            if (!state.isScrollInProgress) {
                pagerState.animateScrollToPage(page)
            }
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        onPageChanged?.invoke(currentPage)
        snapshotFlow { pagerState.settledPage to pagerState.isScrollInProgress }
            .filter { !it.second }
            .map { it.first }
            .collectLatest {
                val index = when {
                    it < pageCount -> it + pageCount
                    it >= pageCount * 2 -> it - pageCount
                    else -> null
                }
                if (index != null) {
                    launch { pagerState.scrollToPage(index) }
                } else {
                    delay(scrollDurationMillis)
                    scrollTo(pagerState, pagerState.currentPage + 1)
                }
            }
    }

    return AutoScrollPagerState(
        pageCount,
        initialPage,
        currentPage,
        scrollDurationMillis,
        pagerState
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun rememberLoopAutoScrollPagerState(
    pageCount: Int,
    initialPage: Int = 0,
    scrollDurationMillis: Long = 3000L,
    onPageChanged: ((Int) -> Unit)? = null,
): AutoScrollPagerState {
    val pagerState = rememberPagerState(initialPage = initialPage, pageCount = { pageCount })
    val scope = rememberCoroutineScope()
    val currentPage = pagerState.currentPage
    val scrollTo = { state: PagerState, page: Int ->
        scope.launch {
            if (!state.isScrollInProgress) {
                pagerState.animateScrollToPage(page)
            }
        }
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        onPageChanged?.invoke(currentPage)
        val page = currentPage.inc().rem(pageCount)
        delay(scrollDurationMillis)
        scrollTo(pagerState, page)
    }

    return AutoScrollPagerState(
        pageCount,
        initialPage,
        currentPage,
        scrollDurationMillis,
        pagerState
    )
}
