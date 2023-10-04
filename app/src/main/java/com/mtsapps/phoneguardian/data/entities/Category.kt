package com.mtsapps.phoneguardian.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("categoryID")
    val categoryID: Long = 0,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("colorCode")
    val colorCode: String,
    @ColumnInfo("isActive")
    val isActive : Boolean,
    @ColumnInfo("isAlarm")
    val isAlarm : Boolean = false,
    @ColumnInfo("blockedStartTime")
    val blockedStartTime : String = "00:00",
    @ColumnInfo("blockedEndTime")
    val blockedEndTime : String = "00:00"
)
