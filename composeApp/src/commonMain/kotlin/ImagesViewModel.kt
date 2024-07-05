import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.ItemImage

data class ImagesUiState(
    val images: List<ItemImage> = emptyList()
)

class ImagesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<ImagesUiState>(ImagesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateImages()
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
    }

    fun updateImages(){
        viewModelScope.launch {
            val images = getImages()
            _uiState.update { it.copy(images = images) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        httpClient.close()
    }

    private suspend fun getImages(): List<ItemImage> {
        return httpClient.get("https://sirotovab.github.io/testPuplicAPI/apiList.json")
            .body<List<ItemImage>>()
    }
}