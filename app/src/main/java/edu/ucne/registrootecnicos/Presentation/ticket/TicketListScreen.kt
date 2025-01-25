package edu.ucne.registrootecnicos.Presentation.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb
import edu.ucne.registrootecnicos.data.local.entities.TicketEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    ticketList: List<TicketEntity>,
    onBackClick: () -> Unit,
    onAddClick: () -> Unit,
    onMensajeClick: () -> Unit,
    editarTicket: (Int) -> Unit,
    tecnicoDb: TecnicoDb
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Tickets") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            // Usamos Box para superponer los FABs correctamente
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd // Alineación de ambos FABs en la esquina inferior derecha
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // FAB para agregar tickets
                    FloatingActionButton(
                        onClick = onAddClick,
                        modifier = Modifier.padding(bottom = 16.dp) // Espacio entre los FABs
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Ticket")
                    }

                    // FAB para ir a la pantalla de mensajes
                    FloatingActionButton(
                        onClick = onMensajeClick
                    ) {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Ir a Mensajes")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Listado de Tickets",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(8.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(ticketList) { ticket ->
                    TicketRow(ticket, editarTicket, tecnicoDb)
                }
            }
        }
    }
}

@Composable
private fun TicketRow(
    ticket: TicketEntity,
    editarTicket: (Int) -> Unit,
    tecnicoDb: TecnicoDb
) {
    val scope = rememberCoroutineScope()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { editarTicket(ticket.ticketId ?: 0) }
            .padding(8.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.ticketId.toString(),
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.fecha,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.cliente,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.asunto,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.weight(2f),
            text = ticket.descripcion,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.tecnicoid.toString(),
            style = MaterialTheme.typography.bodyLarge
        )

        IconButton(
            onClick = {
                scope.launch {
                    deleteTicket(tecnicoDb, ticket)
                }
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar Ticket"
            )
        }
    }
    Divider(modifier = Modifier.padding(vertical = 4.dp))
}

suspend fun deleteTicket(tecnicoDb: TecnicoDb, ticket: TicketEntity) {
    tecnicoDb.ticketDao().delete(ticket)
}
