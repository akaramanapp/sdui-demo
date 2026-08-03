package com.demo.sdui.ui

import androidx.compose.runtime.compositionLocalOf

/** App-wide action handler. Provides navigation callbacks from any component. */
val LocalActionHandler = compositionLocalOf<(String) -> Unit> { {} }
