package br.com.udemy.interceptor.domain.usecase

import br.com.udemy.interceptor.domain.model.CharacterModel
import br.com.udemy.interceptor.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCharacterUseCase(private val characterRepository: CharacterRepository) {

    operator fun invoke(id: Int): Flow<CharacterModel> {
        return flow {
            val characters = characterRepository.fetchCharacter(id)
            emit(characters)
        }
    }
}