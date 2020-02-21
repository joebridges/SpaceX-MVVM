package com.spacex_mvvm.features.launchlist.view

import androidx.recyclerview.widget.RecyclerView
import com.spacex_mvvm.databinding.ViewHolderLaunchBinding
import com.spacex_mvvm.features.launchlist.usecase.LaunchListItem

class LaunchViewHolder(
    private val binding: ViewHolderLaunchBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(launchListItem: LaunchListItem) {
        binding.launch = launchListItem
        binding.executePendingBindings()
    }
}