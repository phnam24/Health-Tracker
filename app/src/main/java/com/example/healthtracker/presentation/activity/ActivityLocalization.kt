package com.example.healthtracker.presentation.activity

import com.example.healthtracker.domain.model.ActivityEntry
import com.example.healthtracker.domain.model.ActivityType

fun ActivityType.localizedName(language: String): String =
    if (language == ENGLISH_LANGUAGE) nameEn.ifBlank { name } else name

fun ActivityEntry.localizedName(language: String): String =
    if (language == ENGLISH_LANGUAGE) {
        activityNameEn.ifBlank { activityName }
    } else {
        activityName
    }

private const val ENGLISH_LANGUAGE = "en"
