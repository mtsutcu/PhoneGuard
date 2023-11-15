package com.mtsapps.phoneguardian.domain.use_case.pref_data_store_use_case

import androidx.datastore.preferences.core.Preferences
import com.mtsapps.phoneguardian.data.repository.DataStoreRepositoryImpl
import javax.inject.Inject

class UpdateBooleanPrefUseCase @Inject constructor(private val dataStoreRepositoryImpl: DataStoreRepositoryImpl) {
    suspend operator fun invoke(key: Preferences.Key<Boolean>,status:Boolean) = dataStoreRepositoryImpl.updateValue(key = key,status =status)
}