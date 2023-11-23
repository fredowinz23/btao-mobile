package com.capstone.btao.request

import com.capstone.btao.models.Driver
import com.capstone.btao.models.User
import com.google.gson.annotations.SerializedName

data class CreateDriverViolationRequest(
    @SerializedName("username")
    var username: String,

    @SerializedName("driverId")
    var driverId: Int,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("driverPenaltyId")
    var driverPenaltyId: Int = 0,
)