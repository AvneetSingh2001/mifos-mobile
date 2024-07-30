package org.mifos.mobile.feature.third.party.transfer.third_party_transfer.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.third.party.transfer.third_party_transfer.ThirdPartyTransferPayload
import org.mifos.mobile.feature.third.party.transfer.third_party_transfer.ThirdPartyTransferScreen

fun NavController.navigateToThirdPartyTransfer() {
    navigate(ThirdPartyTransferNavigation.ThirdPartyTransferBase.route)
}

fun NavGraphBuilder.thirdPartyTransferNavGraph(
    navigateBack: () -> Unit,
    addBeneficiary: () -> Unit,
    reviewTransfer: (ThirdPartyTransferPayload) -> Unit
) {
    navigation(
        startDestination = ThirdPartyTransferNavigation.ThirdPartyTransferBase.route,
        route = ThirdPartyTransferNavigation.ThirdPartyTransferScreen.route,
    ) {
        thirdPartyTransferRoute(
            navigateBack = navigateBack,
            addBeneficiary = addBeneficiary,
            reviewTransfer = reviewTransfer
        )
    }
}

fun NavGraphBuilder.thirdPartyTransferRoute(
    navigateBack: () -> Unit,
    addBeneficiary: () -> Unit,
    reviewTransfer: (ThirdPartyTransferPayload) -> Unit
) {
    composable(
        route = ThirdPartyTransferNavigation.ThirdPartyTransferScreen.route,
    ) {
        ThirdPartyTransferScreen(
            navigateBack = navigateBack,
            addBeneficiary = addBeneficiary ,
            reviewTransfer = reviewTransfer
        )
    }
}