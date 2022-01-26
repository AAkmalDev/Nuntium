package uz.pandamobileuz.nuntium.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.pandamobileuz.nuntium.data.models.NewsApi

@Database(entities = [NewsApi::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun newsDao(): NewsDao
}