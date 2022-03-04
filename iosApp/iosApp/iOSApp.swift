import SwiftUI
import shared

@main
struct iOSApp: App {
//     let repo = ShoppingListRepo(db: ShoppingListDb(databaseDriverFactory: DatabaseDriverFactory()))
	var body: some Scene {
		WindowGroup {
//             ContentView(viewModel: .init(repo: repo))
            ContentView()
		}
	}
}