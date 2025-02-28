package edu.ucne.registrootecnicos.data.repository

import edu.ucne.registrootecnicos.data.remote.RemoteDataSource
import edu.ucne.registrootecnicos.data.remote.Resource
import edu.ucne.registrootecnicos.data.remote.TecnicoApi
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class TecnicoRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    fun getAll(): Flow<Resource<List<TecnicoDto>>> = flow {
        try {
            emit(Resource.Loading())
            val clientes = remoteDataSource.getAll()
            emit(Resource.Success(clientes))
        } catch (e: HttpException) {
            val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
            emit(Resource.Error("Error de conexion $errorMessage"))
        } catch (e: Exception) {
            emit(Resource.Error("Error ${e.message}"))
        }
    }

    suspend fun post(tecnicoDto: TecnicoDto) = remoteDataSource.post(tecnicoDto)
}