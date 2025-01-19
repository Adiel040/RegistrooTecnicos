package edu.ucne.registrootecnicos.Presentation.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.ucne.registrootecnicos.ui.theme.RegistrooTecnicosTheme

@Composable
fun HomeScreen(
    gotoTecnico: () -> Unit,
    gotoTickets: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = gotoTecnico,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir hacia Técnicos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = gotoTickets,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir hacia Tickets")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    RegistrooTecnicosTheme {
        HomeScreen(
            gotoTecnico = {},
            gotoTickets = {}
        )
    }
}
