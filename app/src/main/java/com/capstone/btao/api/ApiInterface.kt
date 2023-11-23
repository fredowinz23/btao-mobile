package com.capstone.btao.api

import com.capstone.btao.request.AuthRequest
import com.capstone.btao.request.CreateDriverViolationRequest
import com.capstone.btao.request.DriverListRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Headers


interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("api/login.php")
    fun loginUser(@Body loginInfo: AuthRequest): Call<AuthRequest>

    @Headers("Content-Type: application/json")
    @POST("api/change-password.php")
    fun changePassword(@Body loginInfo: AuthRequest): Call<AuthRequest>

    @Headers("Content-Type: application/json")
    @POST("api/search-drivers.php")
    fun searchDrivers(@Body programListRequest: DriverListRequest): Call<DriverListRequest>

    @Headers("Content-Type: application/json")
    @POST("api/create-driver-violation.php")
    fun createDriverViolation(@Body createDriverViolationRequest: CreateDriverViolationRequest): Call<CreateDriverViolationRequest>

}