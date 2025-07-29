package pl.personal.sivikmi.filmoteka.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import pl.personal.sivikmi.filmoteka.R
import pl.personal.sivikmi.filmoteka.model.Screenwork
import pl.personal.sivikmi.filmoteka.model.previewData

@Composable
fun ElementPreviewScreen(
    screenwork: Screenwork,
    onOk: () -> Unit
) {
    Dialog(onOk) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(170.dp),
                    painter = rememberAsyncImagePainter(model = screenwork.uri),
                    contentDescription = stringResource(R.string.poster)
                )

                Text(
                    text = "Title: ${screenwork.title}",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Premiere date: ${screenwork.premiere}",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )

                Text(
                    text = stringResource(screenwork.category.displayName),
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.SemiBold
                )

                Row {
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

                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onOk
                ) {
                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        imageVector = Icons.Filled.Check,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ElementPreviewScreenPreview() {
    ElementPreviewScreen(previewData[0]) {}
}
