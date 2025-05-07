package br.com.udemy.interceptor.data.remote

import br.com.udemy.interceptor.data.remote.response.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteSignedApi {

    @GET("characters")
    suspend fun fetchCharacters(): List<CharacterResponse>

    @GET("characters/{id}")
    suspend fun fetchCharacters(@Path("id") id: Int): CharacterResponse
}