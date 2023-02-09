package database

import androidx.room.*
import models.Favorites

@Dao
interface FavoritesDao {

    @Query("select * from favorites_table")
    fun getAllFavorites(): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorites: Favorites)

    @Delete
    fun deleteFavorites(favorites: Favorites)




}