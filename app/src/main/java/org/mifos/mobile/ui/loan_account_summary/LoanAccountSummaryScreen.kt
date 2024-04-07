package org.mifos.mobile.ui.loan_account_summary

import androidx.appcompat.app.AppCompatDelegate.NightMode
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MifosTopBar
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.models.accounts.loan.LoanWithAssociations
import org.mifos.mobile.utils.CurrencyUtil


@Composable
fun LoanAccountSummaryScreen(
    navigateBack: () -> Unit,
    loanWithAssociations: LoanWithAssociations?
) {

    var currencySymbol = loanWithAssociations?.currency?.displaySymbol
    if (currencySymbol == null) {
        currencySymbol = loanWithAssociations?.currency?.code ?: ""
    }

    Column {
        MifosTopBar(
            navigateBack = navigateBack,
            title = { Text(text = stringResource(id = R.string.loan_summary)) }
        )
        Spacer(modifier = Modifier.height(14.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            MifosTitleDescSingleLine(
                modifier = Modifier.padding(horizontal = 14.dp),
                title = stringResource(id = R.string.account_short),
                description = loanWithAssociations?.accountNo ?: ""
            )

            MifosTitleDescSingleLine(
                modifier = Modifier.padding(horizontal = 14.dp),
                title = stringResource(id = R.string.loan_product),
                description = loanWithAssociations?.loanProductName ?: ""
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.principal),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.principal ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.interest),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.interestCharged ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.fees),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.feeChargesCharged ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.penalties),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.penaltyChargesCharged ?: 0.0
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.total_repayment),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.totalExpectedRepayment ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.total_paid),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.totalRepayment ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.interest_waived),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.interestWaived ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.penalties_waived),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.penaltyChargesWaived ?: 0.0
                        )
                    )

                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.fees_waived),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.feeChargesWaived ?: 0.0
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    MifosTitleDescSingleLine(
                        title = stringResource(id = R.string.outstanding_balance),
                        description = stringResource(
                            id = R.string.string_and_double,
                            currencySymbol,
                            loanWithAssociations?.summary?.totalOutstanding ?: 0.0
                        )
                    )

                    MifosTitleStatusSingleLine(
                        title = stringResource(id = R.string.account_status),
                        isActive = loanWithAssociations?.status?.active == true
                    )
                }
            }
        }
    }
}

@Composable
fun MifosTitleDescSingleLine(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            text = title,
            modifier = Modifier
                .alpha(0.7f)
        )

        Text(
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            text = description
        )
    }
}

@Composable
fun MifosTitleStatusSingleLine(
    modifier: Modifier = Modifier,
    title: String,
    isActive: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
            text = title,
            modifier = Modifier
                .weight(1f)
                .alpha(0.7f)
        )

        Text(
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            text = if (isActive) stringResource(id = R.string.active_uc)
            else stringResource(id = R.string.inactive_uc)
        )
        Spacer(modifier = modifier.width(5.dp))
        Image(
            painter = painterResource(
                id = if (isActive) R.drawable.ic_check_circle_green_24px
                else R.drawable.ic_report_problem_red_24px
            ),
            contentDescription = null,
            modifier = Modifier.size(14.dp)
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun LoanAccountSummaryPreview() {
    MifosMobileTheme {
        LoanAccountSummaryScreen(
            navigateBack = {},
            loanWithAssociations = LoanWithAssociations()
        )
    }
}