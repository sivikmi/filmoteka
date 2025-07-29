package pl.personal.sivikmi.filmoteka.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.personal.sivikmi.filmoteka.data.ScreenworkRepository
import pl.personal.sivikmi.filmoteka.model.Screenwork
import pl.personal.sivikmi.filmoteka.data.LocalScreenworkRepository

class AddEditViewModel : ViewModel() {
    private val _repository: ScreenworkRepository = LocalScreenworkRepository

    var stateScreenwork by mutableStateOf<Screenwork?>(null)

    fun load(id: Int) {
        stateScreenwork = _repository.findById(id)
    }

    fun add(screenwork: Screenwork) {
        _repository.addScreenwork(screenwork)
    }

    fun edit(screenworkToEdit: Screenwork, screenworkNewData: Screenwork) {
        _repository.editScreenwork(screenworkToEdit, screenworkNewData)
    }

    fun remove(screenwork: Screenwork) {
        _repository.removeScreenwork(screenwork)
    }
}