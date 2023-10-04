package com.mtsapps.phoneguardian.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun  getBlockStatus(key:Preferences.Key<Boolean>) : Flow<Boolean>
    suspend fun updateBlockStatus(key: Preferences.Key<Boolean>,status : Boolean)
}