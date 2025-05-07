package br.com.udemy.interceptor.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.udemy.interceptor.data.remote.response.ErrorResponse
import br.com.udemy.interceptor.domain.usecase.GetCharacterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    fun dispatchAction(action: CharacterDetailAction) {
        when (action) {
            is CharacterDetailAction.GetCharacter -> getCharacter(action.id)
        }
    }

    private fun getCharacter(id: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(showLoading = true)
            }

            getCharacterUseCase(id)
                .catch { error ->
                    val message = if (error is ErrorResponse) {
                        error.errorMessage
                    } else {
                        error.message
                    }
                    _state.update {
                        it.copy(
                            showLoading = false,
                            showErrorMessage = message
                        )
                    }
                }
                .collect { character ->
                    _state.update {
                        it.copy(
                            showLoading = false,
                            character = character,
                            showErrorMessage = null
                        )
                    }
                }
        }
    }
}