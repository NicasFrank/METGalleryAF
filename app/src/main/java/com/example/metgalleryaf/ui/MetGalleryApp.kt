package com.example.metgalleryaf.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.ui.theme.METGalleryAFTheme

@Composable
fun MetGalleryApp(appContainer: AppContainer) {

    METGalleryAFTheme {
        val navController = rememberNavController()
        Greeting(name = "Ben")
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    METGalleryAFTheme {
        Greeting("Android")
    }
}