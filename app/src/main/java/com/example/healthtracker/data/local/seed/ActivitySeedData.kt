package com.example.healthtracker.data.local.seed

import com.example.healthtracker.data.local.entity.ActivityTypeEntity

object ActivitySeedData {

    val items: List<ActivityTypeEntity> = listOf(
        ActivityTypeEntity(
            name = "Đi bộ",
            nameEn = "Walking",
            met = 3.5
        ),
        ActivityTypeEntity(
            name = "Chạy bộ",
            nameEn = "Running",
            met = 9.8
        ),
        ActivityTypeEntity(
            name = "Đạp xe",
            nameEn = "Cycling",
            met = 7.5
        ),
        ActivityTypeEntity(
            name = "Bơi lội",
            nameEn = "Swimming",
            met = 8.0
        ),
        ActivityTypeEntity(
            name = "Yoga",
            nameEn = "Yoga",
            met = 2.5
        ),
        ActivityTypeEntity(
            name = "Tập gym",
            nameEn = "Weight training",
            met = 6.0
        ),
        ActivityTypeEntity(
            name = "Leo cầu thang",
            nameEn = "Stair climbing",
            met = 8.8
        ),
        ActivityTypeEntity(
            name = "Nhảy dây",
            nameEn = "Jump rope",
            met = 11.0
        ),
        ActivityTypeEntity(
            name = "Cầu lông",
            nameEn = "Badminton",
            met = 5.5
        ),
        ActivityTypeEntity(
            name = "Bóng đá",
            nameEn = "Football",
            met = 7.0
        ),
        ActivityTypeEntity(
            name = "Bóng rổ",
            nameEn = "Basketball",
            met = 6.5
        ),
        ActivityTypeEntity(
            name = "Bóng chuyền",
            nameEn = "Volleyball",
            met = 4.0
        ),
        ActivityTypeEntity(
            name = "Tennis",
            nameEn = "Tennis",
            met = 7.3
        ),
        ActivityTypeEntity(
            name = "Khiêu vũ",
            nameEn = "Dancing",
            met = 4.8
        ),
        ActivityTypeEntity(
            name = "Võ thuật",
            nameEn = "Martial arts",
            met = 10.3
        ),
        ActivityTypeEntity(
            name = "Đi bộ nhanh",
            nameEn = "Brisk walking",
            met = 4.3
        )
    )
}