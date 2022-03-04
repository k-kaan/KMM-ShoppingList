import SwiftUI
import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesRxSwift

struct ContentView: View {
    @ObservedObject var viewModel = ShoppingListViewModel(repository: ShoppingListRepo(databaseDriverFactory: DatabaseDriverFactory()))

	var body: some View {
        NavigationView {
            List(viewModel.items) { item in
                HStack{
                    Button(action: {
                        viewModel.updateItemState(item: item)
                    }) {
                        Image(systemName: item.isChecked ? "checkmark.circle" : "circle")
                    }
                    Text(item.name)
                }
            }
            .navigationBarTitle("kmmShopper")
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
    
    func updateItemState(item: ShoppingListItem){
        repository.updateItemState(item: item)
    }
}
