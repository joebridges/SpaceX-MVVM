package com.spacex_mvvm.features.launchdetails.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.spacex_mvvm.R
import com.spacex_mvvm.databinding.FragmentLaunchDetailsBinding
import com.spacex_mvvm.features.launchdetails.model.LaunchDetails
import com.spacex_mvvm.ui.GlideApp
import com.spacex_mvvm.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.fragment_launch_details) {

    private val viewModel: LaunchDetailsViewModel by viewModels()

    private val args by navArgs<LaunchDetailsFragmentArgs>()

    private val binding by viewBinding(FragmentLaunchDetailsBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            duration = 500L
            setAllContainerColors(requireContext().getColor(R.color.color_surface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.transitionName = args.launchId

        viewModel.launchDetails.observe(viewLifecycleOwner, Observer { launchDetails ->
            if (launchDetails != null) {
                bindLaunch(launchDetails)
            }
        })

        viewModel.loadLaunch(args.launchId)
    }

    private fun bindLaunch(launchDetails: LaunchDetails) {
        binding.launchName.text = launchDetails.name
        binding.launchDescription.text = launchDetails.details
        bindMissionPatchImage(launchDetails.missionPatchImageUrl)
        bindLaunchImage(launchDetails.imageUrl)
    }

    private fun bindMissionPatchImage(missionPatchUrl: String?) {
        binding.missionPatchImageView.isVisible = !missionPatchUrl.isNullOrEmpty()
        if (!missionPatchUrl.isNullOrEmpty()) {
            GlideApp.with(requireContext())
                .load(missionPatchUrl)
                .into(binding.missionPatchImageView)
        }
    }

    private fun bindLaunchImage(launchImageUrl: String?) {
        binding.launchImageView.isVisible = !launchImageUrl.isNullOrEmpty()
        if (!launchImageUrl.isNullOrEmpty()) {
            GlideApp.with(requireContext())
                .load(launchImageUrl)
                .into(binding.launchImageView)
        }
    }
}