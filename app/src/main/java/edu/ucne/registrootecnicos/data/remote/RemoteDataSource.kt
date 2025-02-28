package edu.ucne.registrootecnicos.data.remote

import edu.ucne.registrootecnicos.data.remote.dto.MensajeDto
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val tecnicoApi: TecnicoApi
) {
    suspend fun getAll() = tecnicoApi.getAll()

    suspend fun post(tecnicoDto: TecnicoDto) = tecnicoApi.post(tecnicoDto)

    suspend fun getMensajes() = tecnicoApi.getMesajes()

    suspend fun postMensaje(mensajeDto: MensajeDto) = tecnicoApi.postMensaje(mensajeDto)
}