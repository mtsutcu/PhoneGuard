package com.mtsapps.phoneguardian.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun  getBooleanValue(key:Preferences.Key<Boolean>) : Flow<Boolean>
    suspend fun updateValue(key: Preferences.Key<Boolean>, status : Boolean)
}