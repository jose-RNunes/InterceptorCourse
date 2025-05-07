package br.com.udemy.interceptor.presentation.character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.udemy.interceptor.domain.model.CharacterModel
import coil.compose.AsyncImage

@Composable
fun CharacterDetailScreen(
    modifier: Modifier = Modifier,
    state: CharacterDetailState,
) {
    when {
        state.showLoading -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        state.showCharacter() && state.character != null -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CharacterSelected(
                    character = state.character
                )
            }
        }

        state.showError() -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(state.showErrorMessage.orEmpty())
            }
        }
    }
}

@Composable
fun CharacterSelected(
    modifier: Modifier = Modifier,
    character: CharacterModel
) {
    Column(
        modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = character.image,
            contentDescription = ""
        )

        Text(
            text = "Character ${character.name}",
            modifier = modifier
        )
    }
}