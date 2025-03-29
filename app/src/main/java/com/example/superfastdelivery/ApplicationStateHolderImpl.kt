package com.example.superfastdelivery

import com.example.core.app_state.state_holder.ApplicationStateHolder
import com.example.core.app_state.state_holder.AuthStateHolder
import com.example.core.app_state.state_holder.ProfileStateHolder
import com.example.core.app_state.state_holder.SessionStateHolder


class ApplicationStateHolderImpl(
    override val sessionStateHolder: SessionStateHolder,
    override val authStateHolder: AuthStateHolder,
    override val profileStateHolder: ProfileStateHolder
) : ApplicationStateHolder