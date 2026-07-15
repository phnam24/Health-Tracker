package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailyAdviceType
import javax.inject.Inject

class BuildDailyAdviceUseCase @Inject constructor() {
    operator fun invoke(remaining: Int): DailyAdvice = when {
        remaining > 100 -> DailyAdvice(
            DailyAdviceType.NEED_MORE,
            remaining
        )
        remaining < -100 -> DailyAdvice(
            DailyAdviceType.OVER,
            kotlin.math.abs(remaining)
        )
        else -> DailyAdvice(
            DailyAdviceType.ON_TARGET,
            kotlin.math.abs(remaining)
        )
    }
}