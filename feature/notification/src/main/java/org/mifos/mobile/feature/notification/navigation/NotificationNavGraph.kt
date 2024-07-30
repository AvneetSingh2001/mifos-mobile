package org.mifos.mobile.feature.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.mifos.mobile.feature.notification.NotificationScreen

fun NavController.navigateToNotificationScreen() {
    navigate(NotificationNavigation.NotificationBase.route)
}

fun NavGraphBuilder.notificationNavGraph(
    navigateBack: () -> Unit,
) {
    navigation(
        startDestination = NotificationNavigation.NotificationBase.route,
        route = NotificationNavigation.NotificationScreen.route,
    ) {
        notificationScreenRoute(
            navigateBack = navigateBack,
        )
    }
}

fun NavGraphBuilder.notificationScreenRoute(
    navigateBack: () -> Unit,
) {
    composable(
        route = NotificationNavigation.NotificationScreen.route,
    ) {
        NotificationScreen(
            navigateBack = navigateBack,
        )
    }
}