package com.mtsapps.phoneguardian.domain.use_case

import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(private val contactRepositoryImpl: ContactRepositoryImpl) {
    suspend operator fun invoke() = contactRepositoryImpl.getAllContacts()
}