package com.mtsapps.phoneguardian.core.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtsapps.phoneguardian.data.entities.CallContact
import kotlinx.coroutines.flow.Flow

@Dao
interface CallContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertCallContacts(callContacList  : List<CallContact>) : List<Long>

    @Query("SELECT * FROM call_contact_table WHERE type =:type")
    fun getCallContacts(type : String) : Flow<List<CallContact>>

    @Query("DELETE FROM call_contact_table")
    suspend fun deleteAll()

}