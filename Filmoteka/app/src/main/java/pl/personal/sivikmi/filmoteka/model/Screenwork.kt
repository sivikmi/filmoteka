package pl.personal.sivikmi.filmoteka.model

import android.net.Uri
import androidx.core.net.toUri
import java.time.LocalDate

data class Screenwork (
    val id: Int,
    var uri: Uri,
    var title: String,
    var premiere: LocalDate,
    var category: Category,
    var status: Status,
    var rating: Int? = null
) {
    companion object Id {
        var id: Int = 1
    }
    init {
        Id.id++
    }
}

val previewData = List(10) {
    Screenwork(
        it,
        "android.resource://pl.edu.pl.s24787.filmoteka/drawable/sunflower_temp".toUri(),
        "Title",
        LocalDate.of(1379, 3, 7),
        Category.entries.random(),
        Status.WATCHED,
        3
    )
}

