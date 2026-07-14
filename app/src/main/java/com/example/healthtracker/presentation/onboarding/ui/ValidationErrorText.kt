package com.example.healthtracker.presentation.onboarding.ui

import androidx.annotation.StringRes
import com.example.healthtracker.R

@StringRes
fun ValidationError.toStringRes(): Int = when (this) {
    ValidationError.REQUIRED -> R.string.error_required
    ValidationError.NAME_TOO_LONG -> R.string.error_name_too_long
    ValidationError.BIRTH_DATE_IN_FUTURE -> R.string.error_birth_date_future
    ValidationError.AGE_OUT_OF_RANGE -> R.string.error_age_range
    ValidationError.INVALID_NUMBER -> R.string.error_invalid_number
    ValidationError.WEIGHT_OUT_OF_RANGE -> R.string.error_weight_range
    ValidationError.HEIGHT_OUT_OF_RANGE -> R.string.error_height_range
}