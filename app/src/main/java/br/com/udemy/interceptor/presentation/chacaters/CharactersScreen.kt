package br.com.udemy.interceptor.presentation.chacaters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.udemy.interceptor.domain.model.CharacterModel
import br.com.udemy.interceptor.presentation.chacaters.CharactersAction.OnCharacterSelected
import coil.compose.AsyncImage

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    state: CharacterState,
    onAction: (CharactersAction) -> Unit
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

        state.showCharacters() -> {
            LazyColumn(modifier = modifier) {
                items(items = state.characters, key = { it.id }) {
                    Characters(
                        character = it,
                        onClick = { character -> onAction.invoke(OnCharacterSelected(character)) }
                    )
                }
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
fun Characters(
    modifier: Modifier = Modifier,
    character: CharacterModel,
    onClick: (character: CharacterModel) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable { onClick(character) },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(character.image, contentDescription = "")
        Text(
            text = "Character ${character.name}",
            modifier = modifier
        )
    }
}