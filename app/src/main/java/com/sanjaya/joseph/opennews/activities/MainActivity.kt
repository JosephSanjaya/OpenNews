/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.ActivityMainBinding
import com.sanjaya.joseph.opennews.fragments.CategoriesFragment
import com.sanjaya.joseph.opennews.fragments.SourcesFragment
import com.sanjaya.joseph.opennews.utils.replaceFragment
import com.sanjaya.joseph.opennews.utils.setToolbar
import com.skydoves.bundler.intentOf
import com.skydoves.bundler.observeBundle

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mBinding by viewBinding(ActivityMainBinding::bind)
    private val mType: LiveData<Int> by observeBundle(TYPE_KEY, TYPE_CATEGORIES)
    private val mViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mType.observe(
            this,
            {
                it.replaceFragment()
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun Int.replaceFragment() {
        setToolbar(
            true,
            titleRes =
            when (this) {
                TYPE_CATEGORIES -> {
                    supportFragmentManager.replaceFragment(
                        placeholder = mBinding.fragmentPlaceholder.id,
                        fragment = CategoriesFragment()
                    )
                    R.string.label_categories
                }
                TYPE_SOURCES -> {
                    supportFragmentManager.replaceFragment(
                        placeholder = mBinding.fragmentPlaceholder.id,
                        fragment = SourcesFragment()
                    )
                    R.string.label_sources
                }
                else -> R.string.app_name
            }
        )
    }

    companion object {
        const val TYPE_KEY = "type"
        const val TYPE_CATEGORIES = 0
        const val TYPE_SOURCES = 1

        fun Context.launchSources() = intentOf<MainActivity> {
            putExtra(TYPE_KEY, TYPE_SOURCES)
            startActivity(this@launchSources)
        }
    }
}
