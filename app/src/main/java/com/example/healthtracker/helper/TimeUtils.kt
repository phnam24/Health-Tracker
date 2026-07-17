package com.example.healthtracker.helper

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateFromPicker(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toLocalizedDateString(pattern: String, locale: Locale): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))
