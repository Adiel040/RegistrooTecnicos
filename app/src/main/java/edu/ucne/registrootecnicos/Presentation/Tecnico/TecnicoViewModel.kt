package edu.ucne.registrootecnicos.Presentation.Tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrootecnicos.data.remote.Resource
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import edu.ucne.registrootecnicos.data.repository.TecnicoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val tecnicoRepository: TecnicoRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(TecnicoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTecnicos()
    }

    fun onEvent(event: TecnicoUiEvent) {
        when (event) {
            is TecnicoUiEvent.TecnicoIdChanged -> onTecnicoIdChange(event.tecnicoId)
            is TecnicoUiEvent.NombreChanged -> onNombreChange(event.nombre)
            is TecnicoUiEvent.SueldoChanged -> onSueldoChange(event.sueldo)
            TecnicoUiEvent.GetTecnicos -> getTecnicos()
            TecnicoUiEvent.PostTecnico -> {
                viewModelScope.launch {
                    tecnicoRepository.post(_uiState.value.toEntity())
                }
            }
        }
    }

    private fun onTecnicoIdChange(tecnicoId: Int) {
        _uiState.update {
            it.copy(tecnicoid = tecnicoId)
        }
    }

    private fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(nombre = nombre)
        }
    }

    private fun onSueldoChange(sueldo: String) {
        _uiState.update {
            it.copy(sueldo = sueldo.toString()) // Si sueldo debería ser Double, cambia TecnicoUiState.sueldo a Double
        }
    }

    private fun getTecnicos () {
        viewModelScope.launch {
            tecnicoRepository.getAll().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        _uiState.update{
                            it.copy(
                                tecnicos = result.data ?: emptyList(),
                            )
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }
}

fun TecnicoUiState.toEntity() = TecnicoDto(
    tecnicoid = tecnicoid,
    nombre = nombre,
    sueldo = sueldo
)