package com.yuvraj.livesmashbar.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.support.annotation.ColorInt
import android.text.Spanned
import android.text.TextUtils
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.yuvraj.livesmashbar.*
import com.yuvraj.livesmashbar.anim.AnimBarBuilder
import com.yuvraj.livesmashbar.anim.AnimIconBuilder
import com.yuvraj.livesmashbar.anim.FlashAnim
import com.yuvraj.livesmashbar.enums.BarPosition
import com.yuvraj.livesmashbar.enums.BarStyle
import com.yuvraj.livesmashbar.enums.DismissEvent
import com.yuvraj.livesmashbar.enums.GravityView
import com.yuvraj.livesmashbar.lintener.OnEventDismissListener
import com.yuvraj.livesmashbar.lintener.OnEventListener
import com.yuvraj.livesmashbar.lintener.OnEventShowListener
import com.yuvraj.livesmashbar.lintener.OnEventTapListener
import kotlinx.android.synthetic.main.bar_default_message.view.*

/**
 * Created by Yuvraj on 29/04/18.
 */
class BarContainerView(val activity: Context) : RelativeLayout(activity) {

    lateinit var liveSmashBar: LiveSmashBar;

    private var onBarShowListener: OnEventShowListener? = null
    private var onBarDismissListener: OnEventDismissListener? = null
    private var onTapOutsideListener: OnEventListener? = null

    private var overlayColor: Int? = R.color.translucent_black
    private var showOverlay: Boolean = false
    private var overlayBlockable: Boolean = false

    private var duration = DURATION_INDEFINITE
    private var isBarShowing = false
    private var isBarShown = false
    private var isBarDismissing = false
    private var barDismissOnTapOutside: Boolean = false

    private var enterAnimBuilder: AnimBarBuilder = AnimBarBuilder(activity)
    private var exitAnimBuilder: AnimBarBuilder = AnimBarBuilder(activity)
    private var iconAnimBuilder: AnimIconBuilder? = null

    private val TOP_COMPENSATION_MARGIN = resources.getDimension(R.dimen.top_compensation_margin).toInt()
    private val BOTTOM_COMPENSATION_MARGIN = resources.getDimension(R.dimen.bottom_compensation_margin).toInt()

    private lateinit var gravity: GravityView

    private var isMarginCompensationApplied: Boolean = false

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val rect = Rect()
                relative_root.getHitRect(rect)

                // Checks if the tap was outside the bar
                if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                    onTapOutsideListener?.onTap(liveSmashBar)

                    if (barDismissOnTapOutside) {
                        dismissInternal(DismissEvent.TAP_OUTSIDE)
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (!isMarginCompensationApplied) {
            isMarginCompensationApplied = true

//            val params = layoutParams as ViewGroup.MarginLayoutParams
//            when (gravity) {
//                GravityView.TOP -> params.topMargin = -TOP_COMPENSATION_MARGIN
//                GravityView.BOTTOM -> params.bottomMargin = -BOTTOM_COMPENSATION_MARGIN
//            }
            requestLayout()
        }
    }

    fun attachSmashBar(liveSmashBar: LiveSmashBar) {
        this.liveSmashBar = liveSmashBar;
    }

    fun createSmashBar(gravity: GravityView) {

        this.gravity = gravity

        when (liveSmashBar.builder.barStyle) {
            BarStyle.DEFAULT_MESSAGE -> {
                showDefaultMessageView()
            }
            BarStyle.DIALOG -> {
                showDialogView()
            }
            else -> {
                showDefaultMessageView()
            }
        }

        adjustWitPositionAndOrientation(activity as Activity)
    }

    fun handleOverlay() {
        if (showOverlay) {

            setBackgroundColor(overlayColor!!)

            if (overlayBlockable) {
                isClickable = true
                isFocusable = true
            }
        }
    }

