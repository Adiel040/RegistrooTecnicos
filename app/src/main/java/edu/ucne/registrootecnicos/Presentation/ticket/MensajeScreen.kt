package edu.ucne.registrootecnicos.Presentation.ticket

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb
import edu.ucne.registrootecnicos.data.local.entities.MensajeEntity
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MensajeScreen(tecnicoDb: TecnicoDb, mensajeId: Int) {
    var messageText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // Obtener técnicos y mensajes desde la base de datos usando Flow
    val tecnicos by tecnicoDb.tecnicoDao().getAll().collectAsState(initial = emptyList())
    val messages by tecnicoDb.mensajeDao().getAll().collectAsState(initial = emptyList())

    var selectedTecnicoId by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chat con Técnicos") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Lista de mensajes
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                reverseLayout = true
            ) {
                items(messages.size) { index ->
                    val message = messages[index]
                    val tecnicoNombre = tecnicos.find { it.tecnicoid == message.tecnicoid }?.nombre ?: "Desconocido"
                    MessageItem(message = message, tecnicoNombre = tecnicoNombre)
                }
            }

            // Input de texto y selector de técnico
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Reply",
                    style = MaterialTheme.typography.bodyLarge
                )

                // Selector de técnico
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { expanded = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = tecnicos.find { it.tecnicoid == selectedTecnicoId }?.nombre
                                ?: "Selecciona un técnico"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        tecnicos.forEach { tecnico ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedTecnicoId = tecnico.tecnicoid!!
                                    expanded = false
                                },
                                text = { Text(text = tecnico.nombre) }
                            )
                        }
                    }
                }

                // Input de mensaje y botón para enviar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f),
                        label = { Text("Message") }
                    )
                    Button(
                        onClick = {
                            if (messageText.isNotBlank() && selectedTecnicoId != 0) {
                                scope.launch {
                                    val newMessage = MensajeEntity(
                                        mensajeId = null,
                                        mensaje = messageText,
                                        tecnicoid = selectedTecnicoId
                                    )
                                    tecnicoDb.mensajeDao().save(newMessage)
                                    messageText = "" // Limpiar campo
                                }
                            }
                        }
                    ) {
                        Text(text = "Send")
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItem(message: MensajeEntity, tecnicoNombre: String) {
    // Formatear la fecha actual
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    val formattedDate = LocalDateTime.now().format(formatter)

    val isOwner = tecnicoNombre == "Owner"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(if (isOwner) Alignment.CenterEnd else Alignment.CenterStart)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = if (isOwner) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
                )
                .padding(16.dp)
        ) {
            Text(
                text = "By $tecnicoNombre - $formattedDate",
                style = MaterialTheme.typography.bodySmall,
                color = if (isOwner) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = message.mensaje,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isOwner) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
