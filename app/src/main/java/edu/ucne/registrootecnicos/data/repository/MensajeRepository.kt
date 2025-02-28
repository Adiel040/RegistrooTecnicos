package edu.ucne.registrootecnicos.data.repository

import edu.ucne.registrootecnicos.data.remote.RemoteDataSource
import edu.ucne.registrootecnicos.data.remote.Resource
import edu.ucne.registrootecnicos.data.remote.dto.MensajeDto
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MensajeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getMensajes(): Flow<Resource<List<MensajeDto>>> = flow {
        try {
            emit(Resource.Loading())
            val mensajes = remoteDataSource.getMensajes()
            emit(Resource.Success(mensajes))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    suspend fun postMensaje(mensajeDto: MensajeDto) = remoteDataSource.postMensaje(mensajeDto)
}