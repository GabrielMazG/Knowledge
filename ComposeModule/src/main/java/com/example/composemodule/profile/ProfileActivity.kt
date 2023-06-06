package com.example.composemodule.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composemodule.R
import com.example.composemodule.profile.theme.ColorPrimary
import com.example.composemodule.profile.theme.ColorPrimaryDark
import com.example.composemodule.profile.theme.ColorSecondary
import com.example.composemodule.profile.theme.ColorSecondaryLight
import com.example.composemodule.profile.theme.ComposeTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateBizCard()
                }
            }
        }
    }
}

@Composable
fun CreateBizCard() {
    val buttonClickState = remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        color = ColorPrimaryDark
    ) {
        Card(
            modifier = Modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            colors = CardDefaults.cardColors(containerColor = ColorPrimary),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CreateImageProfile()
                Divider(modifier = Modifier.padding(8.dp))
                CreateInfo()
                Button(
                    onClick = {
                        buttonClickState.value = !buttonClickState.value
                    }) {
                    Text(
                        text = "Portfolio",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                if (buttonClickState.value) {
                    Content()
                } else {
                    Box {}
                }
            }
        }
    }
}

@Preview
@Composable
fun Content() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(5.dp),
    ) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
            border = BorderStroke(2.dp, color = ColorPrimaryDark),
            contentColor = ColorPrimary
        ) {
            Portfolio(
                data = listOf(
                    "Project 1 ",
                    "Project 2",
                    "Project 3",
                    "Project 4",
                    "Project 5"
                )
            )
        }
    }
}

@Composable
fun Portfolio(data: List<String>) {
    LazyColumn(modifier = Modifier.background(ColorPrimaryDark)) {
        items(data) { item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(corner = CornerSize(5.dp)),
                colors = CardDefaults.cardColors(containerColor = ColorPrimary),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(ColorPrimary)
                        .padding(6.dp)
                ) {
                    CreateImageProfile(modifier = Modifier.size(75.dp))
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 0.dp)
                            .align(alignment = CenterVertically)
                    ) {
                        Text(
                            text = item,
                            fontWeight = FontWeight.Bold,
                            color = ColorSecondary
                        )
                        Text(
                            text = "A grat project",
                            style = MaterialTheme.typography.labelSmall,
                            color = ColorSecondaryLight
                        )

                    }
                }
            }
        }
    }
}

@Composable
private fun CreateInfo() {
    Column(modifier = Modifier.padding(5.dp)) {
        Text(
            text = "Miles P.",
            style = MaterialTheme.typography.titleLarge,
            color = ColorSecondary
        )
        Text(
            text = "Android Compose Programmer",
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = ColorSecondaryLight
        )
        Text(
            text = "@themilesCompose",
            modifier = Modifier.padding(3.dp),
            color = ColorSecondaryLight
        )
    }
}

@Composable
private fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(150.dp)
            .padding(4.dp),
        shape = CircleShape,
        border = BorderStroke(0.dp, Color.LightGray),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {

        Image(
            painter = painterResource(id = R.drawable.profile_image),
            contentDescription = "Profile image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTheme {
        CreateBizCard()
    }
}