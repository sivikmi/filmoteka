package pl.personal.sivikmi.filmoteka.model

import pl.personal.sivikmi.filmoteka.R

enum class Category (val displayName: Int){
    FEATUREFILM(R.string.feature_film),
    SHORTFILM(R.string.shor_film),
    SERIES(R.string.series),
    MINISERIES(R.string.mini_series),
    DOCUMENTARYFILM(R.string.documentary_film),
    DOCUMENTARYSERIES(R.string.documentary_series),
    ANIMATEDFILM(R.string.animated_film),
    ANIMATEDSERIES(R.string.animated_series),
    FILM(R.string.film),
    REALITYSHOW(R.string.reality_show),
    TALKSHOW(R.string.talk_show)

}