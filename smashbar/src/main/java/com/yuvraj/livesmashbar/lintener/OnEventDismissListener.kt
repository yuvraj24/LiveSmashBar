package com.yuvraj.livesmashbar.lintener

import com.yuvraj.livesmashbar.enums.DismissEvent
import com.yuvraj.livesmashbar.view.LiveSmashBar

interface OnEventDismissListener {
        fun onDismissing(bar: LiveSmashBar, isSwiped: Boolean)
        fun onDismissProgress(bar: LiveSmashBar, progress: Float)
        fun onDismissed(bar: LiveSmashBar, event: DismissEvent)
    }