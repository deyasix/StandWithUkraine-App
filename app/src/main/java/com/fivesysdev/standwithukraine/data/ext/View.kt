package com.fivesysdev.standwithukraine.data.ext

import android.view.View

fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}