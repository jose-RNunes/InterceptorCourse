package br.com.udemy.interceptor.presentation.chacaters

import br.com.udemy.interceptor.domain.model.CharacterModel

data class CharacterState(
    val showLoading: Boolean = false,
    val characters: List<CharacterModel> = emptyList(),
    val showErrorMessage: String? = null,
    val characterSelected: CharacterModel? = null,
    val navigateToDetail: Boolean = false
) {
    fun showCharacters() = characters.isNotEmpty() && showLoading.not()

    fun showError() = showErrorMessage != null && showLoading.not()
}

sealed interface CharactersAction {
    data object GetCharacters : CharactersAction
    data class OnCharacterSelected(val characterModel: CharacterModel) : CharactersAction
    data object OnNavigated: CharactersAction
}