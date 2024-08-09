package com.bhavinlearn.artspaceapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhavinlearn.artspaceapp.ui.theme.ArtSpaceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    val config = LocalConfiguration.current

    val arts = listOf(
        painterResource(id = R.drawable.art1),
        painterResource(id = R.drawable.art2),
        painterResource(id = R.drawable.art3),
        painterResource(id = R.drawable.art4)
    )

    val artDescriptions = listOf(
        stringResource(id = R.string.art1_title),
        stringResource(id = R.string.art2_title),
        stringResource(id = R.string.art3_title),
        stringResource(id = R.string.art4_title)
    )

    val artistNames = listOf(
        stringResource(id = R.string.artist1),
        stringResource(id = R.string.artist2),
        stringResource(id = R.string.artist3),
        stringResource(id = R.string.artist4)
    )

    val artYears = listOf(
        stringResource(id = R.string.art1_year),
        stringResource(id = R.string.art2_year),
        stringResource(id = R.string.art3_year),
        stringResource(id = R.string.art4_year)
    )

    var currentStep by remember { mutableIntStateOf(1) }

    val index = (currentStep - 1).coerceIn(0, arts.size - 1)

    val imageResources = arts[index]
    val imageDescription = artDescriptions[index]
    val artistName = artistNames[index]
    val artYear = artYears[index]


    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxHeight()
            .padding(20.dp)
    ) {

        when (config.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight(0.70f)
                        .fillMaxWidth(0.35f)
                        .align(Alignment.CenterHorizontally),
                    shadowElevation = 10.dp
                ) {
                    ArtImage(imageResources, imageDescription)
                }
            }
            else -> {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight(0.60f)
                        .fillMaxWidth(1f),
                    shadowElevation = 10.dp
                ) {
                    ArtImage(imageResources, imageDescription)
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        ImageContentDetails(imageDescription, artistName, artYear)
        ActionButton(currentStep,
            onImageClickPrevious = {
                if (currentStep > 0) {
                    currentStep--
                }
            },
            onImageClickNext = {
                if (currentStep < 5) {
                    currentStep++
                }
            }
        )

    }

}

@Composable
fun ArtImage(imageResources: Painter, imageDescription: String) {
    Image(
        painter = imageResources,
        contentDescription = imageDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(top = 25.dp, bottom = 25.dp, start = 30.dp, end = 30.dp)

    )
}

@Composable
fun ImageContentDetails(artTitle: String, artistName: String, artYear: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .background(colorResource(id = R.color.grey))
                .padding(16.dp)
        ) {

            Text(
                text = artTitle,
                fontSize = 25.sp,
                fontWeight = FontWeight.Light,
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Text(
                    text = artistName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = "($artYear)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )
            }

        }
    }
}

@Composable
fun ActionButton(
    currentStep: Int,
    onImageClickPrevious: () -> Unit,
    onImageClickNext: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {

        Button(
            modifier = Modifier.defaultMinSize(120.dp),
            enabled = currentStep > 1,
            onClick = onImageClickPrevious
        ) {
            Text(text = stringResource(R.string.previous))
        }
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier.defaultMinSize(120.dp),
            enabled = currentStep < 4,
            onClick = onImageClickNext
        ) {
            Text(text = stringResource(R.string.next))
        }


    }
}

@Preview(showBackground = true,showSystemUi = true,device = Devices.AUTOMOTIVE_1024p, widthDp = 1024, heightDp = 720)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtSpaceAppTheme {
        ArtSpaceApp()
    }
}