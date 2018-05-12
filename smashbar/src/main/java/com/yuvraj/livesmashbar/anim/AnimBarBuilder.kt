package com.yuvraj.livesmashbar.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.support.annotation.InterpolatorRes
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import com.yuvraj.livesmashbar.enums.AnimDirection
import com.yuvraj.livesmashbar.enums.AnimType
import com.yuvraj.livesmashbar.enums.GravityView

class AnimBarBuilder(context: Context) : BaseSmashBarBuilder(context) {

    private var type: AnimType? = AnimType.ENTER
    private var gravity: GravityView = GravityView.BOTTOM
    private var direction: AnimDirection? = null// = AnimDirection.RIGHT

    /**
     * Specifies the duration (in millis) for the animation
     */
    override fun duration(millis: Long) = apply { super.duration(millis) }

    /**
     * Specifies accelerate interpolator for the animation
     */
    override fun accelerate() = apply { super.accelerate() }

    /**
     * Specifies decelerate interpolator for the animation
     */
    override fun decelerate() = apply { super.decelerate() }

    /**
     * Specifies accelerate-decelerate interpolator for the animation
     */
    override fun accelerateDecelerate() = apply { super.accelerateDecelerate() }

    /**
     * Specifies custom interpolator for the animation
     */
    override fun interpolator(interpolator: Interpolator) = apply { super.interpolator(interpolator) }

    /**
     * Specifies custom interpolator resource for the animation
     */
    override fun interpolator(@InterpolatorRes id: Int) = apply { super.interpolator(id) }

    /**
     * Specifies that the animation should have alpha effect
     */
    override fun alpha() = apply { super.alpha() }

    /**
     * Specifies that the bar should slide to/from the left
     */
    fun slideFromLeft() = apply {
        this.direction = AnimDirection.LEFT
    }

    /**
     * Specifies that the bar should slide to/from the right
     */
    fun slideFromRight() = apply {
        this.direction = AnimDirection.RIGHT
    }

    /**
     * Specifies overshoot interpolator for the animation
     */
    fun overshoot() = apply {
        this.interpolator = OvershootInterpolator()
    }

    /**
     * Specifies overshoot-anticipate interpolator for the animation
     */
    fun anticipateOvershoot() = apply {
        this.interpolator = AnticipateInterpolator()
    }

    override fun withView(view: View) = apply { super.withView(view) }

    internal fun enter() = apply {
        this.type = AnimType.ENTER
    }

    internal fun exit() = apply {
        this.type = AnimType.EXIT
    }

    internal fun fromTop() = apply {
        this.gravity = GravityView.TOP
    }

    internal fun fromBottom() = apply {
        this.gravity = GravityView.BOTTOM
    }

    internal fun build(): FlashAnim {
        requireNotNull(view, { "Target view can not be null" })

        val compositeAnim = AnimatorSet()
        val animators = linkedSetOf<Animator>()

        val translationAnim = ObjectAnimator()
        // Slide from left/right animation is not specified, default top/bottom
        // animation is applied
        if (direction == null) {
            translationAnim.propertyName = "translationY"

            when (type) {
                AnimType.ENTER -> when (gravity) {
                    GravityView.TOP -> translationAnim.setFloatValues(-view!!.height.toFloat(), 0f)
                    GravityView.BOTTOM -> translationAnim.setFloatValues(view!!.height.toFloat(), 0f)
                }
                AnimType.EXIT -> when (gravity) {
                    GravityView.TOP -> translationAnim.setFloatValues(0f, -view!!.height.toFloat())
                    GravityView.BOTTOM -> translationAnim.setFloatValues(0f, view!!.height.toFloat())
                }
            }
        } else {
            translationAnim.propertyName = "translationX"

            when (type) {
                AnimType.ENTER -> when (direction) {
                    AnimDirection.LEFT -> translationAnim.setFloatValues(-view!!.width.toFloat(), 0f)
                    AnimDirection.RIGHT -> translationAnim.setFloatValues(view!!.width.toFloat(), 0f)
                }
                AnimType.EXIT -> when (direction!!) {
                    AnimDirection.LEFT -> translationAnim.setFloatValues(0f, -view!!.width.toFloat())
                    AnimDirection.RIGHT -> translationAnim.setFloatValues(0f, view!!.width.toFloat())
                }
            }
        }

        translationAnim.target = view
        animators.add(translationAnim)

        if (alpha) {
            val alphaAnim = ObjectAnimator()
            alphaAnim.propertyName = "alpha"
            alphaAnim.target = view

            when (type) {
                AnimType.ENTER -> alphaAnim.setFloatValues(DEFAULT_ALPHA_START, DEFAULT_ALPHA_END)
                AnimType.EXIT -> alphaAnim.setFloatValues(DEFAULT_ALPHA_END, DEFAULT_ALPHA_START)
            }
            animators.add(alphaAnim)
        }

        compositeAnim.playTogether(animators)
        compositeAnim.duration = duration
        compositeAnim.interpolator = interpolator

        return FlashAnim(compositeAnim)
    }


}