package com.karaev.githubrepos

import com.google.gson.annotations.SerializedName

class UserDetails (
    @SerializedName("name")
    val name: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("avatar_url")
    val avatar: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("blog")
    val email: String,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("public_repos")
    val public_repos: Int,

    @SerializedName("bio")
    val bio: String

    ): java.io.Serializable