import SwiftUI
import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync
import KMPNativeCoroutinesCombine
import KMPNativeCoroutinesRxSwift

struct ContentView: View {
    @State private var showAddItemModal = false
    @ObservedObject var viewModel = ShoppingListViewModel(repository: ShoppingListRepo(databaseDriverFactory: DatabaseDriverFactory()))

	var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.items) { item in
                    HStack{
                        Button(action: {
                            viewModel.updateItemState(item: item)
                        }) {
                            Image(systemName: item.isChecked ? "checkmark.circle" : "circle")
                        }
                        Text(item.name)
                    }
                }
                .onDelete { indexSet in
                    viewModel.removeItem(at: indexSet)
                }
            }
            .navigationBarTitle("kmmShopper")
            .navigationBarItems(trailing: Button( action: {
                self.showAddItemModal = true
            }){
                Image(systemName: "plus")
            })
            .sheet(isPresented: $showAddItemModal) {
                AddItemModalView { text in
                    viewModel.addNewItem(text: text)
                }
            }
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
    
    func addNewItem(text:String) {
        repository.insertShoppingListItemWithTextonly(text: text, id: NSUUID.init().uuidString)
    }
    
    func updateItemState(item: ShoppingListItem){
        repository.updateItemState(item: item)
    }
    
    func removeItem(at offsets: IndexSet) {
        let item = items[offsets[offsets.startIndex]]
        repository.deleteShoppingListItem(item: item)
    }
}

struct AddItemModalView: View {
    var onAddingItem: (String) -> Void
    
    @Environment(\.presentationMode) var presentationMode
    @State var newItemName = ""
    
    var body: some View {
        NavigationView {
            VStack {
                Form {
                    TextField("What do you want to get?", text: $newItemName)
                }
            }
            .navigationBarItems(trailing: Button("Add Item"){
                onAddingItem(newItemName)
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}
