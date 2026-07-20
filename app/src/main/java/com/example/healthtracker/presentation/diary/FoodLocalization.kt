package com.example.healthtracker.presentation.diary

import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.model.MealEntry

fun Food.localizedName(language: String): String =
    if (language == ENGLISH_LANGUAGE) nameEn.ifBlank { name } else name

fun MealEntry.localizedName(language: String): String =
    if (language == ENGLISH_LANGUAGE) {
        foodNameEn.ifBlank { foodName }
    } else {
        foodName
    }

private const val ENGLISH_LANGUAGE = "en"
