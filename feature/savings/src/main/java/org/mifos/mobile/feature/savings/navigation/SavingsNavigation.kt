package org.mifos.mobile.feature.savings.navigation

const val SAVINGS_NAVIGATION_ROUTE_BASE = "savings_route"
const val SAVINGS_DETAIL_SCREEN_ROUTE = "savings_detail_screen_route"
const val SAVINGS_APPLICATION_SCREEN_ROUTE = "savings_application_screen_route"
const val SAVINGS_TRANSACTION_SCREEN_ROUTE = "savings_transaction_screen_route"
const val SAVINGS_WITHDRAW_SCREEN_ROUTE = "savings_withdraw_screen_route"
const val SAVINGS_MAKE_TRANSFER_SCREEN_ROUTE = "savings_make_transfer_screen_route"

sealed class SavingsNavigation(val route: String) {
    data object SavingsBase : SavingsNavigation(
        route = "$SAVINGS_NAVIGATION_ROUTE_BASE"
    ) {
        fun passArguments(savingsId: Long) = "$SAVINGS_NAVIGATION_ROUTE_BASE"
    }

    data object SavingsDetail : SavingsNavigation(
        route = "$SAVINGS_DETAIL_SCREEN_ROUTE"
    )

    data object SavingsApplication : SavingsNavigation(
        route = "$SAVINGS_APPLICATION_SCREEN_ROUTE"
    )

    data object SavingsTransaction : SavingsNavigation(
        route = "$SAVINGS_TRANSACTION_SCREEN_ROUTE"
    )

    data object SavingsWithdraw : SavingsNavigation(
        route = "$SAVINGS_WITHDRAW_SCREEN_ROUTE"
    )

    data object SavingsMakeTransfer : SavingsNavigation(
        route = "$SAVINGS_MAKE_TRANSFER_SCREEN_ROUTE"
    )
}
