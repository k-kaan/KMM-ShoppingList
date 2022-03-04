package at.kcode.kmm_shoppinglist.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.kcode.kmm_shoppinglist.data.ShoppingListRepo
import at.kcode.kmm_shoppinglist.data.db.DatabaseDriverFactory
import at.kcode.kmm_shoppinglist.data.db.ShoppingListDb
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem

class MainActivity : AppCompatActivity() {

    private val repo = ShoppingListRepo(ShoppingListDb(DatabaseDriverFactory(this)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo light/dark theme, background
        setContent {
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
        LazyColumn {
            // todo: sticky headers? https://developer.android.com/jetpack/compose/lists#sticky-headers
            items(shoppingListItems) { shoppingListItem ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val checkedState = remember { mutableStateOf(shoppingListItem.isChecked) }
                    Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it }) // todo change in db

                    Text(shoppingListItem.name, textAlign = TextAlign.Center, fontSize = 16.sp)
                }
            }
        }
    }
}
