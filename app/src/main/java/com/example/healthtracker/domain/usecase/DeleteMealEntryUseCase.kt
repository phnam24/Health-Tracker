package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.CancellationException
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject

class DeleteMealEntryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val clock: Clock,
) {
    suspend operator fun invoke(entry: MealEntry): Result<Unit> {
        if (entry.date != LocalDate.now(clock)) {
            return Result.failure(MealEntryDeletionNotAllowedException())
        }

        return try {
            diaryRepository.deleteEntry(entry)
            Result.success(Unit)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}

class MealEntryDeletionNotAllowedException : IllegalArgumentException(
    "Only meal entries from today can be deleted",
)
