package com.example.shoppingapp.presentation.adapter_delegates

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingapp.databinding.ItemBrandBinding
import com.example.shoppingapp.databinding.ItemCategoryBinding
import com.example.shoppingapp.databinding.ItemCollectionBinding
import com.example.shoppingapp.databinding.ItemPhotoBinding
import com.example.shoppingapp.databinding.ItemPhotoFullsizeBinding
import com.example.shoppingapp.databinding.ItemProductBinding
import com.example.shoppingapp.databinding.ItemProductSaleBinding
import com.example.shoppingapp.databinding.ItemSearchHintBinding
import com.example.shoppingapp.presentation.model.*
import com.example.shoppingapp.presentation.utils.Utils.dpToPixel
import com.example.shoppingapp.presentation.utils.Utils.formatToCurrency
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding


object AdapterDelegates {


    fun productDelegate(onProductClick: (ProductUi) -> Unit) =
        adapterDelegateViewBinding<ProductUi, ListItem, ItemProductBinding>(
            { inflater, container -> ItemProductBinding.inflate(inflater, container, false) },
            on = { item, _, _ ->
                item is ProductUi && item.discount == null
            }
        ) {
            binding.root.setOnClickListener {
                onProductClick.invoke(item)
            }
            bind {
                binding.name.text = item.name
                binding.category.text = item.category
                binding.price.text = formatToCurrency(item.price)
                Glide.with(binding.photo)
                    .load(item.imageUrl)
                    .into(binding.photo)
            }
        }

    fun saleProductDelegate(onProductClick: (ProductUi) -> Unit) =
        adapterDelegateViewBinding<ProductUi, ListItem, ItemProductSaleBinding>(
            { inflater, container -> ItemProductSaleBinding.inflate(inflater, container, false) },

        ) {
            binding.root.setOnClickListener {
                onProductClick.invoke(item)
            }
            bind {
                binding.name.text = item.name
                binding.category.text = item.category
                binding.price.text = formatToCurrency(item.price)
                binding.discount.text = item.discount?.let { "${item.discount}% off" } ?: ""
                Glide.with(binding.photo)
                    .load(item.imageUrl)
                    .into(binding.photo)
            }
        }

    fun productsCategoryDelegate() =
        adapterDelegateViewBinding<ProductsCategory, ListItem, ItemCategoryBinding>(
            { inflater, container -> ItemCategoryBinding.inflate(inflater, container, false) },

        ) {
            bind {
                binding.name.text = item.name
                Glide.with(binding.icon)
                    .load(item.iconId)
                    .into(binding.icon)
            }

        }

    fun brandDelegate() =
        adapterDelegateViewBinding<Brand, ListItem, ItemBrandBinding>(
            { inflater, container -> ItemBrandBinding.inflate(inflater, container, false) },

        ) {
            bind {
                binding.name.text = item.name
            }

        }

    fun customListDelegate(context: Context, onProductClick: (ProductUi) -> Unit) =
        adapterDelegateViewBinding<CustomItemsList, ListItem, ItemCollectionBinding>(
            { inflater, container ->
                ItemCollectionBinding.inflate(inflater, container, false).apply {
                    recyclerView.adapter = ListDelegationAdapter(
                        productDelegate { onProductClick(it) },
                        saleProductDelegate { onProductClick(it) },
                        brandDelegate()
                    )

                }
            },
            on = { item, _, _ -> item is CustomItemsList }
        ) {
            bind {
                binding.name.text = item.title
                (binding.recyclerView.adapter as ListDelegationAdapter<List<ListItem>>).items =
                    item.list
                when (item.type) {
                    CustomItemsList.Type.SMALL_ICON -> binding.recyclerView.addItemDecoration(
                        productsDivider(context, item.list.size, 12)
                    )
                    CustomItemsList.Type.BIG_ICON -> binding.recyclerView.addItemDecoration(
                        productsDivider(context, item.list.size, 9)
                    )
                }
            }
        }

    fun imageSlidesDelegate() = adapterDelegateViewBinding<ImageSlide, ListItem, ItemPhotoBinding>(
        {inflater, container -> ItemPhotoBinding.inflate(inflater, container, false)}
    ){
        bind {
            Glide.with(binding.photo)
                .load(item.url)
                .into(binding.photo)
        }
    }
    fun imageFullSizeSlidesDelegate() = adapterDelegateViewBinding<ImageSlide, ListItem, ItemPhotoFullsizeBinding>(
        {inflater, container -> ItemPhotoFullsizeBinding.inflate(inflater, container, false)}
    ){
        bind {
            Glide.with(binding.photo)
                .load(item.url)
                .into(binding.photo)
        }
    }


    private fun productsDivider(
        context: Context,
        listSize: Int,
        innerSpaceDp: Int
    ) =
        object : RecyclerView.ItemDecoration() {
            private val outerSpace = context.dpToPixel(11)
            private val innerSpace = context.dpToPixel(innerSpaceDp)
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



}

