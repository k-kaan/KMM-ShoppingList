import SwiftUI
import shared

struct ShoppingListItemRow: View {
    var item: ShoppingListItem

    var body: some View {
        HStack(
            alignment: .top,
            spacing: 10
        ) {
            Text(item.name)
//                 Text("Launch name: \(rocketLaunch.missionName)")
//                 Text(launchText).foregroundColor(launchColor)
//                 Text("Launch year: \(String(rocketLaunch.launchYear))")
//                 Text("Launch details: \(rocketLaunch.details ?? "")")
        }
    }
}
