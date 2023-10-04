package com.mtsapps.phoneguardian.data.repository

import com.mtsapps.phoneguardian.core.room.AppDatabase
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) :
    ContactRepository {
    override fun getCategoriesWithContacts(): Flow<List<CategoryWithContacts>> =
        appDatabase.categoryDao().getCategoriesWithContacts()

    override fun getCategories(): Flow<List<Category>> = appDatabase.categoryDao().getCategories()

    override fun getActiveCategories(): Flow<List<Category>> =
        appDatabase.categoryDao().getActiveCategories()

    override suspend fun getPhoneNumbersByCategory(categoryID: Long): List<String> =
        appDatabase.personDao().getPhoneNumbersByCategory(categoryID = categoryID)

    override suspend fun updateContactCategory(contact: Contact, categoryID: Long) =
        appDatabase.categoryDao().updateContactCategory(categoryID = categoryID, contact = contact)

    override suspend fun insertContact(contact: Contact) {
        appDatabase.personDao().insterContact(contact = contact)
    }

    override suspend fun insertContactWithCategory(category: Category, contact: Contact) {
        appDatabase.categoryDao().instertContactWithCategory(category = category,contact=contact)
    }

    override suspend fun deleteContact(contact: Contact) =
        appDatabase.personDao().deleteContact(contact)

    override suspend fun updateCategory(category: Category) =appDatabase.categoryDao().updateCategory(category=category)
    override suspend fun getAllContacts(): Flow<List<Contact>> =
        appDatabase.personDao().getContacts()

    override suspend fun getAllPhoneNumbers(): Flow<List<String>> =
        appDatabase.personDao().getPhoneNumbers()


    override suspend fun insertContactInCategory(contact: Contact, categoryID: Long) {
        appDatabase.categoryDao()
            .insertContactInCategory(categoryID = categoryID, contact = contact)
    }
}