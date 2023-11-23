package com.capstone.btao.request

import com.capstone.btao.models.Driver
import com.capstone.btao.models.PenaltyItem
import com.google.gson.annotations.SerializedName
import com.capstone.btao.models.Violation

data class RemovePenaltyItemRequest(
    @SerializedName("penaltyItemId")
    var penaltyItemId: Int,

    @SerializedName("success")
    var success: String = "",
)