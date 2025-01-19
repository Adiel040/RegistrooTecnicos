package edu.ucne.registrootecnicos.Presentation.ticket

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb
import edu.ucne.registrootecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrootecnicos.data.local.entities.TicketEntity
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    ticketId: Int,
    tecnicoDb: TecnicoDb,
    onNavigateBack: () -> Unit
) {
    var fecha by remember { mutableStateOf("") }
    var prioridadId by remember { mutableStateOf(0) }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var tecnicoId by remember { mutableStateOf(0) }
    var errorMessage: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            fecha = selectedDate
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    LaunchedEffect(ticketId) {
        scope.launch {

            val ticket = findTicket(tecnicoDb, ticketId)

            if (ticket != null) {
                fecha = ticket.fecha ?: ""
                prioridadId = ticket.prioridadId ?: 0
                cliente = ticket.cliente ?: ""
                asunto = ticket.asunto ?: ""
                descripcion = ticket.descripcion ?: ""
                tecnicoId = ticket.tecnicoid ?: 0
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Crear Ticket") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
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
                OutlinedButton(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (fecha.isEmpty()) "Seleccionar Fecha" else fecha)
                }
                OutlinedTextField(
                    label = { Text(text = "Prioridad ID") },
                    value = prioridadId.toString(),
                    onValueChange = { prioridadId = it.toIntOrNull() ?: 0 },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text(text = "Cliente") },
                    value = cliente,
                    onValueChange = { cliente = it },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text(text = "Asunto") },
                    value = asunto,
                    onValueChange = { asunto = it },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    label = { Text(text = "Descripción") },
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    modifier = Modifier.fillMaxWidth()
                )
                TecnicoSelector(tecnicoDb = tecnicoDb) { tecnicoIdSeleccionado ->
                    tecnicoId = tecnicoIdSeleccionado
                }
                Spacer(modifier = Modifier.padding(2.dp))
                errorMessage?.let {
                    Text(text = it, color = Color.Red)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    OutlinedButton(
                        onClick = {

                            fecha = ""
                            prioridadId = 0
                            cliente = ""
                            asunto = ""
                            descripcion = ""
                            tecnicoId = 0
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
                            if (fecha.isBlank() || cliente.isBlank() || asunto.isBlank()) {
                                errorMessage = "Campos obligatorios vacíos"
                            } else {
                                scope.launch {
                                    saveTicket(
                                        tecnicoDb,
                                        TicketEntity(
                                            ticketId = if (ticketId == 0) null else ticketId,
                                            fecha = fecha,
                                            prioridadId = prioridadId,
                                            cliente = cliente,
                                            asunto = asunto,
                                            descripcion = descripcion,
                                            tecnicoid = tecnicoId
                                        )
                                    )

                                    fecha = ""
                                    prioridadId = 0
                                    cliente = ""
                                    asunto = ""
                                    descripcion = ""
                                    tecnicoId = 0
                                    errorMessage = null
                                    onNavigateBack()
                                }
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

@Composable
fun TecnicoSelector(tecnicoDb: TecnicoDb, onSeleccionarTecnico: (Int) -> Unit) {
    val tecnicos = tecnicoDb.tecnicoDao().getAll().collectAsState(initial = emptyList())
    val selectedTecnicoId = remember { mutableStateOf(0) }
    val expanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded.value = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Técnico ID: ${tecnicos.value.find { it.tecnicoid == selectedTecnicoId.value }?.nombre ?: "Seleccionar Técnico"}")
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            tecnicos.value.forEach { tecnico ->
                DropdownMenuItem(
                    onClick = {
                        selectedTecnicoId.value = tecnico.tecnicoid ?: 0
                        expanded.value = false
                        onSeleccionarTecnico(tecnico.tecnicoid ?: 0)
                    },
                    text = {
                        Text(
                            text = tecnico.nombre,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        )
                    }
                )
            }
        }
    }
}


suspend fun saveTicket(tecnicoDb: TecnicoDb, ticket: TicketEntity) {
    tecnicoDb.ticketDao().save(ticket)
}

suspend fun findTicket(tecnicoDb: TecnicoDb, ticketId: Int): TicketEntity? {
    return tecnicoDb.ticketDao().find(ticketId)
}