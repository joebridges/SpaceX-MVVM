package com.spacex_mvvm.features.launchlist.view

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.spacex_mvvm.R
import com.spacex_mvvm.databinding.ViewHolderLaunchBinding
import com.spacex_mvvm.features.launchlist.model.LaunchListItem
import com.spacex_mvvm.ui.GlideApp

class LaunchViewHolder(
    private val binding: ViewHolderLaunchBinding,
    private val onClickListener: (LaunchListItem, View) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launchListItem: LaunchListItem) {
        bindMissionPatch(launchListItem.missionPatchImageUrl)
        binding.launchDate.text = launchListItem.localDate
        binding.missionName.text = launchListItem.name
        binding.root.setOnClickListener {
            onClickListener.invoke(launchListItem, binding.root)
        }
        bindContainerTransitionName(launchListItem.id)
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

    private fun bindContainerTransitionName(transitionId: String) {
        binding.root.transitionName = transitionId
    }
}