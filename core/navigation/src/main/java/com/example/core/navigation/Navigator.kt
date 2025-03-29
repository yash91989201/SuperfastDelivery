package com.example.core.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator @Inject constructor() {
    private val _navigation = MutableSharedFlow<NavigationSubGraphDest>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val navigation = _navigation.asSharedFlow()

    fun navigateBack() {
        _navigation.tryEmit(NavigationSubGraphDest.Back)
    }

    fun navigateTo(subGraphDest: NavigationSubGraphDest) {
        _navigation.tryEmit(subGraphDest)
    }
}