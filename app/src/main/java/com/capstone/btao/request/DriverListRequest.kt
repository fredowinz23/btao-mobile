package com.capstone.btao.request

import com.capstone.btao.models.Driver
import com.capstone.btao.models.User
import com.google.gson.annotations.SerializedName

data class DriverListRequest(
    @SerializedName("keyword")
    var keyword: String,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("driver_list")
    var driver_list: List<Driver>? = null
)