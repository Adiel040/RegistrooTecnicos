package edu.ucne.registrootecnicos.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registrootecnicos.Presentation.Tecnico.TecnicoListScreen
import edu.ucne.registrootecnicos.Presentation.Tecnico.TecnicoScreen
import edu.ucne.registrootecnicos.data.local.database.TecnicoDb

@Composable
fun RegistrooTecnicosNavHost(navHostController: NavHostController, tecnicoDb: TecnicoDb){

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val tecnicoList by tecnicoDb.tecnicoDao().getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                gotoTecnico = { navHostController.navigate(Screen.TecnicoList) },
                gotoPrioridades = { navHostController.navigate(Screen.PrioridadList) },
                gotoTickets = { navHostController.navigate(Screen.TicketList) }
            )
        }
        composable<Screen.TecnicoList> {
            TecnicoListScreen(
                tecnicoList = tecnicoList,
                onBackClick = { navHostController.popBackStack() },
                onAddClick = {
                    navHostController.navigate(Screen.Tecnico(0))
                },
                editarTecnico = {
                    navHostController.navigate(Screen.Tecnico(it))
                }
            )
        }
        composable<Screen.Tecnico> {
            val tecnicoId = it.toRoute<Screen.Tecnico>().tecnicoId
            TecnicoScreen(
                tecnicoId = tecnicoId,
                tecnicoDb = tecnicoDb,
                onNavigateBack = {
                    navHostController.popBackStack() // Navega hacia atrás
                }
            )
        }
        composable<Screen.PrioridadList> {  }
        composable<Screen.Prioridad> {  }
    }
}