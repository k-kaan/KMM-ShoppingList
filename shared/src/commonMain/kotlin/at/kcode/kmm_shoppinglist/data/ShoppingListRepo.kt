package at.kcode.kmm_shoppinglist.data

import at.kcode.kmm_shoppinglist.data.db.DatabaseDriverFactory
import at.kcode.kmm_shoppinglist.data.db.ShoppingListDb
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem
import kotlinx.datetime.*

class ShoppingListRepo(databaseDriverFactory: DatabaseDriverFactory) {
    private val db = ShoppingListDb(databaseDriverFactory)

    fun insertShoppingListItem(item: ShoppingListItem) = db.insertItems(item)

    fun insertShoppingListItemWithTextonly(text: String, id: String) =
        db.insertItems(
            ShoppingListItem(
                id = id,
                name = text,
                timestamp = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                isChecked = false
            )
        )

    fun getShoppingListItems() = db.getAllItems()

    fun updateItemState(item: ShoppingListItem) = db.updateItemState(item)

    fun deleteShoppingListItem(item: ShoppingListItem) = db.deleteItem(item)
}

