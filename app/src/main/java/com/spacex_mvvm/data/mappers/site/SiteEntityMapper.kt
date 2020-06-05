package com.spacex_mvvm.data.mappers.site

import com.spacex_mvvm.data.database.sites.SiteEntity
import com.spacex_mvvm.data.mappers.EntityMapper
import com.spacex_mvvm.data.repositories.launches.model.Site
import javax.inject.Inject

class SiteEntityMapper @Inject constructor() : EntityMapper<SiteEntity, Site> {

    override fun mapToEntity(model: Site): SiteEntity {
        return with(model) {
            SiteEntity(id, siteName)
        }
    }

    override fun mapFromEntity(entity: SiteEntity): Site {
        return with(entity) {
            Site(id, siteName)
        }
    }
}