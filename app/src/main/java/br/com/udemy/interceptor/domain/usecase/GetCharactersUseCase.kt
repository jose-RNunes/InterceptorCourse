package br.com.udemy.interceptor.domain.usecase

import br.com.udemy.interceptor.domain.model.CharacterModel
import br.com.udemy.interceptor.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharactersUseCase(private val characterRepository: CharacterRepository) {

    operator fun invoke(): Flow<List<CharacterModel>> {
        return flow {
            val characters = characterRepository.fetchCharacters()
            emit(characters)
        }
    }
}