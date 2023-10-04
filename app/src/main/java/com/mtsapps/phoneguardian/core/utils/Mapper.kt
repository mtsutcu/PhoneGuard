package com.mtsapps.phoneguardian.core.utils

interface Mapper<E, D> {
    fun mapFromEntity(type: E): D
    fun mapToEntity(type: D): E
}