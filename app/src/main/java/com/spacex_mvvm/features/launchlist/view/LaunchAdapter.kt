package com.spacex_mvvm.features.launchlist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.spacex_mvvm.databinding.ViewHolderLaunchBinding
import com.spacex_mvvm.features.launchlist.model.LaunchListItem

class LaunchAdapter(
    private val onClickListener: (LaunchListItem, View) -> Unit
): ListAdapter<LaunchListItem, LaunchViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderLaunchBinding.inflate(inflater, parent, false)
        return LaunchViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<LaunchListItem>() {
            override fun areItemsTheSame(
                oldItem: LaunchListItem,
                newItem: LaunchListItem
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: LaunchListItem,
                newItem: LaunchListItem
            ) = oldItem == newItem
        }
    }
}