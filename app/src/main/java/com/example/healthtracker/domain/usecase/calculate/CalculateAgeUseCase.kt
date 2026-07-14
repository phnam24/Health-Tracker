package com.example.healthtracker.domain.usecase.calculate

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period

class CalculateAgeUseCase {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        birthDate: LocalDate,
        today: LocalDate = LocalDate.now()
    ): Int {
        require(!birthDate.isAfter(today)) { "Birth date cannot be in the future" }
        return Period.between(birthDate, today).years
    }
}
