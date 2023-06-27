package com.example.commonmodule.extensions

import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun LinearLayoutCompat.clearText() {
    withContext(Dispatchers.Main) {
        removeAllViews()
    }
}

suspend fun LinearLayoutCompat.addText(output: String) {
    val textView = TextView(context).apply {
        layoutParams =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 20, 0, 0)
                setPadding(10, 10, 0, 10)
            }
        text = output
    }
    withContext(Dispatchers.Main) {
        addView(textView)
    }
}