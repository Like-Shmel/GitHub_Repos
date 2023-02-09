package models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
class Favorites (
    @PrimaryKey
    @ColumnInfo(name = "name_db")
    val name: String,

    @ColumnInfo(name = "login_db")
    val login: String,

    @ColumnInfo(name = "avatar_db")
    val avatar: String

): java.io.Serializable