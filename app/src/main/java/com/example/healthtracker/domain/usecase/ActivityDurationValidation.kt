package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.ActivityDurationRules
import com.example.healthtracker.domain.model.AddActivityError

internal fun parseDuration(input: String): Int? {
    val minutes = input.trim().toIntOrNull() ?: return null
    return minutes.takeIf {
        it in ActivityDurationRules.MIN_MINUTES..ActivityDurationRules.MAX_MINUTES
    }
}

internal fun durationError(input: String): AddActivityError {
    val normalized = input.trim()
    if (normalized.isEmpty()) {
        return AddActivityError.DURATION_REQUIRED
    }

    val minutes = normalized.toIntOrNull()
        ?: return AddActivityError.DURATION_INVALID

    if (minutes !in ActivityDurationRules.MIN_MINUTES..ActivityDurationRules.MAX_MINUTES) {
        return AddActivityError.DURATION_OUT_OF_RANGE
    }

    error("durationError must only be called for invalid duration input")
}
