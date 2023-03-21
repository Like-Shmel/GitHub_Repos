package database

import androidx.room.*
import models.Favorites

@Dao
interface FavoritesDao {

    @Query("select * from favorites_table")
    suspend fun getAllFavorites(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: Favorites)

    @Delete
    suspend fun deleteFavorites(favorites: Favorites)




}