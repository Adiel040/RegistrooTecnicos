package edu.ucne.registrootecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrootecnicos.data.local.dao.MensajeDao
import edu.ucne.registrootecnicos.data.local.dao.TecnicoDao
import edu.ucne.registrootecnicos.data.local.dao.TicketDao
import edu.ucne.registrootecnicos.data.local.entities.MensajeEntity
import edu.ucne.registrootecnicos.data.local.entities.TecnicoEntity
import edu.ucne.registrootecnicos.data.local.entities.TicketEntity

@Database(
    entities = [
        TecnicoEntity::class,
        TicketEntity::class,
        MensajeEntity::class
    ], version = 5,
    exportSchema = false
)
abstract class TecnicoDb : RoomDatabase() {
    abstract fun tecnicoDao(): TecnicoDao
    abstract fun ticketDao(): TicketDao
    abstract fun mensajeDao(): MensajeDao
}