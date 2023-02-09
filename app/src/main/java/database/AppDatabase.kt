package database

import androidx.room.Database
import androidx.room.RoomDatabase
import models.Favorites

@Database(
    entities = arrayOf(Favorites::class),
    version = 1
)


abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}