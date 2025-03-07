package com.example.superfastdelivery

import com.example.common.state_holder.ApplicationStateHolder
import com.example.common.state_holder.AuthStateHolder
import com.example.common.state_holder.ProfileStateHolder
import com.example.common.state_holder.SessionStateHolder


class ApplicationStateHolderImpl(
    override val sessionStateHolder: SessionStateHolder,
    override val authStateHolder: AuthStateHolder,
    override val profileStateHolder: ProfileStateHolder
) : ApplicationStateHolder