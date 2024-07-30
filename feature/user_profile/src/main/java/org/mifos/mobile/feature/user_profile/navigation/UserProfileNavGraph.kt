package org.mifos.mobile.feature.user_profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.user_profile.screens.UserProfileScreen

fun NavController.navigateToUserProfile() {
    navigate(UserProfileNavigation.UserProfileBase.route)
}

fun NavGraphBuilder.userProfileNavGraph(
    navigateBack: () -> Unit,
    navigateToChangePassword: () -> Unit
) {
    navigation(
        startDestination = UserProfileNavigation.UserProfileBase.route,
        route = UserProfileNavigation.UserProfileScreen.route,
    ) {
        userProfileRoute(
            navigateBack = navigateBack,
            navigateToChangePassword = navigateToChangePassword
        )
    }
}

fun NavGraphBuilder.userProfileRoute(
    navigateBack: () -> Unit,
    navigateToChangePassword: () -> Unit
) {
    composable(
        route = UserProfileNavigation.UserProfileScreen.route,
    ) {
        UserProfileScreen(
            navigateBack = navigateBack,
            changePassword = navigateToChangePassword
        )
    }
}