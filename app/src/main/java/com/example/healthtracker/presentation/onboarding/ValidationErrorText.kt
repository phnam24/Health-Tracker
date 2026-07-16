package com.example.healthtracker.presentation.onboarding

import androidx.annotation.StringRes
import com.example.healthtracker.R
import com.example.healthtracker.domain.model.OnboardingValidationError

@StringRes
fun OnboardingValidationError.toStringRes(): Int = when (this) {
    OnboardingValidationError.REQUIRED -> R.string.error_required
    OnboardingValidationError.NAME_TOO_LONG -> R.string.error_name_too_long
    OnboardingValidationError.BIRTH_DATE_IN_FUTURE -> R.string.error_birth_date_future
    OnboardingValidationError.AGE_OUT_OF_RANGE -> R.string.error_age_range
    OnboardingValidationError.INVALID_NUMBER -> R.string.error_invalid_number
    OnboardingValidationError.WEIGHT_OUT_OF_RANGE -> R.string.error_weight_range
    OnboardingValidationError.HEIGHT_OUT_OF_RANGE -> R.string.error_height_range
}