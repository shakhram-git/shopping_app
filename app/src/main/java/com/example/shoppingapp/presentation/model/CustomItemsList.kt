package com.example.shoppingapp.presentation.model

data class CustomItemsList(
    val title: String,
    val list: List<ListItem>,
    val type: Type
) : ListItem {
    enum class Type{
        SMALL_ICON,
        BIG_ICON
    }
}