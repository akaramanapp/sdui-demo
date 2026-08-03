package com.demo.sdui.ui.components.banking

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.ui.graphics.vector.ImageVector

internal fun bankActionIcon(iconName: String): ImageVector = when (iconName) {
    "send", "send_to_mobile", "quick_transfer" -> Icons.Default.Send
    "history"                                  -> Icons.Default.History
    "more_horiz"                               -> Icons.Default.MoreHoriz
    "qr_code", "qr_code_scanner", "qr"        -> Icons.Default.QrCode
    "currency_exchange", "swap_horiz", "forex" -> Icons.Default.SwapHoriz
    "credit_card", "card_payment"              -> Icons.Default.CreditCard
    "wallet"                                   -> Icons.Default.AccountBalanceWallet
    else                                       -> Icons.Default.Star
}
