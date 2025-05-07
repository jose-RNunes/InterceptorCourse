package br.com.udemy.interceptor.presentation.character

import br.com.udemy.interceptor.domain.model.CharacterModel

data class CharacterDetailState(
    val showLoading: Boolean = false,
    val showErrorMessage: String? = null,
    val character: CharacterModel? = null
) {
    fun showError() = showErrorMessage != null && showLoading.not()

    fun showCharacter() = character != null && showError().not()
}

sealed interface CharacterDetailAction {
    data class GetCharacter(val id: Int) : CharacterDetailAction
}