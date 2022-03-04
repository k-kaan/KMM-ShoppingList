import Combine
import shared

// public struct ShoppingListItemsPublisher: Publisher {
//     public typealias Output = [ShoppingListItem]
//     public typealias Failure = Never
//
//     private let repository: ShoppingListRepo
//     public init(repository: ShoppingListRepo) {
//         self.repository = repository
//     }
//
//     public func receive<S: Subscriber>(subscriber: S) where S.Input == [ShoppingListItem], S.Failure == Failure {
// //         let subscription = IssPositionSubscription(repository: repository, subscriber: subscriber)
//         subscriber.receive(subscription: subscription)
//     }
//
//     final class ShoppingListItemsSubscription<S: Subscriber>: Subscription where S.Input == [ShoppingListItem], S.Failure == Failure {
//         private var subscriber: S?
//         private var job: Kotlinx_coroutines_coreJob? = nil
//
//         private let repository: ShoppingListRepo
//
//         init(repository: ShoppingListRepo, subscriber: S) {
//             self.repository = repository
//             self.subscriber = subscriber
//
//             job = repository.iosGetShoppingListItems().subscribe(
//                 scope: repository.iosScope,
//                 onEach: { items in
//                     subscriber.receive(items!)
//                 },
//                 onComplete: { subscriber.receive(completion: .finished) },
//                 onThrow: { error in debugPrint(error) }
//             )
//         }
//
//         func cancel() {
//             subscriber = nil
//             job?.cancel(cause: nil)
//         }
//
//         func request(_ demand: Subscribers.Demand) {}
//     }
// }