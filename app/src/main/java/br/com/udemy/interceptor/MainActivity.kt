package br.com.udemy.interceptor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import org.koin.androidx.compose.getViewModel

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

        AppBar(
            title = "Personagens",
            onNavigateBack = {
                navController.popBackStack()
            },
            content = {
                CharacterScreen(state = state) { action ->
                    viewModel.dispatchAction(action)
                }
            }
        )

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

        AppBar(
            title = state.character?.name.orEmpty(),
            onNavigateBack = {
                navController.popBackStack()
            },
            content = {
                CharacterDetailScreen(state = state)
            }
        )

        LaunchedEffect(id) {
            viewModel.dispatchAction(CharacterDetailAction.GetCharacter(id))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title : String,
    onNavigateBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
            ),
            title = {
                Text(title)
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            }
        )

        content.invoke(this)
    }
}
