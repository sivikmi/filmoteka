package pl.personal.sivikmi.filmoteka.data

import pl.personal.sivikmi.filmoteka.model.Screenwork

interface ScreenworkRepository {
    val screenworks: List<Screenwork>

    fun findById(id: Int): Screenwork? {
        return screenworks.find { it.id == id }
    }

    fun addScreenwork(screenwork: Screenwork)

    fun editScreenwork(screenworkToEdit: Screenwork, screenworkNewData: Screenwork)

    fun removeScreenwork(screenwork: Screenwork)
}