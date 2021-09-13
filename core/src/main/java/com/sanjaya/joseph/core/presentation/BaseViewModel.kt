/*
 * Copyright (c) 2021 Designed and developed by Joseph Sanjaya, S.T., M.Kom., All
 * Rights Reserved.
 * @Github (https://github.com/JosephSanjaya),
 * @LinkedIn (https://www.linkedin.com/in/josephsanjaya/))
 */

package com.sanjaya.joseph.core.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sanjaya.joseph.core.domain.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex

abstract class BaseViewModel : ViewModel() {

    protected val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
    protected val mainScope = CoroutineScope(Dispatchers.Main + exceptionHandler)
    protected val ioScope = CoroutineScope(Dispatchers.IO + exceptionHandler)
    protected val defaultScope = CoroutineScope(viewModelScope.coroutineContext + exceptionHandler)
    val mutex by lazy { Mutex() }

    override fun onCleared() {
        super.onCleared()
        defaultScope.coroutineContext.cancel()
        mainScope.coroutineContext.cancel()
        ioScope.coroutineContext.cancel()
    }

    fun <T> MutableStateFlow<State.Single<T>>.resetSingleState() {
        value = State.Single.Idle()
    }

    fun <T, K> MutableStateFlow<State.Double<T, K>>.resetDoubleState() {
        value = State.Double.Idle()
    }

    fun <T, K, V> MutableStateFlow<State.Triple<T, K, V>>.resetTripleState() {
        value = State.Triple.Idle()
    }

    fun <T, K, V, X> MutableStateFlow<State.Quad<T, K, V, X>>.resetQuadState() {
        value = State.Quad.Idle()
    }

    fun <T> MutableStateFlow<T>.asImmutable() = this as StateFlow<T>

    fun <T> MutableLiveData<T>.asImmutable() = this as LiveData<T>
}
