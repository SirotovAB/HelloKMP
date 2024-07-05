import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.ItemImage
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val imagesViewModel = getViewModel(Unit, viewModelFactory { ImagesViewModel() })
        imagesPages(imagesViewModel)
    }
}

@Composable
fun imagesPages(viewModel: ImagesViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    AnimatedVisibility(uiState.images.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
            content = {
                items(uiState.images.size) {
                    ImageCell(uiState.images[it])
                }
            }
        )
    }
}

@Composable
fun ImageCell(image: ItemImage) {
    KamelImage(
        resource = asyncPainterResource(data = image.URL),
        contentDescription = image.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize().aspectRatio(1.0f)
    )
}
