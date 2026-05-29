package com.yoimerdr.compose.ludens.ui.extensions

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard

actual suspend fun Clipboard.setClipText(text: String) {
    val clipData = ClipData.newPlainText("text/plain", text)
    this.setClipEntry(ClipEntry(clipData))
}
