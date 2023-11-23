package com.capstone.btao.request

import com.capstone.btao.models.Driver
import com.capstone.btao.models.PenaltyItem
import com.google.gson.annotations.SerializedName
import com.capstone.btao.models.Violation

data class AddPenaltyItemRequest(
    @SerializedName("violationId")
    var violationId: Int,

    @SerializedName("driverPenaltyId")
    var driverPenaltyId: Int,

    @SerializedName("success")
    var success: String = "",
)