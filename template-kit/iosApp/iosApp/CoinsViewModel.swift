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
class CoinsViewModel : BaseViewModel, ObservableObject {
    @Published var coins = [Coin]()

//    private let getCoinsUseCase = GetCoinsListUseCase()
    private let getCoinsUseCase = ObserveCoinsListUseCase()

    override init() {
        super.init()
        self.getCoins()
    }

    func getCoins() {
        getCoinsUseCase.execute(self, args: KotlinUnit()) {
            $0.onNext { list in
                guard let coinsList = list?.list else { return }
                self.coins = coinsList as [Coin]
            }
            $0.onError { error in
                print(error)
            }
        }
    }
}
