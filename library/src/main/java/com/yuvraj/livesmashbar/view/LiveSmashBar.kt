package com.yuvraj.livesmashbar.view

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.annotation.*
import android.support.v4.content.ContextCompat
import android.text.Spanned
import android.widget.ImageView
import com.yuvraj.livesmashbar.DEFAULT_ICON_SCALE
import com.yuvraj.livesmashbar.R
import com.yuvraj.livesmashbar.anim.AnimBarBuilder
import com.yuvraj.livesmashbar.anim.AnimIconBuilder
import com.yuvraj.livesmashbar.enums.BarStyle
import com.yuvraj.livesmashbar.enums.GravityView
import com.yuvraj.livesmashbar.enums.ProgressPosition
import com.yuvraj.livesmashbar.enums.Vibration
import com.yuvraj.livesmashbar.lintener.OnEventDismissListener
import com.yuvraj.livesmashbar.lintener.OnEventListener
import com.yuvraj.livesmashbar.lintener.OnEventShowListener
import com.yuvraj.livesmashbar.lintener.OnEventTapListener

/**
 * Created by Yuvraj on 29/04/18.
 */
open class LiveSmashBar(internal var builder: Builder) {

    private val barContainerView = BarContainerView(builder.activity);

    internal fun create() {
        barContainerView.attachSmashBar(this)
        barContainerView.createSmashBar(builder.gravity)

        with(barContainerView) {
            setBarBackgroundColor(builder.backgroundColor)
            setBarBackgroundDrawable(builder.backgroundDrawable)
            setDuration(builder.duration)

            setEnterAnim(builder.enterAnimBuilder)
            setExitAnim(builder.exitAnimBuilder)

            setBarTapListener(builder.onBarTapListener)
            setBarShowListener(builder.onEventShowListener)
            setBarDismissListener(builder.onEventDismissListener)
            setBarDismissOnTapOutside(builder.barDismissOnTapOutside)
            setOnTapOutsideListener(builder.onTapOutsideListener)

            setOverlayColor(builder.overlayColor)
            setOverlay(builder.overlay)
            setOverlayBlockable(builder.overlayBlockable)
            handleOverlay()

            setTitle(builder.title)
            setTitleSpanned(builder.titleSpanned)
            setTitleTypeface(builder.titleTypeface)
            setTitleSizeInPx(builder.titleSizeInPx)
            setTitleSizeInSp(builder.titleSizeInSp)
            setTitleColor(builder.titleColor)
            setTitleAppearance(builder.titleAppearance)

            setMessage(builder.description)
            setMessageSpanned(builder.descriptionSpanned)
            setMessageTypeface(builder.descriptionTypeface)
            setMessageSizeInPx(builder.descriptionSizeInPx)
            setMessageSizeInSp(builder.descriptionSizeInSp)
            setMessageColor(builder.descriptionColor)
            setMessageAppearance(builder.descriptionAppearance)

            setPrimaryActionText(builder.primaryActionText)
            setPrimaryActionTextSpanned(builder.primaryActionTextSpanned)
            setPrimaryActionTextTypeface(builder.primaryActionTextTypeface)
            setPrimaryActionTextSizeInPx(builder.primaryActionTextSizeInPx)
            setPrimaryActionTextSizeInSp(builder.primaryActionTextSizeInSp)
            setPrimaryActionTextColor(builder.primaryActionTextColor)
            setPrimaryActionTextAppearance(builder.primaryActionTextAppearance)
            setPrimaryActionTapListener(builder.onPrimaryActionEventListener)

            setPositiveActionText(builder.positiveActionText)
            setPositiveActionTextSpanned(builder.positiveActionTextSpanned)
            setPositiveActionTextTypeface(builder.positiveActionTextTypeface)
            setPositiveActionTextSizeInPx(builder.positiveActionTextSizeInPx)
            setPositiveActionTextSizeInSp(builder.positiveActionTextSizeInSp)
            setPositiveActionTextColor(builder.positiveActionTextColor)
            setPositiveActionTextAppearance(builder.positiveActionTextAppearance)
            setPositiveActionTapListener(builder.onPositiveActionEventListener)

            setNegativeActionText(builder.negativeActionText)
            setNegativeActionTextSpanned(builder.negativeActionTextSpanned)
            setNegativeActionTextTypeface(builder.negativeActionTextTypeface)
            setNegativeActionTextSizeInPx(builder.negativeActionTextSizeInPx)
            setNegativeActionTextSizeInSp(builder.negativeActionTextSizeInSp)
            setNegativeActionTextColor(builder.negativeActionTextColor)
            setNegativeActionTextAppearance(builder.negativeActionTextAppearance)
            setNegativeActionTapListener(builder.onNegativeActionEventListener)

            showIcon(builder.showIcon)
            showIconScale(builder.iconScale, builder.iconScaleType)
            setIconDrawable(builder.iconDrawable)
            setIconBitmap(builder.iconBitmap)
            setIconColorFilter(builder.iconColorFilter, builder.iconColorFilterMode)
            setIconAnim(builder.iconAnimBuilder)
        }
    }

