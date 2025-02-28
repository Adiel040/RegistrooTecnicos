package edu.ucne.registrootecnicos.Presentation.Tecnico

import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto

data class TecnicoUiState(
    val tecnicoid: Int? = null,
    val nombre: String = "",
    val sueldo: String = "",
    val tecnicos: List<TecnicoDto> = emptyList()
)