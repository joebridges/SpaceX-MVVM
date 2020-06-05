package com.spacex_mvvm.data.mappers.rocket

import com.spacex_mvvm.data.database.rockets.RocketEntity
import com.spacex_mvvm.data.mappers.EntityMapper
import com.spacex_mvvm.data.repositories.launches.model.Rocket
import javax.inject.Inject

class RocketEntityMapper @Inject constructor() : EntityMapper<RocketEntity, Rocket> {

    override fun mapToEntity(model: Rocket): RocketEntity {
        return with(model) {
            RocketEntity(id, name, type)
        }
    }

    override fun mapFromEntity(entity: RocketEntity): Rocket {
        return with(entity) {
            Rocket(id, name, type)
        }
    }
}