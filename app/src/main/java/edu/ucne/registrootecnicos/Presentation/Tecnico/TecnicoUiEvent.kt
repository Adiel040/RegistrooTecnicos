package edu.ucne.registrootecnicos.Presentation.Tecnico

sealed interface TecnicoUiEvent {
    data object GetTecnicos : TecnicoUiEvent
    data object PostTecnico : TecnicoUiEvent
    data class TecnicoIdChanged(val tecnicoId:Int): TecnicoUiEvent
    data class NombreChanged(val nombre:String): TecnicoUiEvent
    data class SueldoChanged(val sueldo:String): TecnicoUiEvent
}