package com.capstone.btao.request

import com.google.gson.annotations.SerializedName
import com.capstone.btao.models.User

data class AuthRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("profile")
    var profile: User? = null
)