package edu.ucne.registrootecnicos.Presentation.Tecnico

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb
import edu.ucne.registrootecnicos.data.local.entities.TecnicoEntity
import kotlinx.coroutines.launch

@Composable
fun TecnicoScreen(
    tecnicoId: Int,
    tecnicoDb: TecnicoDb,
    onNavigateBack: () -> Unit,
    viewModel: TecnicoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TecnicoBodyScreen(
        tecnicoId = tecnicoId,
        tecnicoDb = tecnicoDb,
        onNavigateBack = onNavigateBack,
        onEvent = viewModel::onEvent,
        uiState = uiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicoBodyScreen(
    tecnicoId: Int,
    tecnicoDb: TecnicoDb,
    onNavigateBack: () -> Unit,
    onEvent: (TecnicoUiEvent) -> Unit,
    uiState: TecnicoUiState
){
    var nombre by remember { mutableStateOf("") }
    var sueldo by remember { mutableStateOf("") }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

//    LaunchedEffect(true) {
//        scope.launch{
//            val tecnico =  findTecnico(tecnicoDb,tecnicoId)
//            nombre = tecnico?.nombre ?: ""
//            sueldo = tecnico?.sueldo ?: ""
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Crear Técnico") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    label = { Text(text = "Técnico") },
                    value = uiState.nombre,
                    onValueChange = { onEvent(TecnicoUiEvent.NombreChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    label = { Text(text = "Sueldo") },
                    value = uiState.sueldo,
                    onValueChange = { onEvent(TecnicoUiEvent.SueldoChanged(it)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.padding(2.dp))
//                errorMessage?.let {
//                    Text(text = it, color = Color.Red)
//                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedButton(
                        onClick = {
                            nombre = ""
                            sueldo = ""
                            errorMessage = null
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new button"
                        )
                        Text(text = "Nuevo")
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                onEvent(TecnicoUiEvent.PostTecnico)
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "save button"
                        )
                        Text(text = "Guardar")
                    }
                }
            }
        }
    }
}

suspend fun saveTecnico(tecnicoDb: TecnicoDb, tecnico: TecnicoEntity) {
    tecnicoDb.tecnicoDao().save(tecnico)
}

suspend fun findTecnico(tecnicoDb: TecnicoDb, tecnicoId: Int): TecnicoEntity? {
    return tecnicoDb.tecnicoDao().find(tecnicoId)
}
