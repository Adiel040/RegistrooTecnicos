package edu.ucne.registrootecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrootecnicos.data.local.entities.MensajeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MensajeDao {
    @Upsert
    suspend fun save(mensajes: MensajeEntity)

    @Query(
        """
        SELECT * 
        FROM mensajes 
        WHERE mensajeId = :id 
        LIMIT 1
        """
    )
    suspend fun find(id: Int): MensajeEntity?

    @Delete
    suspend fun delete(mensaje: MensajeEntity)

    @Query("SELECT * FROM mensajes")
    fun getAll(): Flow<List<MensajeEntity>>
}