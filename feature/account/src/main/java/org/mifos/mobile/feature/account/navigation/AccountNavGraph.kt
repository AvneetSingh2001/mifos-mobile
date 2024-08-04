package org.mifos.mobile.feature.account.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.account.client_account.screens.ClientAccountsScreen


fun NavController.navigateToClientAccountsScreen() {
    navigate(ClientAccountsNavigation.ClientAccountsBase.route)
}

fun NavGraphBuilder.clientAccountsNavGraph(
    navigateToAccountDetail: (String, Long) -> Unit,
    navigateToNextActivity: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    navigation(
        startDestination = ClientAccountsNavigation.ClientAccountsBase.route,
        route = ClientAccountsNavigation.ClientAccountsScreen.route,
    ) {
        clientAccountsScreenRoute(
            navigateToAccountDetail = navigateToAccountDetail,
            navigateToNextActivity = navigateToNextActivity,
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.clientAccountsScreenRoute(
    navigateToAccountDetail: (String, Long) -> Unit,
    navigateToNextActivity: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    composable(
        route = ClientAccountsNavigation.ClientAccountsScreen.route,
    ) {
        val context = LocalContext.current

        ClientAccountsScreen(
            navigateBack = navigateBack,
            openNextActivity = { navigateToNextActivity(it) },
            onItemClick = { accountType, accountId -> navigateToAccountDetail(accountType, accountId) }
        )
    }
}