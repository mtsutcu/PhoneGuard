package com.mtsapps.phoneguardian.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.mtsapps.phoneguardian.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(private val dataStore: DataStore<Preferences>) :
    DataStoreRepository {
    override suspend fun getBooleanValue(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { pref ->
            pref[key] ?: true
        }
    }

    override suspend fun updateValue(key: Preferences.Key<Boolean>, status: Boolean) {
        dataStore.updateData {
            val pref =it.toMutablePreferences()
            pref.set(key = key, value = status)
            pref.toPreferences()
            return@updateData pref  }
    }


}