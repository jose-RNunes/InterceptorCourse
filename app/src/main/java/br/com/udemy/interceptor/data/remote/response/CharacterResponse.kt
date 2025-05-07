package br.com.udemy.interceptor.data.remote.response

import br.com.udemy.interceptor.domain.model.CharacterModel

data class CharacterResponse(
    val id: Int,
    val name: String,
    val image: String
) {
    fun toCharacterModel(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            image = image
        )
    }
}