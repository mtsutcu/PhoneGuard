package com.mtsapps.phoneguardian.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_contact_table")
data class CallContact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("callContactID")
    val callContactID : Long? =null,
    @ColumnInfo("name")
    val name : String?=null,
    @ColumnInfo("number")
    val number : String?=null,
    @ColumnInfo("photoUri")
    val photoUri : String?=null,
    @ColumnInfo("type")
    val type : String? = null
)