package com.yuvraj.livesmashbar.lintener

import com.yuvraj.livesmashbar.view.LiveSmashBar

interface OnEventShowListener {
        fun onShowing(bar: LiveSmashBar)
        fun onShowProgress(bar: LiveSmashBar, progress: Float)
        fun onShown(bar: LiveSmashBar)
    }