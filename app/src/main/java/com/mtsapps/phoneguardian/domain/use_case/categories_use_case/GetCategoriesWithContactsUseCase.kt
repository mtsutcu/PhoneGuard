package com.mtsapps.phoneguardian.domain.use_case.categories_use_case

import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class GetCategoriesWithContactsUseCase @Inject constructor(private val contactRepositoryImpl: ContactRepositoryImpl) {
     operator fun invoke() = contactRepositoryImpl.getCategoriesWithContacts()
}