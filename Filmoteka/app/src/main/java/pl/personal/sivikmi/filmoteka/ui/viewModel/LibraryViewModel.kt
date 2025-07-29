package pl.personal.sivikmi.filmoteka.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.personal.sivikmi.filmoteka.data.LocalScreenworkRepository
import pl.personal.sivikmi.filmoteka.data.ScreenworkRepository
import pl.personal.sivikmi.filmoteka.model.Screenwork

class LibraryViewModel : ViewModel(){
    private val _repository: ScreenworkRepository = LocalScreenworkRepository

    var state by mutableStateOf(listOf<Screenwork>())

    fun load() {
        state = _repository.screenworks
    }
}