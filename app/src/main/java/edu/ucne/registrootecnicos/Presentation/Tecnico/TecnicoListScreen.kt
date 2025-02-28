package edu.ucne.registrootecnicos.Presentation.Tecnico

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb
import edu.ucne.registrootecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrootecnicos.data.remote.dto.TecnicoDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoListScreen(
    tecnicoList: List<TecnicoEntity>,
    onBackClick: () -> Unit, onAddClick: () -> Unit,
    editarTecnico: (Int) -> Unit,
    tecnicoDb: TecnicoDb,
    viewModel: TecnicoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Técnicos") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Técnico")
            }
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text("Listado de Tecnicos")

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.tecnicos) {
                    TecnicoRow(tecnicoDb, it, editarTecnico, eliminarTecnico = {})
                }
            }
        }
    }
}
@Composable
private fun TecnicoRow(tecnicoDb: TecnicoDb, it: TecnicoDto, editarTecnico: (Int) -> Unit, eliminarTecnico: (Int) -> Unit) {
   val scope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(modifier = Modifier.weight(1f), text = it.tecnicoid.toString())
        Text(
            modifier = Modifier.weight(2f),
            text = it.nombre,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(modifier = Modifier.weight(2f), text = it.sueldo)

        // Botón de eliminación
        IconButton(
            onClick = {
                scope.launch {
                    deleteTecnico(tecnicoDb, it)
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar Técnico"
            )
        }

        // Botón para editar el técnico
        IconButton(
            onClick = { editarTecnico(it.tecnicoid ?: 0) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar Técnico"
            )
        }
    }

    // Divider para separar filas
    Divider(modifier = Modifier.padding(vertical = 4.dp))
}

suspend fun deleteTecnico(tecnicoDb: TecnicoDb, tecnico: TecnicoDto) {
    //tecnicoDb.tecnicoDao().delete(tecnico)
}