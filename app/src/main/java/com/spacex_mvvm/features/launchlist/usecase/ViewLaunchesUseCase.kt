package com.spacex_mvvm.features.launchlist.usecase

import com.spacex_mvvm.data.Resource
import com.spacex_mvvm.data.repositories.launches.LaunchesRepository
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.data.repositories.launches.model.LaunchesDateOrder
import com.spacex_mvvm.extensions.mapData
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.features.launchlist.model.LaunchListItemsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ViewLaunchesUseCase @Inject constructor(
    private val repository: LaunchesRepository,
    private val launchListItemsMapper: LaunchListItemsMapper
) {
    fun loadLaunches(
        launchEra: LaunchEra,
        forceRefresh: Boolean = false
    ): Flow<Resource<List<LaunchListItem>>> {
        val launchesOrder =
            if (launchEra == LaunchEra.PAST) LaunchesDateOrder.DESCENDING else LaunchesDateOrder.ASCENDING
        return repository.loadLaunches(launchEra, launchesOrder, forceRefresh).map { resource ->
            resource.mapData { launches ->
                launchListItemsMapper.mapToListItems(launches)
            }
        }
    }
}