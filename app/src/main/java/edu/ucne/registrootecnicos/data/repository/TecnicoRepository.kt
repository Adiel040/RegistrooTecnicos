package edu.ucne.registrootecnicos.data.repository

import edu.ucne.registrootecnicos.data.remote.TecnicoApi
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import javax.inject.Inject

class TecnicoRepository @Inject constructor(
    private val api: TecnicoApi
) {
    suspend fun get() = api.get()

    suspend fun post(tecnicoDto: TecnicoDto) = api.post(tecnicoDto)
}