package com.example.shescreen.data.cms

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shescreen.ui.screens.EducationHub.CarouselCategory
import androidx.compose.runtime.State
import com.example.shescreen.ui.screens.EducationHub.CarouselItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EducationHubViewModel : ViewModel() {

    private val _carouselCategories = mutableStateOf<List<CarouselCategory>>(emptyList())
    val carouselCategories: State<List<CarouselCategory>> = _carouselCategories

    init {
        loadContent()
    }

    private fun loadContent() {
        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("Supabase", "fetching...")
                // 1. Fetch data from Supabase
                val categories = SupabaseInit.getCategories()
                Log.d("Supabase", "Categories: $categories")

                val content = SupabaseInit.getEducationalContent()
                Log.d("Supabase", "Content: $content")

                // 2. Group content by category → CarouselCategory
                val grouped = categories.map { category ->
                    val items = content
                        .filter { it.category_id == category.id }
                        .map {
                            CarouselItem(
                                id = it.id,
                                imageUrl = it.image,          // <-- URL from Supabase Storage
                                contentDescription = it.title  // shown under the image
                            )
                        }

                    CarouselCategory(
                        title = category.title,
                        items = items
                    )
                }

                _carouselCategories.value = grouped
                Log.d("EducationHubViewModel", "Loaded categories: ${grouped.size}")


            } catch (e: Exception) {
                // TODO: add proper error handling / retry
                e.printStackTrace()
                Log.e("Supabase", "error loading", e)
            }
        }
    }
}
