package com.mtsapps.phoneguardian.core.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.Contact

@Database(entities = [Contact::class,Category::class,CallContact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun personDao(): ContactDao
    abstract fun callContactDao():CallContactDao

}