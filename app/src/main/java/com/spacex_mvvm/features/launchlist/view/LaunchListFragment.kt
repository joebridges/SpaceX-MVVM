package com.spacex_mvvm.features.launchlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spacex_mvvm.SpaceXApplication
import com.spacex_mvvm.data.repositories.launches.model.LaunchEra
import com.spacex_mvvm.databinding.FragmentLaunchListBinding
import kotlinx.android.synthetic.main.fragment_launch_list.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class LaunchListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: LaunchListFragmentArgs by navArgs()

    private lateinit var binding: FragmentLaunchListBinding

    private lateinit var errorSnackbar: Snackbar

    private val adapter = LaunchAdapter()

    private val viewModel: LaunchListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inject()

        binding = FragmentLaunchListBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        errorSnackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_INDEFINITE)

        val launchEra = getLaunchTimeEra()

        swipeRefresh.setOnRefreshListener {
            viewModel.loadLaunchesForEra(
                launchEra,
                forceRefresh = true
            )
        }

        viewModel.launches.observe(viewLifecycleOwner, Observer { launches ->
            adapter.submitList(launches)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            bindErrorMessage(errorMessage)
        })

        viewModel.loadLaunchesForEra(launchEra)
    }

    private fun inject() {
        SpaceXApplication.get().appComponent.inject(this)
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@LaunchListFragment.adapter
        }
    }

    private fun bindErrorMessage(errorMessage: String?) {
        if (!errorMessage.isNullOrEmpty()) {
            errorSnackbar.setText(errorMessage).show()
        } else {
            errorSnackbar.dismiss()
        }
    }

    private fun getLaunchTimeEra(): LaunchEra {
        return when (args.launchEra) {
            PAST_LAUNCHES_STRING -> LaunchEra.PAST
            UPCOMING_LAUNCHES_STRING -> LaunchEra.UPCOMING
            else -> throw IllegalArgumentException("Unrecognised launch era type ${args.launchEra}")
        }
    }

    companion object {
        const val PAST_LAUNCHES_STRING = "past"
        const val UPCOMING_LAUNCHES_STRING = "upcoming"
    }
}
