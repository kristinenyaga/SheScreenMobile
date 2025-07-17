package com.example.shescreen.data.followup

data class FollowUpResponse(
    val age: Int,
    val category: String,
    val confidence: Double,
    val context: String,
    val created_at: String,
    val final_plan: String,
    val finalized_by: FinalizedBy,
    val finalized_by_user_id: Int,
    val first_sexual_intercourse_age: Int,
    val hpv_current_test_result: String,
    val id: Int,
    val method: String,
    val number_of_sexual_partners: Int,
    val options: String,
    val pap_smear_result: String,
    val patient_id: Int,
    val prediction_label: Int,
    val prediction_probabilities: String,
    val risk_prediction_id: Any,
    val screening_type_last: String,
    val smoking_status: String,
    val stds_history: String
)