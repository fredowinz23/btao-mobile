package com.capstone.btao.models

import com.google.gson.annotations.SerializedName

data class Violation(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("amount")
    var amount: String = "",

)