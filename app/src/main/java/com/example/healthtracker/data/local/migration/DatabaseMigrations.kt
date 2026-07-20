package com.example.healthtracker.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        migrateActivityEntries(db)
        migrateMealEntries(db)
    }
}

private fun migrateActivityEntries(db: SupportSQLiteDatabase) {
    db.execSQL(
        """
        CREATE TABLE IF NOT EXISTS `activity_entry_new` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `date` INTEGER NOT NULL,
            `activityTypeId` INTEGER NOT NULL,
            `activityName` TEXT NOT NULL,
            `activityNameEn` TEXT NOT NULL,
            `durationMinutes` INTEGER NOT NULL,
            `caloriesBurned` INTEGER NOT NULL
        )
        """.trimIndent()
    )
    db.execSQL(
        """
        INSERT INTO `activity_entry_new` (
            `id`,
            `date`,
            `activityTypeId`,
            `activityName`,
            `activityNameEn`,
            `durationMinutes`,
            `caloriesBurned`
        )
        SELECT
            entry.`id`,
            entry.`date`,
            entry.`activityTypeId`,
            entry.`activityName`,
            COALESCE(NULLIF(type.`nameEn`, ''), entry.`activityName`),
            entry.`durationMinutes`,
            entry.`caloriesBurned`
        FROM `activity_entry` AS entry
        LEFT JOIN `activity_type` AS type
            ON type.`id` = entry.`activityTypeId`
        """.trimIndent()
    )
    db.execSQL("DROP TABLE `activity_entry`")
    db.execSQL("ALTER TABLE `activity_entry_new` RENAME TO `activity_entry`")
    db.execSQL(
        "CREATE INDEX IF NOT EXISTS `index_activity_entry_date` " +
            "ON `activity_entry` (`date`)"
    )
}

private fun migrateMealEntries(db: SupportSQLiteDatabase) {
    db.execSQL(
        """
        CREATE TABLE IF NOT EXISTS `meal_entry_new` (
            `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
            `date` INTEGER NOT NULL,
            `mealType` TEXT NOT NULL,
            `foodId` INTEGER NOT NULL,
            `foodName` TEXT NOT NULL,
            `foodNameEn` TEXT NOT NULL,
            `quantity` REAL NOT NULL,
            `calories` INTEGER NOT NULL
        )
        """.trimIndent()
    )
    db.execSQL(
        """
        INSERT INTO `meal_entry_new` (
            `id`,
            `date`,
            `mealType`,
            `foodId`,
            `foodName`,
            `foodNameEn`,
            `quantity`,
            `calories`
        )
        SELECT
            entry.`id`,
            entry.`date`,
            entry.`mealType`,
            entry.`foodId`,
            entry.`foodName`,
            COALESCE(NULLIF(food.`nameEn`, ''), entry.`foodName`),
            entry.`quantity`,
            entry.`calories`
        FROM `meal_entry` AS entry
        LEFT JOIN `food`
            ON food.`id` = entry.`foodId`
        """.trimIndent()
    )
    db.execSQL("DROP TABLE `meal_entry`")
    db.execSQL("ALTER TABLE `meal_entry_new` RENAME TO `meal_entry`")
    db.execSQL(
        "CREATE INDEX IF NOT EXISTS `index_meal_entry_date` " +
            "ON `meal_entry` (`date`)"
    )
}
