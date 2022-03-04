package at.kcode.kmm_shoppinglist.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.kcode.kmm_shoppinglist.data.ShoppingListRepo
import at.kcode.kmm_shoppinglist.data.db.DatabaseDriverFactory
import at.kcode.kmm_shoppinglist.data.db.ShoppingListDb
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem

class MainActivity : AppCompatActivity() {

    private val repo = ShoppingListRepo(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo light/dark theme, background
        setContent {
            MaterialTheme {
                Scaffold(
                    topBar = { CenteredTopBar() },
                    content = {
                        // receive from flow in runtime
                        val shoppingListItems: List<ShoppingListItem> by repo.getShoppingListItems().collectAsState(initial = emptyList())
                        ShoppingList(shoppingListItems = shoppingListItems)
                    },
                    floatingActionButton = {
                        // todo centered floating button
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Filled.Add, "")
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun CenteredTopBar() {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "kmmShopper", //todo string
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Black
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp
        )
    }


    @Composable
    fun ShoppingList(shoppingListItems: List<ShoppingListItem>) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            // todo: sticky headers? https://developer.android.com/jetpack/compose/lists#sticky-headers
            items(shoppingListItems) { shoppingListItem ->
                ShoppingListItemRow(item = shoppingListItem)
                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f), startIndent = 16.dp, thickness = Dp.Hairline)
            }
        }
    }

    @Composable
    fun ShoppingListItemRow(item: ShoppingListItem) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { repo.updateItemState(item) }
        ) {
            Checkbox(checked = item.isChecked, onCheckedChange = { repo.updateItemState(item) })

            Text(item.name, fontSize = 16.sp)
        }
    }
}
