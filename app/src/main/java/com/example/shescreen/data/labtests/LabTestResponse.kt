package com.example.shescreen.data.labtests

data class LabTestResponse(
    val date_completed: Any,
    val date_ordered: String,
    val entered_by_id: Any,
    val id: Int,
    val ordered_by: OrderedBy,
    val patient: Patient,
    val recommendation_id: Int,
    val result: Any,
    val service: Service,
    val status: String,
    val follow_up_id: Int,
)