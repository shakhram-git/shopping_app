package com.example.shoppingapp.presentation

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentHomeBinding
import com.example.shoppingapp.presentation.adapter_delegates.AdapterDelegates.customListDelegate
import com.example.shoppingapp.presentation.adapter_delegates.AdapterDelegates.productsCategoryDelegate
import com.example.shoppingapp.presentation.model.ListItem
import com.example.shoppingapp.presentation.model.ProductsCategory
import com.example.shoppingapp.presentation.utils.Utils.dpToPixel
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val tabsViewModel: TabsViewModel by activityViewModels()


    private lateinit var categories: List<ProductsCategory>
    private lateinit var searchListAdapter: ArrayAdapter<String>


    private val categoriesAdapter = ListDelegationAdapter(
        productsCategoryDelegate()
    )

    private lateinit var mainAdapter: ListDelegationAdapter<List<ListItem>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categories = listOf(
            ProductsCategory(getString(R.string.phones), R.drawable.icon_phone),
            ProductsCategory(getString(R.string.headphones), R.drawable.icon_headphones),
            ProductsCategory(getString(R.string.games), R.drawable.icon_controller),
            ProductsCategory(getString(R.string.cars), R.drawable.icon_car),
            ProductsCategory(getString(R.string.furniture), R.drawable.icon_bed),
            ProductsCategory(getString(R.string.kids), R.drawable.icon_robot)
        )
        mainAdapter =
            ListDelegationAdapter(customListDelegate(requireContext()) { onProductClick() })
        searchListAdapter = ArrayAdapter(requireContext(), R.layout.item_search_hint)
        searchListAdapter.setNotifyOnChange(true)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.iconContainer.setOnClickListener {
            tabsViewModel.goToProfileNavGraph()
        }
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            viewModel.getSearchHints(text.toString())
        }
        binding.collectionsList.adapter = mainAdapter

        binding.productCategories.adapter = categoriesAdapter

        binding.searchInput.setAdapter(searchListAdapter)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoriesAdapter.items = categories
        binding.productCategories.addItemDecoration(
            categoriesDivider(
                requireContext(),
                categories.size
            )
        )
        categoriesAdapter.notifyDataSetChanged()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.networkData.collect { products ->
                mainAdapter.items = products
                binding.collectionsList.addItemDecoration(
                    listsDivider(
                        requireContext(),
                        products.size
                    )
                )
                mainAdapter.notifyDataSetChanged()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productsName.collect { productsName ->
                searchListAdapter.addAll(productsName)
                binding.searchInput.showDropDown()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            tabsViewModel.userInfo.collect { userInfo ->
                userInfo?.let {
                    userInfo.photoUri?.let {
                        Glide.with(requireActivity())
                            .load(userInfo.photoUri!!.toUri())
                            .into(binding.userIcon)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingState.collect { state ->
                binding.progressBar.isVisible = state == HomeViewModel.State.LOADING
                binding.collectionsList.isVisible = state == HomeViewModel.State.SUCCESS
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect { error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchState.collect { state ->
                binding.searchBarProgress.isVisible = state == HomeViewModel.State.LOADING
                binding.searchIcon.isVisible = state != HomeViewModel.State.LOADING
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun onProductClick() {
        findNavController().navigate(R.id.action_homeFragment_to_productFragment)
    }

    private fun listsDivider(context: Context, listSize: Int) =
        object : RecyclerView.ItemDecoration() {
            private val outerTopSpace = context.dpToPixel(24)
            private val innerSpace = context.dpToPixel(16)
            private val outerBottomSpace = context.dpToPixel(65)
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                outRect.top = if (position == 0) outerTopSpace else innerSpace
                outRect.bottom = if (position == listSize - 1) outerBottomSpace else 0
            }
        }

    private fun categoriesDivider(context: Context, listSize: Int) =
        object : RecyclerView.ItemDecoration() {
            private val innerSpace = context.dpToPixel(16)
            private val outerSpace = context.dpToPixel(11)
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val position = parent.getChildAdapterPosition(view)
                    .let { if (it == RecyclerView.NO_POSITION) return else it }
                outRect.left = if (position == 0) outerSpace else innerSpace
                outRect.right = if (position == listSize - 1) outerSpace else 0
            }

        }

    private fun onSearchHintClick(text: String) {
        binding.searchInput.setText(text)

    }

}