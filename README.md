# KMM-ShoppingList
This is an offline shopping list for iOS and Android made by **Kotlin Multiplatform** and the UI is built entirely with **SwiftUI/Compose** and the shopping list items are stored in a **SQLite** database.

You can create items, update the state of the items, swipe to delete them and all the changes on the database are shown through **Kotlin Flow** on our UI. Note that the project has several experimental features like [the new memory model of Kotlin](https://github.com/JetBrains/kotlin/blob/master/kotlin-native/NEW_MM.md) to avoid concurrency issues, or the library [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) for using Kotlin Flow in Swift. 

<p align="center">
<img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/ios/Simulator%20Screen%20Shot%20-%20iPhone%2013%20-%202022-03-24%20at%2014.51.49.png" width="250">  <img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/ios/Simulator%20Screen%20Shot%20-%20iPhone%2013%20-%202022-03-24%20at%2014.51.22.png" width="250"> <img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/ios/Simulator%20Screen%20Shot%20-%20iPhone%2013%20-%202022-03-24%20at%2014.51.35.png" width="250"> 
</p>
<p align="center">
<img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/android/Screenshot_1648130184.png" width="250"> <img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/android/Screenshot_1648130207.png" width="250"> <img src="https://github.com/k-kaan/KMM-ShoppingList/raw/main/screenshots/android/Screenshot_1648130219.png" width="250"> 
</p>

ToBeDone:
- add dependency injection
- improve design pattern
- improve UI (e.g. empty state) 

See also [my post on Medium](https://kkaan.medium.com/kotlin-multiplatform-a-shopping-list-for-android-ios-including-flow-a383ffa4abb7?source=friends_link&sk=49d55c9240e404443763d1f4ef9b2776) about this project ✍️
