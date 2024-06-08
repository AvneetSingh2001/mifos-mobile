package org.mifos.mobile.ui.recent_transactions

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.mifos.mobile.MifosSelfServiceApp
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MFScaffold
import org.mifos.mobile.core.ui.component.MifosErrorComponent
import org.mifos.mobile.core.ui.component.MifosProgressIndicator
import org.mifos.mobile.core.ui.theme.MifosMobileTheme
import org.mifos.mobile.models.Transaction
import org.mifos.mobile.utils.CurrencyUtil
import org.mifos.mobile.utils.DateHelper
import org.mifos.mobile.utils.Network
import org.mifos.mobile.utils.RecentTransactionUiState
import org.mifos.mobile.utils.Utils

@Composable
fun RecentTransactionScreen(
    viewModel: RecentTransactionViewModel = hiltViewModel(),
    navigateBack: () -> Unit )
{
    val uiState by viewModel.recentTransactionUiState.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.loadRecentTransactions(false, 0)
    }

    RecentTransactionScreenContent(
        uiState = uiState,
        navigateBack = navigateBack,
        retryConnection = { viewModel.loadRecentTransactions(false, 0) },
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() }
    )
}

@OptIn( ExperimentalMaterial3Api::class)
@Composable
fun RecentTransactionScreenContent(
    uiState: RecentTransactionUiState,
    navigateBack: () -> Unit,
    retryConnection: () -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit) {

    val context = LocalContext.current
    val pullRefreshState = rememberPullToRefreshState()

        Box(Modifier.nestedScroll(pullRefreshState.nestedScrollConnection))
        {
            Column {

                when (uiState) {

                    RecentTransactionUiState.EmptyTransaction -> {
                        MifosErrorComponent(isEmptyData = true)
                    }

                    is RecentTransactionUiState.Error -> {
                        MifosErrorComponent(
                            isNetworkConnected = Network.isConnected(context),
                            isEmptyData = false,
                            isRetryEnabled = true,
                            onRetry = retryConnection
                        )
                    }

                    RecentTransactionUiState.Initial -> {
                    }

                    RecentTransactionUiState.Loading -> {

                        MifosProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
                        )
                    }

                    is RecentTransactionUiState.LoadMoreRecentTransactions -> {

                    }

                    is RecentTransactionUiState.RecentTransactions -> {

                        if (uiState.transactions?.isNotEmpty() == true) {
                            LoadRecentTransactions(transactionList = uiState.transactions as ArrayList<Transaction>)
                        } else {
                            MifosErrorComponent(isEmptyData = true)
                        }
                    }
                }
            }

            if (pullRefreshState.isRefreshing) {
                LaunchedEffect(key1 = true) {
                    onRefresh()
                }
            }
            LaunchedEffect(key1 = isRefreshing) {
                if (isRefreshing)
                    pullRefreshState.startRefresh()
                else
                    pullRefreshState.endRefresh()
            }

            PullToRefreshContainer(
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),

                )
        }
}


@Composable
fun LoadRecentTransactions( transactionList : ArrayList<Transaction> ){

    LazyColumn {
        items(items = transactionList?.toList().orEmpty()) {
            RecentTransactionListItem(it)
        }
    }
}


@Composable
fun RecentTransactionListItem(transaction: Transaction?) {
    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_local_atm_black_24dp),
            contentDescription = stringResource(id = R.string.atm_icon),
            modifier = Modifier.size(39.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = Utils.formatTransactionType(transaction?.type?.value),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row {
                Text(
                    text = stringResource(
                        id = R.string.string_and_string,
                        transaction?.currency?.displaySymbol ?: transaction?.currency?.code ?: "",
                        CurrencyUtil.formatCurrency(
                            MifosSelfServiceApp.context,
                            transaction?.amount ?: 0.0,
                        )
                    ),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.7f),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = DateHelper.getDateAsString(transaction?.submittedOnDate),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.alpha(0.7f),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

        }
    }
}


class RecentTransactionScreenPreviewProvider : PreviewParameterProvider<RecentTransactionUiState> {

    var tList : ArrayList<Transaction> = ArrayList<Transaction>()
    override val values: Sequence<RecentTransactionUiState>
        get() = sequenceOf(
            RecentTransactionUiState.RecentTransactions(tList),
            RecentTransactionUiState.Loading,
            RecentTransactionUiState.LoadMoreRecentTransactions(tList),
            RecentTransactionUiState.Error(R.string.recent_transactions),
            RecentTransactionUiState.EmptyTransaction,
            RecentTransactionUiState.Initial
        )
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun RecentTransactionScreenPreview(
    @PreviewParameter(RecentTransactionScreenPreviewProvider::class) recentTransactionUiState: RecentTransactionUiState
) {
    MifosMobileTheme {
        RecentTransactionScreenContent(
            uiState = recentTransactionUiState,
            navigateBack = {}, {},false , {})
    }
}

