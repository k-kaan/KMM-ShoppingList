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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.kcode.kmm_shoppinglist.data.ShoppingListRepo
import at.kcode.kmm_shoppinglist.data.db.DatabaseDriverFactory
import at.kcode.kmm_shoppinglist.data.model.ShoppingListItem
import java.util.*

class MainActivity : AppCompatActivity() {

    private val repo = ShoppingListRepo(DatabaseDriverFactory(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo light/dark theme, background
        setContent {
            MaterialTheme {
                val showAddItemDialog = remember { mutableStateOf(false) }

                Scaffold(
                    topBar = { CenteredTopBar() },
                    content = {
                        AddItemDialog(show = showAddItemDialog) { textValue -> addNewItem(textValue) }

                        // receive from flow in runtime
                        val shoppingListItems: List<ShoppingListItem> by repo.getShoppingListItems().collectAsState(initial = emptyList())
                        ShoppingList(shoppingListItems = shoppingListItems)
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            showAddItemDialog.value = !showAddItemDialog.value
                        }) {
                            Icon(Icons.Filled.Add, "")
                        }
                    })
            }
        }
    }

    //region ui
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
    fun AddItemDialog(show: MutableState<Boolean>, onConfirm: (String) -> Unit) {
        if (show.value) {
            var textValue by remember { mutableStateOf(TextFieldValue("")) }

            AlertDialog(
                text = {
                    TextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        label = { Text("What do you want to get?") }
                    )
                },
                onDismissRequest = { show.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        onConfirm.invoke(textValue.text)
                        show.value = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { show.value = false }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }

    // SwipeToDismiss is buggy (when dismissing, other elements disappear todo!)
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ShoppingList(shoppingListItems: List<ShoppingListItem>) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            // todo: sticky headers? https://developer.android.com/jetpack/compose/lists#sticky-headers
            items(shoppingListItems) { shoppingListItem ->

                val rememberDismiss = rememberDismissState()

                if (rememberDismiss.isDismissed(DismissDirection.EndToStart)) {
                    repo.deleteShoppingListItem(shoppingListItem) // todo buggy
                }

                SwipeToDismiss(
                    state = rememberDismiss,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {},
                    dismissThresholds = { FractionalThreshold(0.05f) },
                    dismissContent = {
                        ShoppingListItemRow(item = shoppingListItem)
                        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f), startIndent = 16.dp, thickness = Dp.Hairline)
                    }
                )
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
    //endregion

    //region io
    private fun addNewItem(textValue: String) {
        repo.insertShoppingListItemWithTextonly(
            id = UUID.randomUUID().toString(),
            text = textValue
        )
    }
    //endregion
}
