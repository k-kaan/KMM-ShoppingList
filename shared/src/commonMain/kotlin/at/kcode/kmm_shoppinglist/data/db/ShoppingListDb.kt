package at.kcode.kmm_shoppinglist.data.db

import at.kcode.kmm_shoppinglist.AppDatabase
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

class ShoppingListDb(databaseDriverFactory: DatabaseDriverFactory) {
    private val db = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQueries = db.appDatabaseQueries

    fun getAllItems(): Flow<List<ShoppingListItem>> {
        return dbQueries
            .selectAllItems { id, name, timestamp, checked ->
                ShoppingListItem(id, name, LocalDateTime.parse(timestamp), checked ?: false)
            }
            .asFlow()
            .mapToList()
    }

    fun insertItems(shoppingListItem: ShoppingListItem) {
        dbQueries.insertItem(
            id = shoppingListItem.id,
            name = shoppingListItem.name,
            timestamp = shoppingListItem.timestamp.toString(),
            checked = shoppingListItem.isChecked
        )
    }

    fun clearDatabase() {
        dbQueries.transaction {
            dbQueries.removeAllItems()
        }
    }

    fun updateItemState(item: ShoppingListItem) {
        dbQueries.updateItemCheckedState(!item.isChecked, item.id)
    }

    fun deleteItem(item: ShoppingListItem) {
        dbQueries.transaction {
            dbQueries.deleteItem(item.id)
        }
    }
}