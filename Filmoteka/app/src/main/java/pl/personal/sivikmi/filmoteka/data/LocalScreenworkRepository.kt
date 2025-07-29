package pl.personal.sivikmi.filmoteka.data

import pl.personal.sivikmi.filmoteka.model.Category
import pl.personal.sivikmi.filmoteka.model.Screenwork
import pl.personal.sivikmi.filmoteka.model.Status
import java.time.LocalDate
import androidx.core.net.toUri

object LocalScreenworkRepository : ScreenworkRepository {
    private val _screenworks: MutableList<Screenwork>

    override val screenworks: List<Screenwork>
        get() = _screenworks

    init {
        _screenworks = """
            1, sunflower_temp, Mini1, 2020-12-03, MINISERIES, WATCHED
            2, sunflower_temp, Animated2, 2004-03-07, ANIMATEDSERIES, UNWATCHED
            3, sunflower_temp, Talk3, 1980-08-20, TALKSHOW, WATCHED, 5
            4, sunflower_temp, Feature4, 1234-05-13, FEATUREFILM, WATCHED, 3
            5, sunflower_temp, Series5, 3456-12-08, SERIES, UNWATCHED
            6, sunflower_temp, DocumentaryFilm6, 3456-08-08, DOCUMENTARYFILM, WATCHED, 1
            7, sunflower_temp, DocumentarySeries7, 5039-11-12, DOCUMENTARYSERIES, UNWATCHED
            8, sunflower_temp, RealityShow8, 4897-01-08, REALITYSHOW, WATCHED, 0
        """.trimIndent()
            .split("\n")
            .filter { it.isNotBlank() }
            .map {
                val parts = it.split(", ")
                Screenwork(
                    Screenwork.Id.id,
                    ("android.resource://pl.edu.pl.s24787.filmoteka/drawable/" + parts[1]).toUri(),
                    parts[2],
                    LocalDate.parse(parts[3]),
                    Category.valueOf(parts[4]),
                    Status.valueOf(parts[5]),
                    if (parts.size == 7) parts[6].toInt() else null
                )
            }
            .toMutableList()
        _screenworks.sortWith(compareBy {it.premiere})
    }

    override fun addScreenwork(screenwork: Screenwork) {
         _screenworks.add(screenwork)
        _screenworks.sortWith(compareBy {it.premiere})
    }

    override fun editScreenwork(screenworkToEdit: Screenwork, screenworkNewData: Screenwork) {
        val id = _screenworks.indexOf(screenworkToEdit)
        _screenworks[id].uri = screenworkNewData.uri
        _screenworks[id].title = screenworkNewData.title
        _screenworks[id].premiere = screenworkNewData.premiere
        _screenworks[id].category = screenworkNewData.category
        _screenworks[id].status = screenworkNewData.status
        _screenworks[id].rating = screenworkNewData.rating
        _screenworks.sortWith(compareBy {it.premiere})
    }

    override fun removeScreenwork(screenwork: Screenwork) {
        _screenworks.remove(screenwork)
    }
}