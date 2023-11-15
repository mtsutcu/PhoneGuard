package com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case

import com.mtsapps.phoneguardian.data.repository.CallContactRepositoryImpl
import javax.inject.Inject

class GetCallContactsUseCase @Inject constructor(private val callContactRepositoryImpl: CallContactRepositoryImpl) {
    operator fun invoke(type : String) = callContactRepositoryImpl.getCallContacts(type)
}