    /**
     * Shows a LiveSmashbar
     */
    fun show() {
        barContainerView.show(builder.activity)
    }

    /**
     * Dismisses a LiveSmashbar
     */
    fun dismiss() {
        barContainerView.dismiss()
    }

    /**
     * Returns true/false depending on whether the LiveSmashbar is showing or not
     * This represents the partial appearance of the LiveSmashbar
     */
    fun isShowing() = barContainerView.isBarShowing()

    /**
     * Returns true/false depending on whether the LiveSmashbar has been completely shown or not
     * This represents the complete appearance of the LiveSmashbar
     */
    fun isShown() = barContainerView.isBarShown()

    open class Builder(var activity: Activity) {

        internal var barStyle = BarStyle.DEFAULT_MESSAGE;

        internal var onBarTapListener: OnEventListener? = null
        internal var onEventShowListener: OnEventShowListener? = null
        internal var onEventDismissListener: OnEventDismissListener? = null
        internal var barDismissOnTapOutside: Boolean = false
        internal var onTapOutsideListener: OnEventListener? = null
        internal var overlay: Boolean = false
        internal var overlayColor: Int = ContextCompat.getColor(activity, R.color.modal)
        internal var overlayBlockable: Boolean = false
        internal var enableSwipeToDismiss: Boolean = false
        internal var vibrationTargets: List<Vibration> = emptyList()
        internal var iconAnimBuilder: AnimIconBuilder? = null

        internal var showIcon: Boolean = false
        internal var iconScale: Float = DEFAULT_ICON_SCALE
        internal var iconScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
        internal var iconDrawable: Drawable? = null
        internal var iconBitmap: Bitmap? = null
        internal var iconColorFilter: Int? = null
        internal var iconColorFilterMode: PorterDuff.Mode? = null

        internal var progressPosition: ProgressPosition? = null
        internal var progressTint: Int? = null

        internal var enterAnimBuilder: AnimBarBuilder = AnimBarBuilder(activity)
        internal var exitAnimBuilder: AnimBarBuilder = AnimBarBuilder(activity)

        internal var gravity: GravityView = GravityView.BOTTOM;
        internal var backgroundColor: Int? = null;
        internal var backgroundDrawable: Drawable? = null
        internal var duration: Long = -1L

        internal var title: String? = null
        internal var titleSpanned: Spanned? = null
        internal var titleTypeface: Typeface? = null
        internal var titleSizeInPx: Float? = null
        internal var titleSizeInSp: Float? = null
        internal var titleColor: Int? = null
        internal var titleAppearance: Int? = null

        internal var description: String? = null
        internal var descriptionSpanned: Spanned? = null
        internal var descriptionTypeface: Typeface? = null
        internal var descriptionSizeInPx: Float? = null
        internal var descriptionSizeInSp: Float? = null
        internal var descriptionColor: Int? = null
        internal var descriptionAppearance: Int? = null

        internal var primaryActionText: String? = null
        internal var primaryActionTextSpanned: Spanned? = null
        internal var primaryActionTextTypeface: Typeface? = null
        internal var primaryActionTextSizeInPx: Float? = null
        internal var primaryActionTextSizeInSp: Float? = null
        internal var primaryActionTextColor: Int? = null
        internal var primaryActionTextAppearance: Int? = null
        internal var onPrimaryActionEventListener: OnEventTapListener? = null

