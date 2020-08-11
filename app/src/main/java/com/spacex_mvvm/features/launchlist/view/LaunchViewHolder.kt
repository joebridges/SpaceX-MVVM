package com.spacex_mvvm.features.launchlist.view

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.spacex_mvvm.R
import com.spacex_mvvm.databinding.ViewHolderLaunchBinding
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.ui.GlideApp

class LaunchViewHolder(
    private val binding: ViewHolderLaunchBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launchListItem: LaunchListItem) {
        bindMissionPatch(launchListItem.missionPatchImageUrl)
        binding.launchDate.text = launchListItem.localDate
        binding.missionName.text = launchListItem.missionName
    }

    private fun bindMissionPatch(missionPatchUrl: String?) {
        binding.missionPatchImageView.isVisible = !missionPatchUrl.isNullOrEmpty()
        if (!missionPatchUrl.isNullOrEmpty()) {
            GlideApp.with(binding.missionPatchImageView.context)
                .load(missionPatchUrl)
                .placeholder(R.drawable.ic_mission_patch_placeholder)
                .into(binding.missionPatchImageView)
        }
    }
}