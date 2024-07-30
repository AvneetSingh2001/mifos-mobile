package org.mifos.mobile.feature.location.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.mifos.mobile.feature.location.LocationsScreen

fun NavController.navigateToLocationsScreen() {
    navigate(LocationsNavigation.LocationsBase.route)
}

fun NavGraphBuilder.locationsNavGraph() {
    navigation(
        startDestination = LocationsNavigation.LocationsBase.route,
        route = LocationsNavigation.LocationsScreen.route,
    ) {
        locationsScreenRoute()
    }
}

fun NavGraphBuilder.locationsScreenRoute() {
    composable(
        route = LocationsNavigation.LocationsScreen.route,
    ) {
        LocationsScreen()
    }
}