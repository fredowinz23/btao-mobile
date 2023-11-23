package com.capstone.btao.models

import com.google.gson.annotations.SerializedName

data class PenaltyItem(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("violation")
    var violation: Violation?= null,
)