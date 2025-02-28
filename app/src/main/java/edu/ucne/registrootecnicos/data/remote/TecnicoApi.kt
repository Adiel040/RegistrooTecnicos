package edu.ucne.registrootecnicos.data.remote

import edu.ucne.registrootecnicos.data.remote.dto.MensajeDto
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TecnicoApi {

    @GET("api/Tecnicos")
    suspend fun getAll(): List<TecnicoDto>

    @POST("api/Tecnicos")
    suspend fun post(@Body post: TecnicoDto): TecnicoDto

    @GET("api/Mensajes")
    suspend fun getMesajes(): List<MensajeDto>

    @POST("api/Mensajes")
    suspend fun postMensaje(@Body post: MensajeDto): MensajeDto
}