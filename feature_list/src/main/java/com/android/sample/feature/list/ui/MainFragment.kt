package com.android.sample.feature.list.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.sample.common.base.BaseFragment
import com.android.sample.common.util.ViewState
import com.android.sample.feature.list.BR
import com.android.sample.feature.list.R
import com.android.sample.feature.list.di.DaggerMainComponent
import com.android.sample.feature.list.di.MainModule
import com.android.sample.feature.list.ui.MovieAdapter.OnClickListener
import com.android.sample.feature.list.viewmodel.MainViewModel
import com.android.sample.app.Application.Companion.coreComponent
import com.android.sample.feature.list.databinding.FragmentMainBinding

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>
    (R.layout.fragment_main, BR.vm) {

    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerMainComponent
            .builder()
            .coreComponent(coreComponent(requireContext()))
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val viewModelAdapter =
            MovieAdapter(OnClickListener { movie ->
                val destination =
                    MainFragmentDirections.actionMainFragmentToDetailFragment(movie)
                with(findNavController()) {
                    currentDestination?.getAction(destination.actionId)
                        ?.let { navigate(destination) }
                }
            })

        viewModel.liveData.observe(viewLifecycleOwner) { resource ->
            if (resource is ViewState.Success) {
                viewModelAdapter.submitList(resource.data?.movies)
                viewModelAdapter.baseImageUrl = resource.data?.imageBase
            }
        }

        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = viewModelAdapter
        }

        return binding.root
    }
}