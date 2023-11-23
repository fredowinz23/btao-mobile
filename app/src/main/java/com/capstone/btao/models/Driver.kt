package com.capstone.btao.models

import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("firstName")
    var firstName: String = "",

    @SerializedName("lastName")
    var lastName: String = "",

    @SerializedName("middleInitial")
    var middleInitial: String = "",

    @SerializedName("address")
    var address: String = "",

    @SerializedName("birthday")
    var birthday: String = "",

    @SerializedName("licenseNumber")
    var licenseNumber: String = "",

    @SerializedName("plateNumber")
    var plateNumber: String = "",

    @SerializedName("color")
    var color: String = "",

    @SerializedName("brand")
    var brand: String = "",

    @SerializedName("model")
    var model: String = "",

)