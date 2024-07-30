package org.mifos.mobile.feature.client_charge.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.core.model.entity.client.ClientType
import org.mifos.mobile.core.model.enums.ChargeType
import org.mifos.mobile.feature.client_charge.screens.ClientChargeScreen

fun NavController.navigateToClientChargeScreen(
    chargeType: ChargeType
) {
    navigate(ClientChargeNavigation.ClientChargeBase.route)
}

fun NavGraphBuilder.clientChargeNavGraph(
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = ClientChargeNavigation.ClientChargeBase.route,
        route = ClientChargeNavigation.ClientChargeScreen.route,
    ) {
        clientChargeScreenRoute(
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.clientChargeScreenRoute(
    navigateBack: () -> Unit,
) {
    composable(
        route = ClientChargeNavigation.ClientChargeScreen.route,
    ) {
        ClientChargeScreen(
            navigateBack = navigateBack,
        )
    }
}