package com.mtsapps.phoneguardian.domain.repository

import com.mtsapps.phoneguardian.data.entities.CallContact
import kotlinx.coroutines.flow.Flow

interface CallContactRepository {
    fun getCallContacts(type : String) : Flow<List<CallContact>>
    suspend fun insertCallContacts(callContactList : List<CallContact>) : List<Long>
    suspend fun deleteAll()
}