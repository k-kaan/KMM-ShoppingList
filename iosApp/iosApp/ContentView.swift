import SwiftUI
import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesRxSwift

struct ContentView: View {
    @ObservedObject var viewModel = ShoppingListViewModel(repository: ShoppingListRepo(db: ShoppingListDb(databaseDriverFactory: DatabaseDriverFactory())))

	var body: some View {
        NavigationView {
            List(viewModel.items) { item in
                Text(item.name)
            }
            .navigationBarTitle("kmmShopper")
//             .navigationBa   rItems(trailing:
//                 Button("Reload") {
//                     self.viewModel.loadLaunches(forceReload: true)
//             })
        }
	}
}

extension ShoppingListItem: Identifiable { }

@MainActor
class ShoppingListViewModel: ObservableObject {

    @Published var items = [ShoppingListItem]()

    private var pollShoppingListItemsTask: Task<(), Never>? = nil

    private let repository: ShoppingListRepo
    init(repository: ShoppingListRepo) {
        self.repository = repository

        pollShoppingListItemsTask = Task {
            do {
                let stream = asyncStream(for: repository.getShoppingListItemsNative())

                 for try await data in stream {
                     self.items = data
                 }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
}
