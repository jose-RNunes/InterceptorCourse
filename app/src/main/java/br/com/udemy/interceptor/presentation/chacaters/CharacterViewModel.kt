package br.com.udemy.interceptor.presentation.chacaters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.udemy.interceptor.data.remote.response.ErrorResponse
import br.com.udemy.interceptor.domain.model.CharacterModel
import br.com.udemy.interceptor.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterState())
    val state = _state.asStateFlow()

    fun dispatchAction(action: CharactersAction) {
        when (action) {
            CharactersAction.GetCharacters -> getCharacters()
            is CharactersAction.OnCharacterSelected -> onCharacterSelected(action.characterModel)
            CharactersAction.OnNavigated -> onNavigated()
        }
    }

    private fun getCharacters() {
        if(_state.value.characters.isNotEmpty()) return

        viewModelScope.launch {
            _state.update {
                it.copy(showLoading = true)
            }
            getCharactersUseCase()
                .catch { error ->
                    val message = if (error is ErrorResponse) {
                        error.errorMessage
                    } else {
                        error.message
                    }
                    _state.update {
                        it.copy(showLoading = false, showErrorMessage = message)
                    }
                }
                .collect { character ->
                    _state.update {
                        it.copy(showLoading = false, characters = character)
                    }
                }
        }
    }

    private fun onCharacterSelected(characterModel: CharacterModel) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    characterSelected = characterModel,
                    navigateToDetail = true
                )
            }
        }
    }

    private fun onNavigated() {
       _state.update {
           it.copy(navigateToDetail = false)
       }
    }
}