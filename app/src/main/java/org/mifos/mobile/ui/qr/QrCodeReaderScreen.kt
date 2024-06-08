package org.mifos.mobile.ui.qr

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.mifos.mobile.R
import org.mifos.mobile.core.ui.component.MFScaffold
import org.mifos.mobile.core.ui.theme.MifosMobileTheme

@Composable
fun QrCodeReaderScreen(
    qrScanned: (String) -> Unit,
    navigateBack: () -> Unit
) {
    MFScaffold(
        topBarTitleResId = R.string.add_beneficiary,
        navigateBack = navigateBack,
        scaffoldContent = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                QrCodeReaderContent(
                    qrScanned = qrScanned,
                    navigateBack = navigateBack
                )
            }
        }
    )
}

@Composable
fun QrCodeReaderContent(
    qrScanned: (String) -> Unit,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_AZTEC
            )
            .build()

        val scanner = GmsBarcodeScanning.getClient(context, options)

        scanner.startScan()
            .addOnSuccessListener { barcode ->
                barcode.rawValue?.let { qrScanned(it) }
            }
            .addOnCanceledListener {
                navigateBack()
            }
            .addOnFailureListener { e ->
                e.localizedMessage?.let { Log.d("SendMoney: Barcode scan failed", it) }
            }
    }
}


@Preview(showSystemUi = true)
@Composable
fun QrCodeReaderScreenPreview() {
    MifosMobileTheme {
        QrCodeReaderScreen(
            qrScanned = {},
            navigateBack = {}
        )
    }
}