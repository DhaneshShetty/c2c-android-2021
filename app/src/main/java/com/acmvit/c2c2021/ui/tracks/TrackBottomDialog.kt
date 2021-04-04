package com.acmvit.c2c2021.ui.tracks

import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.navigation.navGraphViewModels
import com.acmvit.c2c2021.BuildConfig
import com.acmvit.c2c2021.R
import com.acmvit.c2c2021.databinding.DialogBottomTracksBinding
import com.acmvit.c2c2021.util.openPdf
import com.acmvit.c2c2021.util.showSnackbar
import com.acmvit.c2c2021.viewmodels.TracksViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class TrackBottomDialog: BottomSheetDialogFragment() {
    private lateinit var binding: DialogBottomTracksBinding
    private val viewModel by navGraphViewModels<TracksViewModel>(R.id.nav_tracks)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DialogBottomTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        initObservers()
    }

    private fun initObservers() {
        viewModel.selectedTrack.observe(viewLifecycleOwner) { if (it == null) dismiss() }

        viewModel.dialogViewEffect.observe(viewLifecycleOwner) {
            when(it) {
                is TracksViewModel.ViewEffect.ShowSnackbar -> {
                    showSnackbar(binding.root, it.msg, it.actionName, it.action)
                }

                is TracksViewModel.ViewEffect.OpenPdf -> {
                    context?.openPdf(it.file)
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.selectTrack(-1)
    }

}