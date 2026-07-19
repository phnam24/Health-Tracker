package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.DailyAdvice
import com.example.healthtracker.domain.model.DailyAdviceType
import javax.inject.Inject
import kotlin.math.abs

class BuildDailyAdviceUseCase @Inject constructor() {
    operator fun invoke(remaining: Int): DailyAdvice = when {
        remaining > 100 -> DailyAdvice(
            DailyAdviceType.NEED_MORE,
            remaining
        )

        remaining < -100 -> DailyAdvice(
            DailyAdviceType.OVER,
            remaining.magnitude()
        )

        else -> DailyAdvice(
            DailyAdviceType.ON_TARGET,
            remaining.magnitude()
        )
    }

    private fun Int.magnitude(): Int =
        if (this == Int.MIN_VALUE) Int.MAX_VALUE else abs(this)
}
