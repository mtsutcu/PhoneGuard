package com.mtsapps.phoneguardian.domain.use_case

import androidx.datastore.preferences.core.Preferences
import com.mtsapps.phoneguardian.data.repository.DataStoreRepositoryImpl
import javax.inject.Inject

class UpdateBlockStatusUseCase @Inject constructor(private val dataStoreRepositoryImpl: DataStoreRepositoryImpl) {
    suspend operator fun invoke(key: Preferences.Key<Boolean>,status:Boolean) = dataStoreRepositoryImpl.updateBlockStatus(key = key,status =status)
}