    internal fun adjustWitPositionAndOrientation(activity: Activity) {
        val barViewLp = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val statusBarHeight = activity.getStatusBarHeightInPx()

        val barViewContentLp = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        when (gravity) {
            GravityView.TOP -> {
                barViewContentLp.topMargin = statusBarHeight//.plus(TOP_COMPENSATION_MARGIN / 2)
                barViewLp.addRule(ALIGN_PARENT_TOP)
            }
            GravityView.BOTTOM -> {
//                barViewContentLp.bottomMargin = BOTTOM_COMPENSATION_MARGIN
                barViewLp.addRule(ALIGN_PARENT_BOTTOM)
            }
        }

        val navigationBarPosition = activity.getNavigationBarPosition()
        val navigationBarSize = activity.getNavigationBarSizeInPx()

        when (navigationBarPosition) {
            BarPosition.LEFT -> barViewContentLp.leftMargin = navigationBarSize
            BarPosition.RIGHT -> barViewContentLp.rightMargin = navigationBarSize
            BarPosition.BOTTOM -> barViewContentLp.bottomMargin = navigationBarSize
            else -> {
                barViewContentLp.bottomMargin = navigationBarSize
            }
        }

        relative_root.layoutParams = barViewLp
        this.layoutParams = barViewContentLp
    }

    private fun showDefaultMessageView() {
        inflate(activity, R.layout.bar_default_message, this);
    }

    private fun showDialogView() {
        inflate(activity, R.layout.bar_default_message, this);
        this.linear_actions.visibility = View.VISIBLE
    }

    internal fun show(activity: Activity) {
        if (isBarShowing || isBarShown) return

        val activityRootView = activity.getRootView() ?: return

        // Only add the withView to the parent once
        if (this.parent == null) activityRootView.addView(this@BarContainerView)

        activityRootView.afterMeasured {
            val enterAnim = enterAnimBuilder.withView(relative_root).build()
            enterAnim.start(object : FlashAnim.InternalAnimListener {
                override fun onStart() {
                    isBarShowing = true
                    onBarShowListener?.onShowing(liveSmashBar)
                }

                override fun onUpdate(progress: Float) {
                    onBarShowListener?.onShowProgress(liveSmashBar, progress)
                }

                override fun onStop() {
                    isBarShowing = false
                    isBarShown = true

                    startIconAnimation(iconAnimBuilder)

                    onBarShowListener?.onShown(liveSmashBar)
                }
            })

            handleDismiss()
        }
    }

    internal fun dismiss() {
        dismissInternal(DismissEvent.MANUAL)
    }

    private fun handleDismiss() {
        if (duration != DURATION_INDEFINITE) {
            postDelayed({ dismissInternal(DismissEvent.TIMEOUT) }, duration)
        }
    }

    private fun dismissInternal(event: DismissEvent) {
        if (isBarDismissing || isBarShowing || !isBarShown) {
            return
        }

        val exitAnim = exitAnimBuilder.withView(relative_root).build()
        exitAnim.start(object : FlashAnim.InternalAnimListener {
            override fun onStart() {
                isBarDismissing = true
                onBarDismissListener?.onDismissing(liveSmashBar, false)
            }

            override fun onUpdate(progress: Float) {
                onBarDismissListener?.onDismissProgress(liveSmashBar, progress)
            }

            override fun onStop() {
                isBarDismissing = false
                isBarShown = false

                onBarDismissListener?.onDismissed(liveSmashBar, event)

                post { (parent as? ViewGroup)?.removeView(this@BarContainerView) }
            }
        })
    }

    internal fun isBarShowing() = isBarShowing

    internal fun isBarShown() = isBarShown

    internal fun setDuration(duration: Long) {
        this.duration = duration
    }

    internal fun setBarShowListener(listener: OnEventShowListener?) {
        this.onBarShowListener = listener
    }

    internal fun setBarDismissListener(listener: OnEventDismissListener?) {
        this.onBarDismissListener = listener
    }

    internal fun setBarDismissOnTapOutside(dismiss: Boolean) {
        this.barDismissOnTapOutside = dismiss
    }

    internal fun setOnTapOutsideListener(listener: OnEventListener?) {
        this.onTapOutsideListener = listener
    }

    internal fun setBarBackgroundDrawable(drawable: Drawable?) {
        if (drawable == null) return

        if (SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.relative_root.background = drawable
        } else {
            this.relative_root.setBackgroundDrawable(drawable)
        }
    }

