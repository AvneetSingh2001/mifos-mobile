package org.mifos.mobile.ui.loan_account_application

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MifosProgressIndicator
import org.mifos.mobile.core.ui.component.MifosTopBar
import org.mifos.mobile.core.ui.component.NoInternet
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.ui.enums.LoanState
import org.mifos.mobile.utils.Network


@Composable
fun LoanApplicationScreen(
    viewModel: LoanApplicationViewModel = hiltViewModel(),
    navigateBack: (isSuccess: Boolean) -> Unit,
    review: () -> Unit,
) {
    val uiState by viewModel.loanUiState.collectAsStateWithLifecycle()
    val uiData by viewModel.loanApplicationScreenData.collectAsStateWithLifecycle()

    LoanApplicationScreen(
        uiState = uiState,
        uiData = uiData,
        loanState = viewModel.loanState,
        onRetry = { viewModel.loadLoanTemplate() },
        navigateBack = navigateBack,
        selectProduct = { viewModel.productSelected(it) },
        selectPurpose = { viewModel.purposeSelected(it) },
        setDisbursementDate = { viewModel.setDisburseDate(it) },
        setPrincipalAmount = { viewModel.setPrincipalAmount(it) },
        reviewClicked = review
    )
}

@Composable
fun LoanApplicationScreen(
    uiState: LoanApplicationUiState,
    loanState: LoanState,
    uiData: LoanApplicationScreenData,
    navigateBack: (isSuccess: Boolean) -> Unit,
    selectProduct: (Int) -> Unit,
    selectPurpose: (Int) -> Unit,
    setDisbursementDate: (String) -> Unit,
    setPrincipalAmount: (String) -> Unit,
    reviewClicked: () -> Unit,
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MifosTopBar(
            modifier = Modifier.fillMaxWidth(),
            navigateBack = { navigateBack(false) },
            title = {
                Text(text = stringResource(
                    id = if (loanState == LoanState.CREATE) R.string.apply_for_loan
                    else R.string.update_loan
                ))
            }
        )

        Box(modifier = Modifier.weight(1f)) {
            LoanApplicationContent(
                uiData = uiData,
                selectProduct = selectProduct,
                selectPurpose = selectPurpose,
                reviewClicked = reviewClicked,
                setPrincipalAmount = setPrincipalAmount,
                setDisbursementDate = setDisbursementDate
            )
            when (uiState) {
                is LoanApplicationUiState.Loading -> {
                    MifosProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background.copy(0.8f))
                    )
                }

                is LoanApplicationUiState.Error -> {
                    ErrorComponent(onRetry = onRetry)
                }

                is LoanApplicationUiState.Success -> Unit
            }
        }
    }
}


@Composable
fun ErrorComponent(onRetry: () -> Unit) {
    val context = LocalContext.current
    if (!Network.isConnected(context)) {
        NoInternet(
            icon = R.drawable.ic_portable_wifi_off_black_24dp,
            error = R.string.no_internet_connection,
            isRetryEnabled = true,
            retry = onRetry
        )
    } else {
        Toast.makeText(context, stringResource(id = R.string.error_fetching_template), Toast.LENGTH_SHORT).show()
    }
}


class UiStatesParameterProvider : PreviewParameterProvider<LoanApplicationUiState> {
    override val values: Sequence<LoanApplicationUiState>
        get() = sequenceOf(
            LoanApplicationUiState.Error(R.string.something_went_wrong),
            LoanApplicationUiState.Loading,
            LoanApplicationUiState.Success
        )
}


@Preview(showSystemUi = true)
@Composable
fun ReviewLoanApplicationScreenPreview(
    @PreviewParameter(UiStatesParameterProvider::class) loanApplicationUiState: LoanApplicationUiState
) {
    MifosMobileTheme {
        LoanApplicationScreen(
            uiState = loanApplicationUiState,
            uiData = LoanApplicationScreenData(),
            loanState = LoanState.CREATE,
            navigateBack = {},
            selectPurpose = {},
            selectProduct = {},
            reviewClicked = {},
            setPrincipalAmount = {},
            setDisbursementDate = {},
            onRetry = {},
        )
    }
}

