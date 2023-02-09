package com.karaev.githubrepos

import com.google.gson.annotations.SerializedName

class Repository(
    @SerializedName("name")
    val name : String,
    @SerializedName("owner")
    val owner : Owner,
    @SerializedName("description")
    val description : String,
    @SerializedName("id")
    val id : Int,
    @SerializedName("login")
    val login: String
): java.io.Serializable {


}