    internal fun setBarBackgroundColor(@ColorInt color: Int?) {
        if (color == null) return
        this.relative_root.setBackgroundColor(color)
    }

    internal fun setBarTapListener(listener: OnEventListener?) {
        if (listener == null) return

        this.relative_root.setOnClickListener {
            listener.onTap(liveSmashBar)
        }
    }

    internal fun setTitle(title: String?) {
        if (TextUtils.isEmpty(title)) return

        this.text_bar_title.text = title
        this.text_bar_title.visibility = VISIBLE
    }

    internal fun setTitleSpanned(title: Spanned?) {
        if (title == null) return

        this.text_bar_title.text = title
        this.text_bar_title.visibility = VISIBLE
    }

    internal fun setTitleTypeface(typeface: Typeface?) {
        if (typeface == null) return
        text_bar_title.typeface = typeface
    }

    internal fun setTitleSizeInPx(size: Float?) {
        if (size == null) return
        text_bar_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setTitleSizeInSp(size: Float?) {
        if (size == null) return
        text_bar_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setTitleColor(color: Int?) {
        if (color == null) return
        text_bar_title.setTextColor(color)
    }

    internal fun setTitleAppearance(titleAppearance: Int?) {
        if (titleAppearance == null) return

        if (SDK_INT >= M) {
            this.text_bar_title.setTextAppearance(titleAppearance)
        } else {
            this.text_bar_title.setTextAppearance(text_bar_title.context, titleAppearance)
        }
    }

    internal fun setMessage(message: String?) {
        if (TextUtils.isEmpty(message)) return

        this.text_bar_description.text = message
        this.text_bar_description.visibility = VISIBLE
    }

    internal fun setMessageSpanned(message: Spanned?) {
        if (message == null) return

        this.text_bar_description.text = message
        this.text_bar_description.visibility = VISIBLE
    }

    internal fun setMessageTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.text_bar_description.typeface = typeface
    }

    internal fun setMessageSizeInPx(size: Float?) {
        if (size == null) return
        this.text_bar_description.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setMessageSizeInSp(size: Float?) {
        if (size == null) return
        this.text_bar_description.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setMessageColor(color: Int?) {
        if (color == null) return
        this.text_bar_description.setTextColor(color)
    }

    internal fun setMessageAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.text_bar_description.setTextAppearance(messageAppearance)
        } else {
            this.text_bar_description.setTextAppearance(text_bar_description.context, messageAppearance)
        }
    }

    internal fun setPrimaryActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.btn_primary.text = text
        this.btn_primary.visibility = VISIBLE
    }

