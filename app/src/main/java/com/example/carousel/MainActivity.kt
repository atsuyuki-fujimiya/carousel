package com.example.carousel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.carousel.state.rememberLoopAutoScrollPagerState
import com.example.carousel.ui.compose.HorizontalCarousel
import com.example.carousel.ui.theme.CarouselTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CarouselTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HorizontalCarousel(
//                        state = rememberInfiniteAutoScrollPagerState(pageCount = 5),
                        state = rememberLoopAutoScrollPagerState(pageCount = 5),
                        modifier = Modifier.height(320.dp)
                    ) {
                        Card(
                            onClick = {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Page $it tapped",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
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
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarouselTheme {
        Greeting("Android")
    }
}
