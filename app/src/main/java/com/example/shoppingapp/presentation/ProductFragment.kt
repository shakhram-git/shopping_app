package com.example.shoppingapp.presentation

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.shoppingapp.R
import com.example.shoppingapp.databinding.FragmentProductBinding
import com.example.shoppingapp.presentation.adapter_delegates.AdapterDelegates.imageFullSizeSlidesDelegate
import com.example.shoppingapp.presentation.adapter_delegates.AdapterDelegates.imageSlidesDelegate
import com.example.shoppingapp.presentation.model.ImageSlide
import com.example.shoppingapp.presentation.utils.Utils.formatToCurrency
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.math.abs

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductViewModel by viewModels()
    private val tabsViewModel: TabsViewModel by activityViewModels()


    private var productName:String? = null


    private val sliderAdapter = ListDelegationAdapter(
        imageSlidesDelegate()
    )
    private val imageFullSizeAdapter = ListDelegationAdapter(
        imageFullSizeSlidesDelegate()
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.minusBtn.setOnClickListener {
            viewModel.removeOneItem()
        }
        binding.plusBtn.setOnClickListener {
            viewModel.addOneItem()
        }
        binding.addToCartBtn.setOnClickListener {
            tabsViewModel.goToCartNavGraph()
        }
        binding.shareBtn.setOnClickListener {
            ShareCompat.IntentBuilder(requireContext())
                .setType("text/plain")
                .setChooserTitle("Share URI")
                .setText("https://www.shoppingappexample.com/product")
                .startChooser()
        }
        setPhotoCarousel()
        binding.mainPhotoContainer.adapter = imageFullSizeAdapter
        binding.photosCarousel.registerOnPageChangeCallback(
            object : OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.mainPhotoContainer.setCurrentItem(position, true)
                }
            }
        )
        binding.mainPhotoContainer.registerOnPageChangeCallback(
            object : OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.photosCarousel.setCurrentItem(position, true)
                }
            }
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loadingState.collect { state ->
                binding.content.isVisible = state == ProductViewModel.State.SUCCESS
                binding.progressBar.isVisible = state == ProductViewModel.State.LOADING
                binding.addToCartBar.isVisible = state == ProductViewModel.State.SUCCESS
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productInfo.collect { product ->
                product?.let {
                    productName = it.name
                    binding.name.text = it.name
                    binding.price.text = formatToCurrency(it.price)
                    binding.description.text = it.description
                    binding.rating.text = it.rating.toString()
                    binding.reviews.text = getString(R.string.reviews_amount, it.numberOfReviews)
                    sliderAdapter.items = product.imageUrls.map { url -> ImageSlide(url) }
                    sliderAdapter.notifyDataSetChanged()
                    imageFullSizeAdapter.items = product.imageUrls.map { url -> ImageSlide(url) }
                    imageFullSizeAdapter.notifyDataSetChanged()
                    setColorsGroup(it.colors)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.amountWithTotalPrice.collect { amountWithTotalPrice ->
                binding.minusBtn.isClickable =
                    amountWithTotalPrice.amount != ProductViewModel.MIN_AMOUNT
                binding.totalAmount.text = amountWithTotalPrice.amount.toString()
                binding.totalPrice.text = formatToCurrency(amountWithTotalPrice.price)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.isInFavourite.collect { isFavourite ->
                if (isFavourite){
                    binding.favBtn.setColorFilter(Color.parseColor("#F93A3A"))
                    binding.favBtn.setOnClickListener { viewModel.removeFromFavourite() }
                } else {
                    binding.favBtn.setColorFilter(Color.parseColor("#545589"))
                    binding.favBtn.setOnClickListener { viewModel.addToFavourite() }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setPhotoCarousel(){
        binding.photosCarousel.adapter = sliderAdapter
        binding.photosCarousel.offscreenPageLimit = 3
        binding.photosCarousel.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        val pageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(4))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                /*page.scaleY = 0.85f + r * 0.15f
                page.scaleX = 0.85f + r * 0.15f*/
                if (r != 1f){
                    (page as MaterialCardView).cardElevation = 0f
                    page.strokeWidth = 1
                    page.scaleX = 0.8f
                    page.scaleY = 0.8f
                } else {
                    (page as MaterialCardView).cardElevation = 14f
                    page.strokeWidth = 0
                    page.scaleX = 1f
                    page.scaleY = 1f
                }
            }
        }
        binding.photosCarousel.setPageTransformer(pageTransformer)
    }

    private fun setColorsGroup(colors: List<String>){
        colors.forEachIndexed { index, color ->
            val chip = layoutInflater.inflate(
                R.layout.item_chip_color,
                binding.colorsGroup,
                false
            ) as Chip
            chip.tag = color
            chip.id = index
            chip.chipBackgroundColor = ColorStateList.valueOf(Color.parseColor(color))
            chip.setOnCheckedChangeListener { chip, isChecked ->
                if (isChecked)
                    (chip as Chip).setChipStrokeWidthResource(R.dimen.selected_chip_stroke_width)
                else (chip as Chip).setChipStrokeWidthResource(R.dimen.unselected_chip_stroke_width)
            }
            binding.colorsGroup.addView(chip)
        }
        binding.colorsGroup.check(0)
    }


}