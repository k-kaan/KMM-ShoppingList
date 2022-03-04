import Foundation
import Combine
import shared
import KMPNativeCoroutinesAsync

// @MainActor
// class ShoppingListViewModel: ObservableObject {
//
//     @Published var items = [ShoppingListItem]
//
//     private var pollShoppingListItemsTask: Task<(), Never? = nil
//
//     private let repository: ShoppingListRepository
//     init(repository: ShoppingListRepository) {
//         self.repository = repository
//
//         pollShoppingListItemsTask = Task {
//             do {
//                 let stream = asyncStream(for: repository.getShoppingListItemsNative())
//                 for try await data in stream {
//                     self.items = data
//                 }
//             } catch {
//                 print("Failed with error: \(error)")
//             }
//         }
//     }
// }