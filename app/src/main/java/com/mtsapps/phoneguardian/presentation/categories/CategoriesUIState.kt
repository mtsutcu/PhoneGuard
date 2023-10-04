package com.mtsapps.phoneguardian.presentation.categories

import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts

data class CategoriesUIState(
    val categoryList: List<Category>? = null,
    val categoryWithContactsList: List<CategoryWithContacts>? = null,
)