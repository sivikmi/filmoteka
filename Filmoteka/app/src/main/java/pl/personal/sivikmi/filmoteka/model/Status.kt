package pl.personal.sivikmi.filmoteka.model

import pl.personal.sivikmi.filmoteka.R

enum class Status(val displayName: Int) {
    WATCHED(R.string.status_watched),
    UNWATCHED(R.string.status_unwatched)
}