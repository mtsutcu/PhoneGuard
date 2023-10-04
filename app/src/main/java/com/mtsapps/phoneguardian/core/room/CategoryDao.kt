package com.mtsapps.phoneguardian.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: Category): Long

    @Query("SELECT * FROM category_table WHERE categoryID =:id")
    suspend fun getCategoryById(id: Int): Category

    @Delete
    suspend fun deleteCategory(category: Category)

    @Insert
    suspend fun insertContact(contact : Contact)

    @Update
    suspend fun updateContact(contact: Contact)
    @Update
    suspend fun updateCategory(category: Category)
    @Transaction
    suspend fun instertContactWithCategory(category: Category, contact: Contact) {
        val categoryID = insertCategory(category)
        val personWithCategoryId = contact.copy(categoryID = categoryID)
        insertContact(contact = personWithCategoryId)
    }

    @Transaction
    suspend fun insertContactInCategory(categoryID: Long, contact: Contact) {
        val contactWithCategoryId = contact.copy(categoryID = categoryID)
        insertContact(contact = contactWithCategoryId)
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateContactCategory(categoryID : Long, contact: Contact) {
        val contactWithCategoryId = contact.copy(categoryID = categoryID)
        updateContact(contact = contactWithCategoryId)
    }

    @Transaction
    @Query("SELECT * FROM category_table")
    fun getCategoriesWithContacts(): Flow<List<CategoryWithContacts>>

    @Query("SELECT * FROM category_table where isActive = 1")
    fun getActiveCategories() : Flow<List<Category>>

    @Query("SELECT * FROM category_table")
    fun getCategories() : Flow<List<Category>>
}