package com.mtsapps.phoneguardian.core.room

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mtsapps.phoneguardian.data.entities.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Provider

class CategoryCallBack(private val provider: Provider<CategoryDao>) :
    RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch {
            val categoryList = listOf(
                Category(name = "Red", colorCode = "#c20e0e", isActive = true),
                Category(name = "Yellow", colorCode = "#dbd809", isActive = true),
                Category(name = "Blue", colorCode = "#0f34d6", isActive = true),
                Category(name = "Green", colorCode = "#09ba3b", isActive = true),
                Category(name = "Grey", colorCode = "#8d8e91", isActive = true)
            )
            categoryList.forEach {
                provider.get().insertCategory(it)
            }
        }
    }

}