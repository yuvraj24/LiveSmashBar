package com.yuvraj.livesmashbar

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.view.*
import com.yuvraj.livesmashbar.enums.BarPosition
import java.lang.reflect.InvocationTargetException

/**
 * Created by Yuvraj on 29/04/18.
 */

const val DEFAULT_SHADOW_STRENGTH = 4
const val DEFAULT_ICON_SCALE = 1.0f

val DURATION_SHORT = 1000L
val DURATION_LONG = 2500L
val DURATION_INDEFINITE = -1L


fun Activity?.getRootView(): ViewGroup? {
    if (this == null || window == null || window.decorView == null) {
        return null
    }
    return window.decorView as ViewGroup
}

@Suppress("DEPRECATION")
inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                f()
            }
        }
    })
}

internal fun Activity.getStatusBarHeightInPx(): Int {
    val rectangle = Rect()

    window.decorView.getWindowVisibleDisplayFrame(rectangle)

    val statusBarHeight = rectangle.top
    val contentViewTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top

    return statusBarHeight
}

internal fun Activity.getNavigationBarPosition(): BarPosition {
    return when (windowManager.defaultDisplay.rotation) {
        Surface.ROTATION_0 -> BarPosition.BOTTOM
        Surface.ROTATION_90 -> BarPosition.RIGHT
        Surface.ROTATION_270 -> BarPosition.LEFT
        else -> BarPosition.TOP
    }
}

internal fun Activity.getNavigationBarSizeInPx(): Int {
    val realScreenSize = getRealScreenSize()
    val appUsableScreenSize = getAppUsableScreenSize()
    val navigationBarPosition = getNavigationBarPosition()

    return if (navigationBarPosition == BarPosition.LEFT || navigationBarPosition == BarPosition.RIGHT) {
        realScreenSize.x - appUsableScreenSize.x
    } else {
        realScreenSize.y - appUsableScreenSize.y
    }
}

private fun Activity.getRealScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        defaultDisplay.getRealSize(size)
    } else  {
        try {
            size.x = Display::class.java.getMethod("getRawWidth").invoke(defaultDisplay) as Int
            size.y = Display::class.java.getMethod("getRawHeight").invoke(defaultDisplay) as Int
        } catch (e: IllegalAccessException) {
        } catch (e: InvocationTargetException) {
        } catch (e: NoSuchMethodException) {
        }
    }
    return size
}

private fun Activity.getAppUsableScreenSize(): Point {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val defaultDisplay = windowManager.defaultDisplay
    val size = Point()
    defaultDisplay.getSize(size)
    return size
}