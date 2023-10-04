package com.mtsapps.phoneguardian.domain.repository

import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getCategories() : Flow<List<Category>>
    fun getCategoriesWithContacts(): Flow<List<CategoryWithContacts>>
    fun getActiveCategories() : Flow<List<Category>>
    suspend fun updateContactCategory(persons: Contact, categoryID : Long)
    suspend fun getAllContacts() : Flow<List<Contact>>
    suspend fun getAllPhoneNumbers() : Flow<List<String>>
    suspend fun getPhoneNumbersByCategory(categoryID: Long) :List<String>
    suspend fun insertContactInCategory(persons: Contact, categoryID : Long)
    suspend fun insertContact(persons: Contact)
    suspend fun insertContactWithCategory(category: Category,contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun updateCategory(category: Category)

}

