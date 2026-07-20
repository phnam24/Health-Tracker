package com.example.healthtracker.domain.usecase

import java.time.LocalDate
import java.time.Period

class CalculateAgeUseCase {
    operator fun invoke(
        birthDate: LocalDate,
        today: LocalDate = LocalDate.now()
    ): Int {
        require(!birthDate.isAfter(today)) { "Birth date cannot be in the future" }
        return Period.between(birthDate, today).years
    }
}
