package org.mifos.mobile.feature.client_charge.navigation

// Constants for Routes
const val CLIENT_CHARGE_NAVIGATION_ROUTE_BASE = "client_charge_base_route"
const val CLIENT_CHARGE_SCREEN_ROUTE = "client_charge_screen_route"

// Sealed class for Navigation Routes
sealed class ClientChargeNavigation(val route: String) {
    data object ClientChargeBase : ClientChargeNavigation(route = CLIENT_CHARGE_NAVIGATION_ROUTE_BASE)
    data object ClientChargeScreen : ClientChargeNavigation(route = CLIENT_CHARGE_SCREEN_ROUTE)
}