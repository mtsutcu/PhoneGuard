package com.mtsapps.phoneguardian.presentation.contacts

import com.mtsapps.phoneguardian.data.entities.CallContact
import com.mtsapps.phoneguardian.data.entities.Category
import com.mtsapps.phoneguardian.data.entities.CategoryWithContacts
import com.mtsapps.phoneguardian.data.entities.Contact

data class PersonsUIState(
    val categoryList : List<Category>? = null,
    val categoryListWithPersons: List<CategoryWithContacts>? = null,
    val contactList: List<Contact>? = null,
    val localContactList : List<CallContact>? = emptyList(),
    val incomingCalls : List<CallContact>? = emptyList(),
    val outgoingCalls : List<CallContact>? = emptyList(),
    val missedCalls : List<CallContact>? = emptyList(),
    val phoneNumbersList : List<String>? = null,
)
