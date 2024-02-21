package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignment2.ui.theme.Assignment2Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Assignment2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentArtworkIndex by remember { mutableIntStateOf(0) }

    val artworks = listOf(
        Artwork("Picasso1", "Buste d’homme dans un cadre", "Pablo Picasso", 1969),
        Artwork("Picasso2", "Girl in front of mirror", "Pablo Picasso", 1932),
        Artwork("Picasso3", "Bust of a Woman", "Pablo Picasso", 1944)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ArtworkWall(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth(),
            artwork = artworks[currentArtworkIndex]
        )

        ArtworkDescriptor(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            artwork = artworks[currentArtworkIndex]
        )

        DisplayController(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            onNext = { currentArtworkIndex = (currentArtworkIndex + 1) % artworks.size },
            onPrevious = { currentArtworkIndex = (currentArtworkIndex - 1 + artworks.size) % artworks.size }
        )
    }
}

@Composable
fun ArtworkWall(
    modifier: Modifier = Modifier,
    artwork: Artwork
) {
    Box(
        modifier = modifier
            .background(color = Color(243, 207, 198))
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getArtworkImageResource(artwork.id)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
    }
}

// Helper function to get the resource ID for the artwork image
@DrawableRes
fun getArtworkImageResource(artworkId: String): Int {
    return when (artworkId) {
        "Picasso1" -> R.drawable.picasso1
        "Picasso2" -> R.drawable.picasso2
        "Picasso3" -> R.drawable.picasso3
        else -> throw IllegalArgumentException("Invalid artwork ID")
    }
}


@Composable
fun ArtworkDescriptor(
    modifier: Modifier = Modifier,
    artwork: Artwork
) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = artwork.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(227, 11, 92)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${artwork.artist} (${artwork.year})",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(149, 53, 83)
        )
    }
}

@Composable
fun DisplayController(
    modifier: Modifier = Modifier,
    onNext: () -> Unit,
    onPrevious: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onPrevious,
        ) {
            Text(
                "Previous",
                color = Color.White,
                style = LocalTextStyle.current.copy(fontSize = 18.sp) // Adjust the font size as needed
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = onNext,

        ) {
            Text(
                "Next",
                color = Color.White,
                style = LocalTextStyle.current.copy(fontSize = 18.sp) // Adjust the font size as needed
            )
        }
    }
}



@Composable
@Preview(showBackground = true)
fun ArtSpaceAppPreview() {
    Assignment2Theme {
        ArtSpaceApp()
    }
}

data class Artwork(
    val id: String,
    val name: String,
    val artist: String,
    val year: Int
)
