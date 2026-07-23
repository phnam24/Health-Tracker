package com.example.healthtracker.core.time

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.time.Clock
import java.time.Duration
import java.time.LocalDate
import kotlin.time.Duration.Companion.milliseconds

fun currentDateFlow(clock: Clock): Flow<LocalDate> = flow {
    while (true) {
        val today = LocalDate.now(clock)
        emit(today)

        val nextDay = today.plusDays(1).atStartOfDay(clock.zone).toInstant()
        val delayMillis = Duration.between(clock.instant(), nextDay)
            .toMillis()
            .coerceAtLeast(MIN_ROLLOVER_DELAY_MILLIS)
        delay(delayMillis.milliseconds)
    }
}.distinctUntilChanged()

private const val MIN_ROLLOVER_DELAY_MILLIS = 1_000L