    internal fun setPrimaryActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.btn_primary.text = text
        this.btn_primary.visibility = VISIBLE
    }

    internal fun setPrimaryActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.btn_primary.typeface = typeface
    }

    internal fun setPrimaryActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.btn_primary.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setPrimaryActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.btn_primary.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setPrimaryActionTextColor(color: Int?) {
        if (color == null) return
        this.btn_primary.setTextColor(color)
    }

    internal fun setPrimaryActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.btn_primary.setTextAppearance(messageAppearance)
        } else {
            this.btn_primary.setTextAppearance(btn_bar_positive.context, messageAppearance)
        }
    }

    internal fun setPrimaryActionTapListener(listener: OnEventTapListener?) {
        if (listener == null) return

        this.btn_primary.setOnClickListener {
            listener.onActionTapped(liveSmashBar)
        }
    }

    internal fun setPositiveActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.linear_actions.visibility = VISIBLE
        this.btn_bar_positive.text = text
        this.btn_bar_positive.visibility = VISIBLE
    }

    internal fun setPositiveActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.linear_actions.visibility = VISIBLE
        this.btn_bar_positive.text = text
        this.btn_bar_positive.visibility = VISIBLE
    }

    internal fun setPositiveActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.btn_bar_positive.typeface = typeface
    }

    internal fun setPositiveActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.btn_bar_positive.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setPositiveActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.btn_bar_positive.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setPositiveActionTextColor(color: Int?) {
        if (color == null) return
        this.btn_bar_positive.setTextColor(color)
    }

    internal fun setPositiveActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.btn_bar_positive.setTextAppearance(messageAppearance)
        } else {
            this.btn_bar_positive.setTextAppearance(btn_bar_positive.context, messageAppearance)
        }
    }

    internal fun setPositiveActionTapListener(listener: OnEventTapListener?) {
        if (listener == null) return

        this.btn_bar_positive.setOnClickListener {
            listener.onActionTapped(liveSmashBar)
        }
    }

    internal fun setNegativeActionText(text: String?) {
        if (TextUtils.isEmpty(text)) return

        this.linear_actions.visibility = VISIBLE
        this.btn_bar_negative.text = text
        this.btn_bar_negative.visibility = VISIBLE
    }

    internal fun setNegativeActionTextSpanned(text: Spanned?) {
        if (text == null) return

        this.linear_actions.visibility = VISIBLE
        this.btn_bar_negative.text = text
        this.btn_bar_negative.visibility = VISIBLE
    }

    internal fun setNegativeActionTextTypeface(typeface: Typeface?) {
        if (typeface == null) return
        this.btn_bar_negative.typeface = typeface
    }

    internal fun setNegativeActionTextSizeInPx(size: Float?) {
        if (size == null) return
        this.btn_bar_negative.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }

    internal fun setNegativeActionTextSizeInSp(size: Float?) {
        if (size == null) return
        this.btn_bar_negative.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    internal fun setNegativeActionTextColor(color: Int?) {
        if (color == null) return
        this.btn_bar_negative.setTextColor(color)
    }

    internal fun setNegativeActionTextAppearance(messageAppearance: Int?) {
        if (messageAppearance == null) return

        if (SDK_INT >= M) {
            this.btn_bar_negative.setTextAppearance(messageAppearance)
        } else {
            this.btn_bar_negative.setTextAppearance(btn_bar_positive.context, messageAppearance)
        }
    }

    internal fun setNegativeActionTapListener(listener: OnEventTapListener?) {
        if (listener == null) return

        this.btn_bar_negative.setOnClickListener {
            listener.onActionTapped(liveSmashBar)
        }
    }

    internal fun setOverlay(overlay: Boolean) {
        this.showOverlay = overlay
    }

    internal fun setOverlayColor(color: Int) {
        this.overlayColor = color
    }

    internal fun setOverlayBlockable(blockable: Boolean) {
        this.overlayBlockable = blockable
    }

    internal fun showIcon(showIcon: Boolean) {
        this.icon_bar_message.visibility = if (showIcon) VISIBLE else GONE
    }

    internal fun showIconScale(scale: Float, scaleType: ImageView.ScaleType?) {
        this.icon_bar_message.scaleX = scale
        this.icon_bar_message.scaleY = scale
        this.icon_bar_message.scaleType = scaleType
    }

    internal fun setIconDrawable(icon: Drawable?) {
        if (icon == null) return
        this.icon_bar_message.setImageDrawable(icon)
    }

    internal fun setIconBitmap(bitmap: Bitmap?) {
        if (bitmap == null) return
        this.icon_bar_message.setImageBitmap(bitmap)
    }

    internal fun setIconColorFilter(colorFilter: Int?, filterMode: PorterDuff.Mode?) {
        if (colorFilter == null) return
        if (filterMode == null) {
            this.icon_bar_message.setColorFilter(colorFilter)
        } else {
            this.icon_bar_message.setColorFilter(colorFilter, filterMode)
        }
    }

    internal fun setIconAnim(builder: AnimIconBuilder?) {
        this.iconAnimBuilder = builder
    }

    internal fun startIconAnimation(animator: AnimIconBuilder?) {
        animator?.withView(icon_bar_message)?.build()?.start()
    }

    internal fun stopIconAnimation() {
        icon_bar_message.clearAnimation()
    }

    internal fun setEnterAnim(builder: AnimBarBuilder) {
        this.enterAnimBuilder = builder
    }

    internal fun setExitAnim(builder: AnimBarBuilder) {
        this.exitAnimBuilder = builder
    }
}