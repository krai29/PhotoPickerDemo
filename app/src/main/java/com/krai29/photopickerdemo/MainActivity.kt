package com.krai29.photopickerdemo

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.krai29.photopickerdemo.ui.theme.PhotoPickerDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoPickerDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhotoPickerScreen()
                }
            }
        }
    }
}

@Composable
fun PhotoPickerScreen(){

    // The uri of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }

    // The launcher we will use for PickerVisualMedia contract
    // When launch() is called, this will display the photo picker
    // When we need single content, PickVisualMedia and if it's multiple then PickMultipleVisualMedia
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        // When the user has picked the media, it;s uri is returned here
        photoUri = uri
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
        Button(onClick = { launcher.launch(
            PickVisualMediaRequest(
                // Image only, video only or both can be selected based on requirement
                mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
            )
        ) }) {
            Text(
                text = "Select photo or images"
            )
        }

        if (photoUri!=null){
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(photoUri).build()
            )
            
            Image(
                painter = painter,
                modifier = Modifier.padding(10.dp).fillMaxWidth().border(5.dp, Color.Gray),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}