        internal var positiveActionText: String? = null
        internal var positiveActionTextSpanned: Spanned? = null
        internal var positiveActionTextTypeface: Typeface? = null
        internal var positiveActionTextSizeInPx: Float? = null
        internal var positiveActionTextSizeInSp: Float? = null
        internal var positiveActionTextColor: Int? = null
        internal var positiveActionTextAppearance: Int? = null
        internal var onPositiveActionEventListener: OnEventTapListener? = null

        internal var negativeActionText: String? = null
        internal var negativeActionTextSpanned: Spanned? = null
        internal var negativeActionTextTypeface: Typeface? = null
        internal var negativeActionTextSizeInPx: Float? = null
        internal var negativeActionTextSizeInSp: Float? = null
        internal var negativeActionTextColor: Int? = null
        internal var negativeActionTextAppearance: Int? = null
        internal var onNegativeActionEventListener: OnEventTapListener? = null

        fun setBarStyle(style: BarStyle) = apply {
            this.barStyle = style;
        }

        /**
         * Specifies the gravity from where the LiveSmashBar will be shown (top/bottom)
         * Default gravity is TOP
         */
        fun gravity(gravity: GravityView) = apply { this.gravity = gravity }

        /**
         * Specifies the background drawable of the LiveSmashBar
         */
        fun backgroundDrawable(drawable: Drawable) = apply { this.backgroundDrawable = drawable }

        /**
         * Specifies the background drawable resource of the LiveSmashBar
         */
        fun backgroundDrawable(@DrawableRes drawableId: Int) = apply {
            this.backgroundDrawable = ContextCompat.getDrawable(activity, drawableId)
        }

        /**
         * Specifies the background color of the LiveSmashBar
         */
        fun backgroundColor(@ColorInt color: Int) = apply { this.backgroundColor = color }

