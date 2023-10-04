package com.mtsapps.phoneguardian.domain.use_case

import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val contactRepositoryImpl: ContactRepositoryImpl) {
     operator fun invoke() = contactRepositoryImpl.getCategories()
}