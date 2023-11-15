package com.mtsapps.phoneguardian.data.repository

import com.mtsapps.phoneguardian.core.room.AppDatabase
import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.domain.repository.CallContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CallContactRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : CallContactRepository {
    override fun getCallContacts(type : String): Flow<List<CallContact>> = appDatabase.callContactDao().getCallContacts(type)
    override suspend fun insertCallContacts(callContactList: List<CallContact>): List<Long> =  appDatabase.callContactDao().insertCallContacts(callContactList)
    override suspend fun deleteAll() = appDatabase.callContactDao().deleteAll()
}