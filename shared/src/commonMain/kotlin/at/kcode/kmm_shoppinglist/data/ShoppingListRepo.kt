package at.kcode.kmm_shoppinglist.data

import at.kcode.kmm_shoppinglist.data.db.ShoppingListDb
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem
import kotlinx.datetime.*

class ShoppingListRepo(
    private val db: ShoppingListDb
) {

    init {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        insertShoppingListItem(ShoppingListItem("ojandkasd", "sucuk", now, isChecked = true))
        insertShoppingListItem(ShoppingListItem("klnasd", "salam", now, isChecked = false))
        insertShoppingListItem(ShoppingListItem("knasd", "sosis", now, isChecked = false))
        LocalDateTime
    }

    fun insertShoppingListItem(item: ShoppingListItem) = db.insertItems(item)

    fun getShoppingListItems() = db.getAllItems()
}

