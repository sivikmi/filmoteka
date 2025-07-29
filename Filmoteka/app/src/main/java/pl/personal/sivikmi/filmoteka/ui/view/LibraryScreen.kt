package pl.personal.sivikmi.filmoteka.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import pl.personal.sivikmi.filmoteka.R
import pl.personal.sivikmi.filmoteka.model.Screenwork
import pl.personal.sivikmi.filmoteka.model.previewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmLibraryScreen(
    screenworks: List<Screenwork>,
    onScreenworkClicked: (Int) -> Unit,
    onLongScreenworkClicked: (Int) -> Unit,
    onFilterClicked: () -> Unit,
    onAddClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                actions = {
                    Row(modifier = Modifier.padding(10.dp)) {
                        IconButton(
                            onClick = onFilterClicked
                        ) {
                            Icon(
                                imageVector = Icons.Filled.FilterAlt,
                                contentDescription = stringResource(R.string.filter_button)
                            )
                        }
                        IconButton(
                            onClick = onAddClicked
                        ) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Filled.Add,
                                contentDescription = stringResource(R.string.add_button)
                            )
                        }
                    }
                }
            )
        },
    ) { innerPaddings ->
        val systemPaddings = WindowInsets.systemBars.asPaddingValues()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(bottom = systemPaddings.calculateBottomPadding()),
            contentPadding = innerPaddings,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(screenworks, key = { it.id }) {
                LibraryElement(it, { onScreenworkClicked(it.id) }) {onLongScreenworkClicked(it.id)}
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LibraryElement(screenwork: Screenwork, onClick: () -> Unit, onLongClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .clickable(onClick = onClick)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = rememberAsyncImagePainter(model = screenwork.uri),
                contentDescription = stringResource(R.string.poster)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = screenwork.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = stringResource(R.string.premiere) + " " + screenwork.premiere,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(screenwork.category.displayName),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(screenwork.status.displayName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                )
                if (screenwork.rating != null) {
                    Row {
                        for (i in 1 ..screenwork.rating!!) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                imageVector = Icons.Filled.Star,
                                contentDescription = stringResource(R.string.star_rating)
                            )
                        }

                        for (i in 1 ..  5 - screenwork.rating!!) {
                            Icon(
                                modifier = Modifier.size(15.dp),
                                imageVector = Icons.Filled.StarOutline,
                                contentDescription = stringResource(R.string.star_rating)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    FilmLibraryScreen(screenworks = previewData, {}, {}, {}) {}
}

@Preview(showBackground = true)
@Composable
fun ListElementPreview() {
    LibraryElement(screenwork = previewData[0], {}) {}
}