package com.example.superfastdelivery

import com.example.auth.domain.session_state_holder.SessionStateHolder

interface ApplicationStateStore {
    val sessionStateHolder: SessionStateHolder
}

class ApplicationStateStoreImpl(
    override val sessionStateHolder: SessionStateHolder
) : ApplicationStateStore