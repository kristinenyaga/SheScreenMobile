package com.example.shescreen.utils.pdf

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.shescreen.data.followup.FollowUpResponse
import com.example.shescreen.data.getprofile.GetProfileResponse
import com.example.shescreen.data.labtests.LabTestResponse
import com.example.shescreen.data.recommendation.RecommendationResponseItem
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

//allow_origins=["https://she-screen-web.vercel.app"],
//192.168.100.77

object HealthPdfGenerator {
    fun generatePdf(
        context: Context,
        profile: GetProfileResponse?,
        recommendations: List<RecommendationResponseItem>,
        labTests: List<LabTestResponse>?,
        followUps: FollowUpResponse?
    ) {
        try {
            val document = Document()
            val fileName = "HealthSummary_${System.currentTimeMillis()}.pdf"
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                fileName
            )

            PdfWriter.getInstance(document, FileOutputStream(file))
            document.open()

            // Sample Content
            document.add(Paragraph("Health Summary", Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD)))
            document.add(Paragraph("Name: ${profile?.first_name} ${profile?.last_name}"))
            document.add(Paragraph("Email: ${profile?.email}"))
            document.add(Paragraph("\n"))

            for (rec in recommendations) {
                document.add(Paragraph("📝 Recommendation: ${rec.test_recommendations}"))
                document.add(Paragraph("Urgency: ${rec.urgency}"))
                document.add(Paragraph("Notes: ${rec.notes}"))
                document.add(Paragraph("Date: ${rec.created_at}"))
                document.add(Paragraph("--------------------------"))
            }

            for (lab in labTests!!) {
                document.add(Paragraph("📝 LabTest: ${lab.service}"))
                document.add(Paragraph("Result: ${lab.result}"))
                document.add(Paragraph("Status: ${lab.status}"))
                document.add(Paragraph("Date: ${lab.date_completed}"))
                document.add(Paragraph("--------------------------"))
            }

            followUps?.let {
                document.add(Paragraph("Follow-Up Plan: ${it.final_plan}"))
            }

            document.close()

            // Share PDF
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.provider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            context.startActivity(Intent.createChooser(shareIntent, ""))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to generate PDF", Toast.LENGTH_LONG).show()
        }
    }
}
