package pl.personal.sivikmi.filmoteka.ui.view

import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import pl.personal.sivikmi.filmoteka.R
import pl.personal.sivikmi.filmoteka.model.Category
import pl.personal.sivikmi.filmoteka.model.Screenwork
import pl.personal.sivikmi.filmoteka.model.Status
import pl.personal.sivikmi.filmoteka.model.previewData
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    screenwork: Screenwork?,
    onBack: () -> Unit,
    onSave: (Screenwork) -> Unit
) {
    val id = screenwork?.id ?: Screenwork.Id.id

    var imageUri by remember {
        mutableStateOf<Uri?>(screenwork?.uri)
    }

    if (imageUri == null) {
        imageUri =
            stringResource(R.string.placeholder_uri).toUri()
    }

    var title by remember {
        mutableStateOf<String?>(screenwork?.title)
    }

    var premiere by remember {
        mutableStateOf<LocalDate?>(screenwork?.premiere)
    }

    var category by remember {
        mutableStateOf<Category?>(screenwork?.category)
    }

    var status by remember {
        mutableStateOf<Status?>(screenwork?.status)
    }

    var rating by remember {
        mutableStateOf<Int?>(screenwork?.rating)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
            }
        }
    )

    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(if (screenwork == null) stringResource(R.string.new_screenwork) else stringResource(R.string.edit_screenwork))
                        },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val new = Screenwork(
                            id,
                            imageUri!!,
                            title!!,
                            premiere!!,
                            category!!,
                            status!!,
                            rating
                        )
                        onSave(new)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Save,
                            contentDescription = stringResource(R.string.save)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(170.dp)
                    .clickable(onClick = {
                        galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                    }),
                painter = if (imageUri != null) rememberAsyncImagePainter(model = imageUri) else painterResource(
                    R.drawable.image_placeholder
                ),
                contentDescription = stringResource(R.string.poster)
            )

            val titleHasErrors by remember {
                derivedStateOf {
                    title == null || title!!.isEmpty()
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title ?: "",
                onValueChange = {
                    title = it
                },
                label = {
                    Text(
                        text = stringResource(R.string.screenwork_title),
                        fontStyle = FontStyle.Italic
                    )
                },
                isError = titleHasErrors,
                supportingText = {
                    if (titleHasErrors) {
                        Text(stringResource(R.string.error_title))
                    }
                }
            )

            var showDatePicker by remember { mutableStateOf(false) }
            val datePickerState = rememberDatePickerState()
            val selectedDate = datePickerState.selectedDateMillis?.let {
                premiere = LocalDate.parse(convertMillisToDate(it))
                convertMillisToDate(it)
            } ?: premiere?.toString() ?: ""

            val premiereHasErrors by remember {
                derivedStateOf {
                    if (premiere == null) {
                        true
                    } else {
                        false
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = selectedDate,
                onValueChange = {},
                label = {
                    Text(
                        text = stringResource(R.string.premiere),
                        fontStyle = FontStyle.Italic
                    )
                },
                isError = premiereHasErrors,
                supportingText = {
                    if (titleHasErrors) {
                        Text(stringResource(R.string.error_premiere))
                    }
                },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = !showDatePicker }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = stringResource(R.string.select_date)
                        )
                    }
                }
            )

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        IconButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = stringResource(R.string.confirm)
                            )
                        }
                    },
                    dismissButton = {
                        IconButton(
                            onClick = { showDatePicker = false }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = stringResource(R.string.dismiss)
                            )
                        }
                    }
                ) {
                    DatePicker(
                        modifier = Modifier.padding(16.dp),
                        title = { Text(stringResource(R.string.select_premiere_date)) },
                        headline = {
                            Text(datePickerState.selectedDateMillis?.let {
                                convertMillisToDate(it)
                            } ?: stringResource(R.string.premiere))
                        },
                        state = datePickerState,
                        showModeToggle = true
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    text = "Category:",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )

                val radioOptions = Category.entries
                val (selectedOption, onOptionSelected) = remember {
                    mutableStateOf(
                        category ?: radioOptions[0]
                    )
                }
                category = selectedOption

                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        onOptionSelected(text)
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = stringResource(text.displayName),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text(
                    text = "Status:",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )

                val radioOptions = Status.entries
                val (selectedOption, onOptionSelected) = remember {
                    mutableStateOf(
                        status ?: radioOptions[0]
                    )
                }
                status = selectedOption

                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = stringResource(text.displayName),
                                modifier = Modifier.padding(start = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }

            val ratingHasErrors by remember {
                derivedStateOf {
                    rating != null && (rating!!.toInt() < 0 || rating!!.toInt() > 5)
                }
            }

            if (status == Status.WATCHED) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = rating?.toString() ?: "",
                    onValueChange = {
                        rating = if (it.isEmpty()) {
                            null
                        } else {
                            try {
                                it.toInt()
                            } catch (e: NumberFormatException) {
                                null
                            }
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.rating),
                            fontStyle = FontStyle.Italic
                        )
                    },
                    isError = ratingHasErrors,
                    supportingText = {
                        if (ratingHasErrors) {
                            Text(stringResource(R.string.error_rating))
                        }
                    }
                )
            }
        }
    }
}


fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun AddEditScreenPreview() {
    AddEditScreen(previewData[0], {}, {})
}

