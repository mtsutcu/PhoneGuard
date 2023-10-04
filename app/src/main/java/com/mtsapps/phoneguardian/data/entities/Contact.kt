package com.mtsapps.phoneguardian.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "contact_table", foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["categoryID"],
            childColumns = ["categoryID"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Contact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("contactID")
    val contactID: Long = 0,
    @ColumnInfo("categoryID")
    val categoryID : Long = 0,
    @ColumnInfo("name")
    val name: String?=null,
    @ColumnInfo("number")
    val number: String?=null,
    @ColumnInfo("photoUri")
    val photoUri: String?=null,
)
