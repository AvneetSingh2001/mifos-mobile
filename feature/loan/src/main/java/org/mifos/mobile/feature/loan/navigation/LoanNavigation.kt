package org.mifos.mobile.feature.loan.navigation

import org.mifos.mobile.core.common.Constants.LOAN_ID
import org.mifos.mobile.feature.loan.navigation.LoanRoute.LOAN_DETAIL_SCREEN_ROUTE
import org.mifos.mobile.feature.loan.navigation.LoanRoute.LOAN_NAVIGATION_ROUTE_BASE

sealed class LoanNavigation(val route: String) {
    data object LoanBase: LoanNavigation(
        route = "$LOAN_NAVIGATION_ROUTE_BASE?$LOAN_ID={$LOAN_ID}"
    ) {
        fun passArguments(loanId: Long) = "$LOAN_NAVIGATION_ROUTE_BASE?$LOAN_ID=$loanId"
    }

    data object LoanDetail: LoanNavigation(
        route = "$LOAN_DETAIL_SCREEN_ROUTE"
    ) {
        fun passArguments(loanId: Long) = "$LOAN_DETAIL_SCREEN_ROUTE?$LOAN_ID=$loanId"
    }

    data object LoanApplication: LoanNavigation(
        route = "${LoanRoute.LOAN_APPLICATION_SCREEN_ROUTE}"
    )

    data object LoanSummary: LoanNavigation(
        route = "${LoanRoute.LOAN_SUMMARY_SCREEN_ROUTE}"
    )

    data object LoanTransaction: LoanNavigation(
        route = "${LoanRoute.LOAN_TRANSACTION_SCREEN_ROUTE}"
    )

    data object LoanWithdraw: LoanNavigation(
        route = "${LoanRoute.LOAN_WITHDRAW_SCREEN_ROUTE}"
    )

    data object LoanSchedule: LoanNavigation(
        route = "${LoanRoute.LOAN_SCHEDULE_SCREEN_ROUTE}"
    )

    data object LoanReview: LoanNavigation(
        route = "${LoanRoute.LOAN_REVIEW_SCREEN_ROUTE}"
    )
}