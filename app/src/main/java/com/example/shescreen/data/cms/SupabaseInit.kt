@file:OptIn(SupabaseExperimental::class)

package com.example.shescreen.data.cms

import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.realtime.selectAsFlow
import io.ktor.client.plugins.websocket.WebSockets

object SupabaseInit {
    @OptIn(SupabaseInternal::class)
    val supabaseClient = createSupabaseClient(
        supabaseUrl = "https://nzotfjsjackyehojjiwb.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im56b3RmanNqYWNreWVob2pqaXdiIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTI0OTk1NzksImV4cCI6MjA2ODA3NTU3OX0.h68qX-Uo0Q8-BSaPUypQJIZsgT0asI0voIqA3k-lXn4"
    ) {
        install(Postgrest)
        install(Realtime)
        httpConfig {
            this.install(WebSockets)
        }
    }

    suspend fun getCategories()=
        supabaseClient.from("Categories")
            .select ()
            .decodeList<Category>()

    fun getCategoriesRealtime()=
        supabaseClient.from("Categories")
            .selectAsFlow(Category::id)

    suspend fun getEducationalContent()=
        supabaseClient.from("educational_content")
            .select ()
            .decodeList<EducationalContent>()

    fun getEducationalContentRealtime()=
        supabaseClient.from("educational_content")
            .selectAsFlow(EducationalContent::id)
}