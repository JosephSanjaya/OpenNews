/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.di

import com.sanjaya.joseph.core.data.api.ApiInterfaces
import com.sanjaya.joseph.core.data.repository.CoreRepository
import com.sanjaya.joseph.core.presentation.core.CoreViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val CoreModules: Module = module {
    single {
        ApiInterfaces(androidContext())
    }
    single {
        CoreRepository(get())
    }
    viewModel {
        CoreViewModel(get())
    }
}
