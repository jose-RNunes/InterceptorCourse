package br.com.udemy.interceptor.data.repository

import br.com.udemy.interceptor.data.remote.RemoteSignedApi
import br.com.udemy.interceptor.domain.model.CharacterModel
import br.com.udemy.interceptor.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteSignedApi: RemoteSignedApi
) : CharacterRepository {

    override suspend fun fetchCharacters(): List<CharacterModel> {
       return remoteSignedApi.fetchCharacters().map { it.toCharacterModel() }
    }

    override suspend fun fetchCharacter(id: Int): CharacterModel {
        return remoteSignedApi.fetchCharacters(id).toCharacterModel()
    }
}