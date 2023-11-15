package com.mtsapps.phoneguardian.domain.use_case.call_contacts_use_case

import com.mtsapps.phoneguardian.data.repository.CallContactRepositoryImpl
import javax.inject.Inject

class DeleteAllCallContactsUseCase @Inject constructor(private val callContactRepositoryImpl: CallContactRepositoryImpl) {
    suspend operator fun invoke() = callContactRepositoryImpl.deleteAll()
}