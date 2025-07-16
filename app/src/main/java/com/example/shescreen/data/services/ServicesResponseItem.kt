package com.example.shescreen.data.services

data class ServicesResponseItem(
    val base_cost: Double,
    val id: Int,
    val insurance_copay_amount: Double,
    val nhif_covered: Boolean,
    val out_of_pocket: Double,
    val service: Service
)