package com.mtsapps.phoneguardian.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithContacts(
    @Embedded val category: Category,
    @Relation(parentColumn = "categoryID", entityColumn = "categoryID")
    val contacts: List<Contact>,
)
