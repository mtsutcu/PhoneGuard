package com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case

import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.data.repository.CallContactRepositoryImpl
import javax.inject.Inject

class InsertCallContactsUseCase @Inject constructor(private val callContactRepositoryImpl: CallContactRepositoryImpl) {
    suspend operator fun invoke(callContactList : List<CallContact>) = callContactRepositoryImpl.insertCallContacts(callContactList)
}