package com.example.shescreen.data.bill

data class BillItem(
    val base_cost: Double,
    val date_created: String,
    val id: Int,
    val nhif_amount: Double,
    val nhif_covered: Boolean,
    val paid: Boolean,
    val patient_amount: Double,
    val patient_id: Int,
    val service: Service,
    val service_cost_id: Int,
    val service_id: Int
)