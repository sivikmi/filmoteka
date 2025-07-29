package pl.personal.sivikmi.filmoteka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pl.personal.sivikmi.filmoteka.ui.view.ElementPreviewScreen
import pl.personal.sivikmi.filmoteka.ui.view.FilmLibraryScreen
import pl.personal.sivikmi.filmoteka.ui.viewModel.LibraryViewModel
import pl.personal.sivikmi.filmoteka.model.Status
import pl.personal.sivikmi.filmoteka.ui.theme.FilmotekaTheme
import pl.personal.sivikmi.filmoteka.ui.view.AddEditScreen
import pl.personal.sivikmi.filmoteka.ui.viewModel.AddEditViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmotekaTheme {
                Home()
            }
        }
    }
}

object Destinations {
    val argId = "id"

    val libraryDestination = "library"
    val previewDestination = "preview/{$argId}"

    fun getRouteForPreview(id: Int): String {
        return previewDestination.replace("{$argId}", id.toString())
    }
}

@Composable
fun Home() {
    val navController = rememberNavController()

    NavHost(
        navController,
        Destinations.libraryDestination
    ) {
        composable(Destinations.libraryDestination) {
            val vm: LibraryViewModel = viewModel()

            LaunchedEffect(Unit) {
                vm.load()
            }

            FilmLibraryScreen(
                vm.state,
                { navController.navigate(Destinations.getRouteForPreview(it)) },
                {},
                {}
            ) { navController.navigate(Destinations.getRouteForPreview(-1)) }
        }

        composable(
            Destinations.previewDestination,
            arguments = listOf(
                navArgument(Destinations.argId) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Destinations.argId) ?: -1
            val vm: AddEditViewModel = viewModel()

            LaunchedEffect(id) {
                vm.load(id)
            }

            val screenwork = vm.stateScreenwork
            if (screenwork != null) {
                if (screenwork.status == Status.WATCHED) {
                    ElementPreviewScreen(screenwork) { navController.popBackStack() }
                } else {
                    AddEditScreen(screenwork, { navController.popBackStack() }) { screenworkNew ->
                        vm.edit(screenwork, screenworkNew)
                        navController.popBackStack()
                    }
                }
            } else {
                AddEditScreen(screenwork, { navController.popBackStack() }) { screenworkNew ->
                    vm.add(screenworkNew)
                    navController.popBackStack()
                }
            }

        }
    }
}

