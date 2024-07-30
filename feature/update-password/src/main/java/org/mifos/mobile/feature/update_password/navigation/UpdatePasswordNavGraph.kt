package org.mifos.mobile.feature.update_password.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.update_password.UpdatePasswordScreen

fun NavController.navigateToUpdatePassword() {
    navigate(UpdatePasswordNavigation.UpdatePasswordBase.route)
}

fun NavGraphBuilder.updatePasswordNavGraph(
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = UpdatePasswordNavigation.UpdatePasswordBase.route,
        route = UpdatePasswordNavigation.UpdatePasswordScreen.route,
    ) {
        updatePasswordRoute(
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.updatePasswordRoute(
    navigateBack: () -> Unit,
) {
    composable(
        route = UpdatePasswordNavigation.UpdatePasswordScreen.route,
    ) {
        UpdatePasswordScreen(
            navigateBack = navigateBack,
        )
    }
}