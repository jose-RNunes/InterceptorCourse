package br.com.udemy.interceptor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.udemy.interceptor.presentation.chacaters.CharacterScreen
import br.com.udemy.interceptor.presentation.chacaters.CharacterViewModel
import br.com.udemy.interceptor.presentation.chacaters.CharactersAction
import br.com.udemy.interceptor.presentation.character.CharacterDetailAction
import br.com.udemy.interceptor.presentation.character.CharacterDetailScreen
import br.com.udemy.interceptor.presentation.character.CharacterDetailViewModel
import br.com.udemy.interceptor.presentation.login.LoginAction
import br.com.udemy.interceptor.presentation.login.LoginScreen
import br.com.udemy.interceptor.presentation.login.LoginViewModel
import br.com.udemy.interceptor.ui.theme.InterceptorCourseTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinApplication
import org.koin.core.context.KoinContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            InterceptorCourseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavigationApp(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationApp(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoute.LOGIN.route
    ) {
        loginScreen(navController)
        charactersScreen(navController)
        characterDetailScreen(navController)
    }
}

private fun NavGraphBuilder.loginScreen(navController: NavHostController) {
    composable(ScreenRoute.LOGIN.route) {
        val viewModel: LoginViewModel = getViewModel<LoginViewModel>()

        val state by viewModel.state.collectAsState()

        LoginScreen(viewModel = viewModel, state = state) {
            viewModel.dispatchAction(LoginAction.OnNavigateSuccess)
            navController.navigate(ScreenRoute.CHARACTER_SCREEN.route)
        }
    }
}

private fun NavGraphBuilder.charactersScreen(navController: NavHostController) {
    composable(ScreenRoute.CHARACTER_SCREEN.route) {
        val viewModel: CharacterViewModel = getViewModel<CharacterViewModel>()

        val state by viewModel.state.collectAsState()

        CharacterScreen(state = state) { action ->
            viewModel.dispatchAction(action)
        }

        LaunchedEffect(Unit) {
            viewModel.dispatchAction(CharactersAction.GetCharacters)
        }

        if (state.navigateToDetail) {
            viewModel.dispatchAction(CharactersAction.OnNavigated)
            navController.navigate(
                ScreenRoute.CHARACTER_DETAIL_SCREEN.route.replace(
                    "{id}", state.characterSelected?.id.toString()
                )
            )
        }
    }
}

private fun NavGraphBuilder.characterDetailScreen(navController: NavHostController) {
    composable(
        ScreenRoute.CHARACTER_DETAIL_SCREEN.route,
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) { backStackEntry ->

        val id = backStackEntry.arguments?.getInt("id") ?: 0

        val viewModel: CharacterDetailViewModel = getViewModel<CharacterDetailViewModel>()

        val state by viewModel.state.collectAsState()

        CharacterDetailScreen(state = state)

        LaunchedEffect(id) {
            viewModel.dispatchAction(CharacterDetailAction.GetCharacter(id))
        }
    }
}


@Composable
fun teste() {
    Column {
        Text("Olá")

        Spacer(modifier = Modifier.weight(1f))

        Text("Mundo")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InterceptorCourseTheme {
        teste()
    }
}