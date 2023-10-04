package com.mtsapps.phoneguardian.domain.use_case

import androidx.datastore.preferences.core.Preferences
import com.mtsapps.phoneguardian.data.repository.DataStoreRepositoryImpl
import javax.inject.Inject

class GetBlockStatusUseCase @Inject constructor(private val dataStoreRepositoryImpl: DataStoreRepositoryImpl) {
    suspend operator fun invoke(key: Preferences.Key<Boolean>) = dataStoreRepositoryImpl.getBlockStatus(key = key)
}