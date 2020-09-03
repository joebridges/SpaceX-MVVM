package com.spacex_mvvm.features.launchlist.view

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.Hold
import com.spacex_mvvm.R
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.databinding.FragmentLaunchListBinding
import com.spacex_mvvm.extensions.scrollToTopOnItemInserted
import com.spacex_mvvm.features.launchdetails.view.LaunchDetailsFragmentArgs
import com.spacex_mvvm.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_launch_list.*

@AndroidEntryPoint
class LaunchListFragment : Fragment(R.layout.fragment_launch_list) {

    private val viewModel: LaunchListViewModel by viewModels()

    private val args: LaunchListFragmentArgs by navArgs()

    private val binding by viewBinding(FragmentLaunchListBinding::bind)

    private lateinit var errorSnackbar: Snackbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val adapter = LaunchAdapter { launch, containerViewGroup ->
            navigateToLaunchDetails(launch.id, containerViewGroup)
        }

        setUpRecyclerView(adapter)

        errorSnackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_INDEFINITE)

        val launchEra = getLaunchEra()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadLaunchesForEra(
                launchEra,
                forceRefresh = true
            )
        }

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            swipeRefresh.isRefreshing = isLoading
        })

        viewModel.launches.observe(viewLifecycleOwner, Observer { launches ->
            adapter.submitList(launches)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            bindErrorMessage(errorMessage)
        })

        viewModel.loadLaunchesForEra(launchEra)
    }

    private fun setUpRecyclerView(adapter: LaunchAdapter) {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter.scrollToTopOnItemInserted(layoutManager)
        binding.recyclerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }

    private fun bindErrorMessage(errorMessage: String?) {
        if (!errorMessage.isNullOrEmpty()) {
            errorSnackbar.setText(errorMessage).show()
        } else {
            errorSnackbar.dismiss()
        }
    }

    private fun getLaunchEra(): LaunchEra {
        return when (args.launchEra) {
            PAST_LAUNCHES_STRING -> LaunchEra.PAST
            UPCOMING_LAUNCHES_STRING -> LaunchEra.UPCOMING
            else -> throw IllegalArgumentException("Unrecognised launch era type ${args.launchEra}")
        }
    }

    private fun navigateToLaunchDetails(launchId: String, viewGroup: View) {
        exitTransition = Hold().apply {
            duration = 500L
        }

        val action = LaunchListFragmentDirections.actionLaunchListToLaunchDetails(launchId)
        val extras = FragmentNavigatorExtras(viewGroup to launchId)
        findNavController().navigate(action, extras)
    }

    companion object {
        const val PAST_LAUNCHES_STRING = "past"
        const val UPCOMING_LAUNCHES_STRING = "upcoming"
    }
}
