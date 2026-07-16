package com.example.healthtracker.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthtracker.domain.model.OnboardingDraft
import com.example.healthtracker.domain.model.OnboardingField
import com.example.healthtracker.domain.model.OnboardingPolicy
import com.example.healthtracker.domain.model.OnboardingValidationError
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class ValidateOnboardingUseCase
@Inject constructor(
    private val calculateAge: CalculateAgeUseCase,
    private val clock: Clock
) {
    operator fun invoke(
        draft: OnboardingDraft
    ): Map<OnboardingField, OnboardingValidationError> {
        val today = LocalDate.now(clock)

        return buildMap {
            validateName(draft)
            validatePersonalInfo(draft, today)
            validateBodyMetrics(draft)
            validateActivityAndGoal(draft)
        }
    }

    private fun MutableMap<OnboardingField, OnboardingValidationError>.validateName(
        draft: OnboardingDraft
    ) {
        when {
            draft.name.isBlank() -> {
                put(
                    OnboardingField.NAME,
                    OnboardingValidationError.REQUIRED
                )
            }

            draft.name.trim().length > OnboardingPolicy.NAME_MAX_LENGTH -> {
                put(
                    OnboardingField.NAME,
                    OnboardingValidationError.NAME_TOO_LONG
                )
            }
        }
    }

    private fun MutableMap<OnboardingField, OnboardingValidationError>.validatePersonalInfo(
        draft: OnboardingDraft,
        today: LocalDate
    ) {
        val birthDate = draft.birthDate

        when {
            birthDate == null -> {
                put(
                    OnboardingField.BIRTH_DATE,
                    OnboardingValidationError.REQUIRED
                )
            }

            birthDate.isAfter(today) -> {
                put(
                    OnboardingField.BIRTH_DATE,
                    OnboardingValidationError.BIRTH_DATE_IN_FUTURE
                )
            }

            calculateAge(birthDate, today) !in
                    OnboardingPolicy.AGE_MIN..OnboardingPolicy.AGE_MAX -> {
                put(
                    OnboardingField.BIRTH_DATE,
                    OnboardingValidationError.AGE_OUT_OF_RANGE
                )
            }
        }

        if (draft.gender == null) {
            put(
                OnboardingField.GENDER,
                OnboardingValidationError.REQUIRED
            )
        }
    }

    private fun MutableMap<OnboardingField, OnboardingValidationError>.validateBodyMetrics(
        draft: OnboardingDraft
    ) {
        val weight = draft.weightInput.toDoubleOrNull()
        val height = draft.heightInput.toDoubleOrNull()

        when {
            draft.weightInput.isBlank() -> {
                put(
                    OnboardingField.WEIGHT,
                    OnboardingValidationError.REQUIRED
                )
            }

            weight == null -> {
                put(
                    OnboardingField.WEIGHT,
                    OnboardingValidationError.INVALID_NUMBER
                )
            }

            weight !in OnboardingPolicy.WEIGHT_MIN..OnboardingPolicy.WEIGHT_MAX -> {
                put(
                    OnboardingField.WEIGHT,
                    OnboardingValidationError.WEIGHT_OUT_OF_RANGE
                )
            }
        }

        when {
            draft.heightInput.isBlank() -> {
                put(
                    OnboardingField.HEIGHT,
                    OnboardingValidationError.REQUIRED
                )
            }

            height == null -> {
                put(
                    OnboardingField.HEIGHT,
                    OnboardingValidationError.INVALID_NUMBER
                )
            }

            height !in OnboardingPolicy.HEIGHT_MIN..OnboardingPolicy.HEIGHT_MAX -> {
                put(
                    OnboardingField.HEIGHT,
                    OnboardingValidationError.HEIGHT_OUT_OF_RANGE
                )
            }
        }
    }

    private fun MutableMap<OnboardingField, OnboardingValidationError>.validateActivityAndGoal(
        draft: OnboardingDraft
    ) {
        if (draft.activityLevel == null) {
            put(
                OnboardingField.ACTIVITY_LEVEL,
                OnboardingValidationError.REQUIRED
            )
        }

        if (draft.goal == null) {
            put(
                OnboardingField.GOAL,
                OnboardingValidationError.REQUIRED
            )
        }
    }
}