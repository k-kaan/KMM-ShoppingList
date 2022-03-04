package at.kcode.kmm_shoppinglist.data.model

import kotlinx.datetime.LocalDateTime

data class ShoppingListItem(
    val id: String,
    val name: String,
    val timestamp: LocalDateTime,
    val isChecked: Boolean
)