        /**
         * Specifies the background color resource of the LiveSmashBar
         */
        fun backgroundColorRes(@ColorRes colorId: Int) = apply {
            this.backgroundColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Sets listener to receive tap events on the surface of the bar
         */
        fun listenBarTaps(listener: OnEventListener) = apply {
            this.onBarTapListener = listener
        }

        /**
         * Specifies the duration for which the LiveSmashBar will be visible
         * By default, the duration is infinite
         */
        fun duration(milliseconds: Long) = apply {
            require(milliseconds > 0) { "Duration can not be negative or zero" }
            this.duration = milliseconds
        }

        /**
         * Sets listener to receive bar showing/shown events
         */
        fun barShowListener(listener: OnEventShowListener) = apply {
            this.onEventShowListener = listener
        }

        /**
         * Sets listener to receive bar dismissing/dismissed events
         */
        fun barDismissListener(listener: OnEventDismissListener) = apply {
            this.onEventDismissListener = listener
        }

        /**
         * Sets listener to receive tap events outside LiveSmashBar surface
         */
        fun listenOutsideTaps(listener: OnEventListener) = apply {
            this.onTapOutsideListener = listener
        }

        /**
         * Dismisses the bar on being tapped outside
         */
        fun dismissOnTapOutside() = apply {
            this.barDismissOnTapOutside = true
        }

        /**
         * Shows the modal overlay
         */
        fun showOverlay() = apply { this.overlay = true }

        /**
         * Specifies modal overlay color
         */
        fun overlayColor(@ColorInt color: Int) = apply {
            this.overlayColor = color
        }

        /**
         * Specifies modal overlay color resource
         * Modal overlay is automatically shown if color is set
         */
        fun overlayColorRes(@ColorRes colorId: Int) = apply {
            this.overlayColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies if modal overlay is blockable and should comsume touch events
         */
        fun overlayBlockable() = apply {
            this.overlayBlockable = true
        }

        /**
         * Specifies the enter animation of the LiveSmashBar
         */
        fun enterAnimation(builder: AnimBarBuilder) = apply {
            this.enterAnimBuilder = builder
        }

        /**
         * Specifies the exit animation of the LiveSmashBar
         */
        fun exitAnimation(builder: AnimBarBuilder) = apply {
            this.exitAnimBuilder = builder
        }

        /**
         * Enables swipe-to-dismiss for the LiveSmashBar
         */
        fun enableSwipeToDismiss() = apply {
            this.enableSwipeToDismiss = true
        }

        /**
         * Specifies whether the device should vibrate during LiveSmashBar enter/exit/both
         */
        fun vibrateOn(vararg vibrate: Vibration) = apply {
            require(vibrate.isNotEmpty()) { "Vibration targets can not be empty" }
            this.vibrationTargets = vibrate.toList()
        }

        /**
         * Specifies the title string
         */
        fun title(title: String) = apply { this.title = title }

        /**
         * Specifies the title string res
         */
        fun title(@StringRes titleId: Int) = apply { this.title = activity.getString(titleId) }

        /**
         * Specifies the title span
         */
        fun title(title: Spanned) = apply { this.titleSpanned = title }

        /**
         * Specifies the title typeface
         */
        fun titleTypeface(typeface: Typeface) = apply { this.titleTypeface = typeface }

        /**
         * Specifies the title size (in pixels)
         */
        fun titleSizeInPx(size: Float) = apply { this.titleSizeInPx = size }

        /**
         * Specifies the title size (in sp)
         */
        fun titleSizeInSp(size: Float) = apply { this.titleSizeInSp = size }

        /**
         * Specifies the title color
         */
        fun titleColor(@ColorInt color: Int) = apply { this.titleColor = color }

        /**
         * Specifies the title color resource
         */
        fun titleColorRes(@ColorRes colorId: Int) = apply {
            this.titleColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the title appearance
         */
        fun titleAppearance(@StyleRes appearance: Int) = apply {
            this.titleAppearance = appearance
        }

        /**
         * Specifies the description string
         */
        fun description(description: String) = apply { this.description = description }

        /**
         * Specifies the description string resource
         */
        fun description(@StringRes descriptionId: Int) = apply {
            this.description = activity.getString(descriptionId)
        }

        /**
         * Specifies the description string span
         */
        fun description(description: Spanned) = apply { this.descriptionSpanned = description }

        /**
         * Specifies the description typeface
         */
        fun descriptionTypeface(typeface: Typeface) = apply { this.descriptionTypeface = typeface }

        /**
         * Specifies the description size (in pixels)
         */
        fun descriptionSizeInPx(size: Float) = apply { this.descriptionSizeInPx = size }

        /**
         * Specifies the description size (in sp)
         */
        fun descriptionSizeInSp(size: Float) = apply { this.descriptionSizeInSp = size }

        /**
         * Specifies the description color
         */
        fun descriptionColor(@ColorInt color: Int) = apply { this.descriptionColor = color }

        /**
         * Specifies the description color resource
         */
        fun descriptionColorRes(@ColorRes colorId: Int) = apply {
            this.descriptionColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the description appearance
         */
        fun descriptionAppearance(@StyleRes appearance: Int) = apply {
            this.descriptionAppearance = appearance
        }

        /**
         * Specifies the primary action text string
         */
        fun primaryActionText(text: String) = apply {
            require(progressPosition != ProgressPosition.RIGHT,
                    { "Cannot show action button if right progress is set" })
            this.primaryActionText = text
        }

        /**
         * Specifies the primary action text string resource
         */
        fun primaryActionText(@StringRes actionTextId: Int) = apply {
            primaryActionText(activity.getString(actionTextId))
        }

        /**
         * Specifies the primary action text string span
         */
        fun primaryActionText(actionText: Spanned) = apply { this.primaryActionTextSpanned = actionText }

        /**
         * Specifies the primary action text typeface
         */
        fun primaryActionTextTypeface(typeface: Typeface) = apply { this.primaryActionTextTypeface = typeface }

        /**
         * Specifies the primary action text size (in pixels)
         */
        fun primaryActionTextSizeInPx(size: Float) = apply { this.primaryActionTextSizeInPx = size }

        /**
         * Specifies the primary action text size (in sp)
         */
        fun primaryActionTextSizeInSp(size: Float) = apply { this.primaryActionTextSizeInSp = size }

        /**
         * Specifies the primary action text color
         */
        fun primaryActionTextColor(@ColorInt color: Int) = apply { this.primaryActionTextColor = color }

        /**
         * Specifies the primary action text color resource
         */
        fun primaryActionTextColorRes(@ColorRes colorId: Int) = apply {
            this.primaryActionTextColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the primary action text appearance
         */
        fun primaryActionTextAppearance(@StyleRes appearance: Int) = apply {
            this.primaryActionTextAppearance = appearance
        }

        /**
         * Sets listener to receive tap events on the primary action
         */
        fun primaryActionEventListener(onActionTapListener: OnEventTapListener) = apply {
            this.onPrimaryActionEventListener = onActionTapListener
        }

        /**
         * Specifies the positive action text string
         */
        fun positiveActionText(text: String) = apply {
            require(progressPosition != ProgressPosition.RIGHT,
                    { "Cannot show action button if right progress is set" })
            this.positiveActionText = text
        }

        /**
         * Specifies the positive action text string resource
         */
        fun positiveActionTextRes(@StringRes actionTextId: Int) = apply {
            positiveActionText(activity.getString(actionTextId))
        }

        /**
         * Specifies the positive action text string span
         */
        fun positiveActionTextSpanned(actionText: Spanned) = apply { this.positiveActionTextSpanned = actionText }

        /**
         * Specifies the positive action text size (in pixels)
         */
        fun positiveActionTextSizeInPx(size: Float) = apply { this.positiveActionTextSizeInPx = size }

        /**
         * Specifies the positive action text size (in sp)
         */
        fun positiveActionTextSizeInSp(size: Float) = apply { this.positiveActionTextSizeInSp = size }

        /**
         * Specifies the positive action text color
         */
        fun positiveActionTextColor(@ColorInt color: Int) = apply { this.positiveActionTextColor = color }

        /**
         * Specifies the positive action text color resource
         */
        fun positiveActionTextColorRes(@ColorRes colorId: Int) = apply {
            this.positiveActionTextColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the positive action text appearance
         */
        fun positiveActionTextAppearance(@StyleRes appearance: Int) = apply {
            this.positiveActionTextAppearance = appearance
        }

        /**
         * Sets listener to receive tap events on the positive action
         */
        fun positiveActionEventListener(onActiOnEventListener: OnEventTapListener) = apply {
            this.onPositiveActionEventListener = onActiOnEventListener
        }

        /**
         * Specifies the positive action text string resource
         */
        fun positiveActionText(@StringRes actionTextId: Int) = apply {
            positiveActionText(activity.getString(actionTextId))
        }

        /**
         * Specifies the positive action text string span
         */
        fun positiveActionText(actionText: Spanned) = apply { this.positiveActionTextSpanned = actionText }

        /**
         * Specifies the positive action text typeface
         */
        fun positiveActionTextTypeface(typeface: Typeface) = apply { this.positiveActionTextTypeface = typeface }

        /**
         * Sets listener to receive tap events on the positive action
         */
        fun positiveActiOnEventListener(onActionEventListener: OnEventTapListener) = apply {
            this.onPositiveActionEventListener = onActionEventListener
        }

        /**
         * Specifies the negative action text string
         */
        fun negativeActionText(text: String) = apply {
            this.negativeActionText = text
        }

        /**
         * Specifies the negative action text string resource
         */
        fun negativeActionText(@StringRes actionTextId: Int) = apply {
            negativeActionText(activity.getString(actionTextId))
        }

        /**
         * Specifies the negative action text string span
         */
        fun negativeActionText(actionText: Spanned) = apply { this.negativeActionTextSpanned = actionText }

        /**
         * Specifies the negative action text typeface
         */
        fun negativeActionTextTypeface(typeface: Typeface) = apply { this.negativeActionTextTypeface = typeface }

        /**
         * Specifies the negative action text size (in pixels)
         */
        fun negativeActionTextSizeInPx(size: Float) = apply { this.negativeActionTextSizeInPx = size }

        /**
         * Specifies the negative action text size (in sp)
         */
        fun negativeActionTextSizeInSp(size: Float) = apply { this.negativeActionTextSizeInSp = size }

        /**
         * Specifies the negative action text color
         */
        fun negativeActionTextColor(@ColorInt color: Int) = apply { this.negativeActionTextColor = color }

        /**
         * Specifies the negative action text color resource
         */
        fun negativeActionTextColorRes(@ColorRes colorId: Int) = apply {
            this.negativeActionTextColor = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Specifies the negative action text appearance
         */
        fun negativeActionTextAppearance(@StyleRes appearance: Int) = apply {
            this.negativeActionTextAppearance = appearance
        }

        /**
         * Sets listener to receive tap events on the negative action
         */
        fun negativeActionEventListener(onActiOnEventListener: OnEventTapListener) = apply {
            this.onNegativeActionEventListener = onActiOnEventListener
        }

        /**
         * Specifies if the icon should be shown. Also configures its scale factor and scale type
         */
        @JvmOverloads
        fun showIcon(scale: Float = DEFAULT_ICON_SCALE, scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP) = apply {
            require(progressPosition != ProgressPosition.LEFT,
                    { "Cannot show icon if left progress is set" })
            require(scale > 0,
                    { "Icon scale cannot be negative or zero" })

            this.showIcon = true
            this.iconScale = scale
            this.iconScaleType = scaleType
        }

        /**
         * Specifies the icon drawable
         */
        fun icon(icon: Drawable) = apply {
            this.iconDrawable = icon
            showIcon()
        }

        /**
         * Specifies the icon drawable resource
         */
        fun icon(@DrawableRes iconId: Int) = apply {
            this.iconDrawable = ContextCompat.getDrawable(activity, iconId)
            showIcon()
        }

        fun getDrawable(): Drawable? {
            return this.iconDrawable;
        }

        /**
         * Specifies the icon bitmap
         */
        fun icon(bitmap: Bitmap) = apply {
            this.iconBitmap = bitmap
            showIcon()
        }

        fun getBitmap(): Bitmap? {
            return this.iconBitmap;
        }

        /**
         * Specifies the icon color filter and mode
         */
        @JvmOverloads
        fun iconColorFilter(@ColorInt color: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = color
            this.iconColorFilterMode = mode
        }

        /**
         * Specifies the icon color filter resource and mode
         */
        @JvmOverloads
        fun iconColorFilterRes(@ColorRes colorId: Int, mode: PorterDuff.Mode? = null) = apply {
            this.iconColorFilter = ContextCompat.getColor(activity, colorId)
            this.iconColorFilterMode = mode
        }

        /**
         * Specifies the icon builder
         */
        fun iconAnimation(builder: AnimIconBuilder) = apply { this.iconAnimBuilder = builder }

        /**
         * Specifies the gravity in which the indeterminate progress is shown (left/right)
         */
        fun showProgress(position: ProgressPosition) = apply {
            this.progressPosition = position

            if (progressPosition == ProgressPosition.LEFT && showIcon) {
                throw IllegalArgumentException("Cannot show progress at left if icon is already set")
            }

            if (progressPosition == ProgressPosition.RIGHT && positiveActionText != null) {
                throw IllegalArgumentException("Cannot show progress at right if action button is already set")
            }
        }

        /**
         * Specifies the indeterminate progress tint
         */
        fun progressTint(@ColorInt color: Int) = apply {
            this.progressTint = color
        }

        /**
         * Specifies the indeterminate progress tint resource
         */
        fun progressTintRes(@ColorRes colorId: Int) = apply {
            this.progressTint = ContextCompat.getColor(activity, colorId)
        }

        /**
         * Builds a LiveSmashBar instance
         */
        internal fun build(): LiveSmashBar {
            configureAnimation()
            val liveSmashBar = LiveSmashBar(this)
            liveSmashBar.create()
            return liveSmashBar
        }

        /**
         * Shows the LiveSmashBar
         */
        fun show() = build().show()

        internal fun configureAnimation() {
            enterAnimBuilder =/* if (enterAnimBuilder == null) {
                when (gravity) {
                    GravityView.TOP -> FlashAnim.with(activity).animateBar().enter().fromTop()
                    GravityView.BOTTOM -> FlashAnim.with(activity).animateBar().enter().fromBottom()
                }
            } else {*/
                    when (gravity) {
                        GravityView.TOP -> enterAnimBuilder.enter().fromTop()
                        GravityView.BOTTOM -> enterAnimBuilder.enter().fromBottom()
                    }
            /*}*/

            exitAnimBuilder = /*if (exitAnimBuilder == null) {
                when (gravity) {
                    GravityView.TOP -> FlashAnim.with(activity).animateBar().exit().fromTop()
                    GravityView.BOTTOM -> FlashAnim.with(activity).animateBar().exit().fromBottom()
                }
            } else {*/
                    when (gravity) {
                        GravityView.TOP -> exitAnimBuilder.exit().fromTop()
                        GravityView.BOTTOM -> exitAnimBuilder.exit().fromBottom()
                    }
            /*}*/
        }

        /**
         * set LiveData listener for any post callbacks.
         **/
        fun liveDataCallback(lifecycleOwner: LifecycleOwner, liveData: MutableLiveData<Unit>) {
            liveData.observe(lifecycleOwner, Observer {
                show()
            })
        }
    }
}