package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.MealEntry
import com.example.healthtracker.domain.repository.DiaryRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class DeleteMealEntryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(entry: MealEntry): Result<Unit> = try {
        diaryRepository.deleteEntry(entry)
        Result.success(Unit)
    } catch (cancellation: CancellationException) {
        throw cancellation
    } catch (error: Exception) {
        Result.failure(error)
    }
}
