package com.example.shoppingapp.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.shoppingapp.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()
    private val tabsViewModel: TabsViewModel by activityViewModels()

    private var userId: Long? = null


    private val getPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) getPhoto.launch(IMAGE_MIME_TYPE)
        }

    private val getPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            imageUri?.let {
                viewModel.updateUserPhoto(imageUri.toString(), userId)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.logOutBtn.setOnClickListener {
            viewModel.logOut()
        }
        binding.changePhotoBtn.setOnClickListener {
            if (!checkPermission()) {
                getPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                getPhoto.launch(IMAGE_MIME_TYPE)
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            tabsViewModel.userInfo.collect { userInfo ->
                userInfo?.let {
                    userId = userInfo.id
                    binding.userName.text = userInfo.name + " " + userInfo.surname
                    userInfo.photoUri?.let {
                        Glide.with(requireActivity())
                            .load(userInfo.photoUri!!.toUri())
                            .into(binding.userPhoto)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

    }


    companion object {
        const val IMAGE_MIME_TYPE = "image/*"
    }


}