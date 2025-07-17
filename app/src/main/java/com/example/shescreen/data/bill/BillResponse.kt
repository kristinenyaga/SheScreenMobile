package com.example.shescreen.data.bill

data class BillResponse(
    val items: List<BillItem>,
    val total_cost: Double
)