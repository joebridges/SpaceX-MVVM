package com.spacex_mvvm.features.launchlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.spacex_mvvm.SpaceXApplication
import com.spacex_mvvm.databinding.FragmentLaunchListBinding
import kotlinx.android.synthetic.main.fragment_launch_list.*
import javax.inject.Inject

class LaunchListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var errorSnackbar: Snackbar

    private val viewModel: LaunchListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inject()

        val binding = FragmentLaunchListBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchesRecycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = LaunchAdapter()
        launchesRecycler.adapter = adapter

        errorSnackbar = Snackbar.make(requireView(), "", Snackbar.LENGTH_INDEFINITE)

        viewModel.launches.observe(viewLifecycleOwner, Observer { launches ->
            adapter.submitList(launches)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            bindErrorMessage(errorMessage)
        })
    }

    private fun inject() {
        SpaceXApplication.get().appComponent.inject(this)
    }

    private fun bindErrorMessage(errorMessage: String?) {
        if (!errorMessage.isNullOrEmpty()) {
            errorSnackbar.setText(errorMessage).show()
        } else {
            errorSnackbar.dismiss()
        }
    }
}
