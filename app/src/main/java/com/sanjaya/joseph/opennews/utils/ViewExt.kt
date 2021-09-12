/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.utils

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.transition.MaterialFade
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.LayoutNoDataBinding

fun ViewGroup.prepareAnim() {
    val materialFade = MaterialFade().apply {
        duration = context.resources.getInteger(
            R.integer.material_motion_duration_medium_2
        ).toLong()
    }
    TransitionManager.beginDelayedTransition(this, materialFade)
}

fun LayoutInflater.buildEmptyAdapterView(
    title: String,
    msg: String,
    logo: Int,
    msgSpan: SpannableStringBuilder? = null,
): View {
    LayoutNoDataBinding.inflate(this).let {
        it.tvTitle.text = title
        it.tvDesc.text = msgSpan ?: msg
        it.ivBigLogo.load(logo)
        return it.root
    }
}
