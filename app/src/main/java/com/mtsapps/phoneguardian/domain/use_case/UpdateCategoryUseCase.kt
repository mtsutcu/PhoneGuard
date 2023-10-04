package com.mtsapps.phoneguardian.domain.use_case

import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.repository.ContactRepositoryImpl
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(private val contactRepositoryImpl: ContactRepositoryImpl) {
    suspend operator fun invoke(category: Category) = contactRepositoryImpl.updateCategory(category)
}