package com.mtsapps.phoneguardian.domain.use_case.contact_use_case

import com.mtsapps.phoneguardian.data.entities.Contact
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class InsertContactUseCase @Inject constructor(private val contactRepositoryImpl: ContactRepositoryImpl) {
    suspend operator fun invoke(contact: Contact) = contactRepositoryImpl.insertContact(contact = contact)
}