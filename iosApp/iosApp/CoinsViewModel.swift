//
//  CoinsViewModel.swift
//  iosApp
//
//  Created by RD on 28.07.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

@available(iOS 14.0, *)
class CoinsViewModel : ObservableObject {
    @Published var coins = [CoinResponse]()

    private let getCoinsUseCase = GetCoinsListUseCase()

    init() {
        self.getCoins()
    }

    func getCoins() {
        getCoinsUseCase.getCoinsList { response, err in
            guard let coinsList = response?.coins else { return }
            self.coins = coinsList
        }
    }
}
