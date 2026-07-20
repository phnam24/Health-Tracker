package com.example.healthtracker.helper

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toLocalDateFromPicker(): LocalDate =
    Instant.ofEpochMilli(this)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

fun LocalDate.toLocalizedDateString(pattern: String, locale: Locale): String =
    format(DateTimeFormatter.ofPattern(pattern, locale))

fun LocalDate.toPickerMillis(): Long =
    atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
