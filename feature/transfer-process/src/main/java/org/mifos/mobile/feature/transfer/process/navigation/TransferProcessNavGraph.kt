package org.mifos.mobile.feature.transfer.process.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.transfer.process.TransferProcessScreen

// Navigation Setup
fun NavController.navigateToTransferProcessScreen() {
    navigate(TransferProcessNavigation.TransferProcessBase.route)
}

fun NavGraphBuilder.transferProcessNavGraph(
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = TransferProcessNavigation.TransferProcessBase.route,
        route = TransferProcessNavigation.TransferProcessScreen.route,
    ) {
        transferProcessScreenRoute(
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.transferProcessScreenRoute(
    navigateBack: () -> Unit,
) {
    composable(
        route = TransferProcessNavigation.TransferProcessScreen.route,
    ) {
        TransferProcessScreen(
            navigateBack = navigateBack,
        )
    }
}