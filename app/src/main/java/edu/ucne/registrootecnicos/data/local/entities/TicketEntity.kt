package edu.ucne.registrootecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tickets",
    foreignKeys = [
        ForeignKey(
            entity = TecnicoEntity::class,
            parentColumns = ["tecnicoid"],
            childColumns = ["tecnicoid"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val ticketId: Int?,
    val fecha: String,
    val prioridadId: Int,
    val cliente: String,
    val asunto: String,
    val descripcion: String,
    val tecnicoid: Int
)