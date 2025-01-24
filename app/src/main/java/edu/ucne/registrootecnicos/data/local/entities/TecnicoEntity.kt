package edu.ucne.registrootecnicos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tecnicos")
data class TecnicoEntity(
    @PrimaryKey
    val tecnicoid: Int? = null,
    val nombre: String = "",
    val sueldo: String = ""
)
