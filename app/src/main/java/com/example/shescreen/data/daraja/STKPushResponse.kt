package com.example.shescreen.data.daraja

import com.google.gson.annotations.SerializedName

data class StkPushResponse(
    val MerchantRequestID: String,
    val CheckoutRequestID: String,
    val ResponseCode: String,
    val ResponseDescription: String,
    val CustomerMessage: String
)
