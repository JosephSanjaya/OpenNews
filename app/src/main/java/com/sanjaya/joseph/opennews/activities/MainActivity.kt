/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.opennews.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sanjaya.joseph.opennews.R
import com.sanjaya.joseph.opennews.databinding.ActivityMainBinding
import com.sanjaya.joseph.opennews.fragments.CategoriesFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val mBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager
            .beginTransaction()
            .replace(mBinding.fragmentPlaceholder.id, CategoriesFragment())
            .commit()
    }
}
