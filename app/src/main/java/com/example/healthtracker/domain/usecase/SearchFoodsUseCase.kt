package com.example.healthtracker.domain.usecase

import com.example.healthtracker.domain.model.Food
import com.example.healthtracker.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchFoodsUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {
    operator fun invoke(query: String): Flow<List<Food>> =
        foodRepository.searchFoods(query.trim())
}