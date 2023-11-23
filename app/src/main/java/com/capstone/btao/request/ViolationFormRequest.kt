package com.capstone.btao.request

import com.capstone.btao.models.Driver
import com.capstone.btao.models.PenaltyItem
import com.google.gson.annotations.SerializedName
import com.capstone.btao.models.Violation

data class ViolationFormRequest(
    @SerializedName("driverPenaltyId")
    var driverPenaltyId: Int,

    @SerializedName("success")
    var success: String = "",

    @SerializedName("driver")
    var driver: Driver? = null,

    @SerializedName("violation_list")
    var violation_list: List<Violation>? = null,

    @SerializedName("penalty_item_list")
    var penalty_item_list: List<PenaltyItem>? = null,
)