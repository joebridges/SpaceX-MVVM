package com.spacex_mvvm.data.mappers

interface EntityMapper<DbEntity, Model> {
    fun mapToEntity(model: Model): DbEntity
    fun mapFromEntity(entity: DbEntity): Model
}