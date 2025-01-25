package edu.ucne.registrootecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
@Entity(
    tableName = "mensajes",
    foreignKeys = [
        ForeignKey(
            entity = TecnicoEntity::class,
            parentColumns = ["tecnicoid"],
            childColumns = ["tecnicoid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MensajeEntity (
    @PrimaryKey
    val mensajeId: Int? = null,
    val mensaje: String = "",
    val tecnicoid: Int

)