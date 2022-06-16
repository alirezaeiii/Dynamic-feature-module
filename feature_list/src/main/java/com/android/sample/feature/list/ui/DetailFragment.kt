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
import com.android.sample.feature.list.di.DaggerDetailComponent
import com.android.sample.feature.list.di.DetailModule
import com.android.sample.feature.list.viewmodel.DetailViewModel
import com.android.sample.app.Application
import com.android.sample.feature.list.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>
    (R.layout.fragment_detail, BR.vm) {
    /**
     * Initialize dagger injection dependency graph.
     */
    override fun onInitDependencyInjection() {
        DaggerDetailComponent
            .builder()
            .coreComponent(Application.coreComponent(requireContext()))
            .detailModule(DetailModule(this))
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        with(binding) {

            viewModel.liveData.observe(viewLifecycleOwner) { resource ->
                if (resource is ViewState.Success) {
                    textTitle.text = resource.data?.title
                    textDescription.text = resource.data?.subTitle
                }
            }

            toolbar.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
            }
        }

        return binding.root
    }
}