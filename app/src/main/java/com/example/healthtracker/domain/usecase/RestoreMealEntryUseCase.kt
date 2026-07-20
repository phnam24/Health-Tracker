package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class RestoreMealEntryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(entry: MealEntry): Result<Long> = try {
        Result.success(diaryRepository.addEntry(entry.copy(id = 0)))
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (error: Exception) {
        Result.failure(error)
    }
}
