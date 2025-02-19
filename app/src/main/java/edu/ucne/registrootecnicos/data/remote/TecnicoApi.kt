package edu.ucne.registrootecnicos.data.remote

import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TecnicoApi {

    @GET("aqui-va-el-endpoint")
    suspend fun get(): List<TecnicoDto>

    @POST("aqui-va-el-endpoint")
    suspend fun post(@Body post: TecnicoDto): TecnicoDto
}