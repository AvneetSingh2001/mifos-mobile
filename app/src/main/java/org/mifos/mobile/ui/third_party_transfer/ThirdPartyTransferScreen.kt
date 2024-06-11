package org.mifos.mobile.ui.third_party_transfer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MFScaffold
import org.mifos.mobile.core.ui.component.MifosErrorComponent
import org.mifos.mobile.core.ui.component.MifosProgressIndicatorOverlay
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.utils.Network

@Composable
fun ThirdPartyTransferScreen(
    viewModel: ThirdPartyTransferViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    addBeneficiary: () -> Unit,
    reviewTransfer: (ThirdPartyTransferPayload) -> Unit
) {
    val uiState = viewModel.thirdPartyTransferUiState.collectAsStateWithLifecycle()
    val uiData = viewModel.thirdPartyTransferUiData.collectAsStateWithLifecycle()

    ThirdPartyTransferScreen(
        uiState = uiState.value,
        uiData = uiData.value,
        navigateBack = navigateBack,
        addBeneficiary = addBeneficiary,
        reviewTransfer = reviewTransfer
    )
}

@Composable
fun ThirdPartyTransferScreen(
    uiState: ThirdPartyTransferUiState,
    uiData: ThirdPartyTransferUiData,
    navigateBack: () -> Unit,
    addBeneficiary: () -> Unit,
    reviewTransfer: (ThirdPartyTransferPayload) -> Unit
) {
    val context = LocalContext.current
    MFScaffold(
        topBarTitleResId = R.string.third_party_transfer,
        navigateBack = navigateBack,
        scaffoldContent = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
               when(uiState) {
                   is ThirdPartyTransferUiState.ShowUI -> {
                       ThirdPartyTransferContent(
                           accountOption = uiData.fromAccountDetail,
                           toAccountOption = uiData.toAccountOption,
                           beneficiaryList = uiData.beneficiaries,
                           navigateBack = navigateBack,
                           addBeneficiary = addBeneficiary,
                           reviewTransfer = reviewTransfer
                       )
                   }

                   is ThirdPartyTransferUiState.Loading -> {
                       MifosProgressIndicatorOverlay()
                   }

                   is ThirdPartyTransferUiState.Error -> {
                       MifosErrorComponent(
                           message = uiState.errorMessage ?: stringResource(id = R.string.error_fetching_third_party_transfer_template),
                           isNetworkConnected = Network.isConnected(context),
                       )
                   }
               }
            }
        }
    )
}

@Composable
@Preview
fun ThirdPartyTransferScreenPreview() {
    MifosMobileTheme {
        ThirdPartyTransferScreen(
            navigateBack = {},
            addBeneficiary = {},
            reviewTransfer = {}
        )
    }
}