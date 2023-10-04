package com.mtsapps.phoneguardian.core.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mtsapps.phoneguardian.data.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert
    suspend fun insterContact(contact: Contact)

    @Query("SELECT * FROM contact_table")
    fun getContacts() : Flow<List<Contact>>

    @Query("SELECT number FROM contact_table")
    fun getPhoneNumbers() : Flow<List<String>>

    @Query("SELECT number FROM contact_table WHERE categoryID =:categoryID")
   suspend fun getPhoneNumbersByCategory(categoryID : Long) : List<String>

    @Delete
    suspend fun deleteContact(contact: Contact)
}