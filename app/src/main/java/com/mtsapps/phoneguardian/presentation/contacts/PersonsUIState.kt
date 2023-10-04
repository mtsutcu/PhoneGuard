package com.mtsapps.phoneguardian.presentation.contacts

import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact

data class PersonsUIState(
    val categoryList : List<Category>? = null,
    val categoryListWithPersons: List<CategoryWithContacts>? = null,
    val contactList: List<Contact>? = null,
    val phoneNumbersList : List<String>? = null,
)
