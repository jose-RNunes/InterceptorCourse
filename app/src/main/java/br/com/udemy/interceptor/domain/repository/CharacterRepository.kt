package br.com.udemy.interceptor.domain.repository

import br.com.udemy.interceptor.domain.model.CharacterModel

interface CharacterRepository {

    suspend fun fetchCharacters(): List<CharacterModel>

    suspend fun fetchCharacter(id: Int): CharacterModel
}