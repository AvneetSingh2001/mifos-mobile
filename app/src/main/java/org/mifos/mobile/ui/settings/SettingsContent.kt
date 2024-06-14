package org.mifos.mobile.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.theme.MifosMobileTheme


@Composable
fun UpdateEndpointDialogScreen(
    initialBaseURL: String?,
    initialTenant: String?,
    updateInvokeEndpointValue: () -> Unit,
    handleEndpointUpdate: (baseURL: String, tenant: String) -> Unit
) {
    var baseURL by rememberSaveable {
        mutableStateOf(initialBaseURL)
    }
    var tenant by rememberSaveable {
        mutableStateOf(initialTenant)
    }

    AlertDialog(onDismissRequest = { updateInvokeEndpointValue.invoke() },
        confirmButton = {},
        modifier = Modifier.fillMaxWidth(),
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.pref_base_url_title))
                Spacer(modifier = Modifier.height(8.dp))

                baseURL?.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { baseURL = it },
                        label = { Text(text = stringResource(id = R.string.enter_base_url)) })
                }

                Spacer(modifier = Modifier.height(8.dp))

                tenant?.let {
                    OutlinedTextField(
                        value = it,
                        onValueChange = { tenant = it },
                        label = { Text(text = stringResource(id = R.string.enter_tenant)) })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { updateInvokeEndpointValue.invoke() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = {
                            baseURL?.let { baseURL ->
                                tenant?.let { tenant ->
                                    handleEndpointUpdate.invoke(
                                        baseURL, tenant
                                    )
                                }
                            }
                            updateInvokeEndpointValue.invoke()
                        })
                    {
                        Text(text = stringResource(id = R.string.dialog_action_ok))
                    }
                }
            }
        })
}



@Composable
@Preview(showSystemUi = true)
fun PreviewUpdateEndpointDialogScreen(modifier: Modifier = Modifier) {
    MifosMobileTheme {
        UpdateEndpointDialogScreen(
            initialBaseURL = "URL",
            initialTenant = "gsoc",
            updateInvokeEndpointValue = { /*TODO*/ },
            handleEndpointUpdate = { _, _ ->

            }
        )
    }
}



