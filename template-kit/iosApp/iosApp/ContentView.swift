import SwiftUI
import shared

@available(iOS 14.0, *)
struct ContentView: View {
    @StateObject private var viewModel = CoinsViewModel()

    var body: some View {
        List {
            ForEach(viewModel.coins, id: \.id) { coin in
                Text(coin.name)
            }
        }
        Button(action: { refresh() }) {
            Text("Get Coins")
        }
    }

    func refresh() {
        viewModel.getCoins()
    }
}

//struct ContentView_Previews: PreviewProvider {
//	static var previews: some View {
//        ContentView()
//	}
//}
