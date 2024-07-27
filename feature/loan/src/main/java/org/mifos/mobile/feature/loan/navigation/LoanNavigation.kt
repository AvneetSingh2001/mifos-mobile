package org.mifos.mobile.feature.loan.navigation

import org.mifos.mobile.core.common.Constants.LOAN_ACCOUNT
import org.mifos.mobile.core.common.Constants.LOAN_ID
import org.mifos.mobile.core.common.Constants.LOAN_STATE

sealed class LoanNavigation(val route: String) {
    data object LoanDetail: LoanNavigation(
        route = "${LoanRoute.LOAN_DETAIL_SCREEN_ROUTE_BASE}"
    )

    data object LoanApplication: LoanNavigation(
        route = "${LoanRoute.LOAN_APPLICATION_SCREEN_ROUTE_BASE}"
    )

    data object LoanSummary: LoanNavigation(
        route = "${LoanRoute.LOAN_SUMMARY_SCREEN_ROUTE_BASE}"
    )

    data object LoanTransaction: LoanNavigation(
        route = "${LoanRoute.LOAN_TRANSACTION_SCREEN_ROUTE_BASE}"
    )

    data object LoanWithdraw: LoanNavigation(
        route = "${LoanRoute.LOAN_WITHDRAW_SCREEN_ROUTE_BASE}"
    )

    data object LoanSchedule: LoanNavigation(
        route = "${LoanRoute.LOAN_SCHEDULE_SCREEN_ROUTE_BASE}"
    )

    data object LoanReview: LoanNavigation(
        route = "${LoanRoute.LOAN_REVIEW_SCREEN_ROUTE_BASE}"
    )
}