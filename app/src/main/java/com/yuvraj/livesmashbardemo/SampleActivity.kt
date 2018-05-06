package com.yuvraj.livesmashbardemo

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yuvraj.livesmashbar.anim.AnimIconBuilder
import com.yuvraj.livesmashbar.enums.BarStyle
import com.yuvraj.livesmashbar.enums.DismissEvent
import com.yuvraj.livesmashbar.enums.GravityView
import com.yuvraj.livesmashbar.lintener.OnEventDismissListener
import com.yuvraj.livesmashbar.lintener.OnEventListener
import com.yuvraj.livesmashbar.lintener.OnEventShowListener
import com.yuvraj.livesmashbar.lintener.OnEventTapListener
import com.yuvraj.livesmashbar.view.LiveSmashBar
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity(), OnEventTapListener,
        OnEventListener, OnEventDismissListener, OnEventShowListener,
        SampleRecyclerAdapter.OnItemClickListener, LifecycleOwner {

    var listSamples = arrayListOf<String>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        createSamplesList();

        recycler_samples.layoutManager = LinearLayoutManager(this);
        recycler_samples.adapter = SampleRecyclerAdapter(this, listSamples, this);
    }

    private fun createSamplesList() {
        listSamples.add("Simple LiveSmashBar")
        listSamples.add("Simple LiveSmashBar with Icon & Action button")
        listSamples.add("Simple Dialog LiveSmashBar with Icon & Action button")
        listSamples.add("Simple Overlay LiveSmashBar")
        listSamples.add("Simple LiveSmashBar using LiveData & LifecycleOwner")
        listSamples.add("Simple LiveSmashBar with Gravity Top")
        listSamples.add("Simple LiveSmashBar with Gravity Top , Icon & Action button")
        listSamples.add("Simple Dialog LiveSmashBar with Gravity Top , Icon & Action button")
        listSamples.add("Simple Dialog LiveSmashBar with Gravity Top")
        listSamples.add("Simple LiveSmashBar Pulse Animation effect")
    }

    override fun onActionTapped(bar: LiveSmashBar) {


    }

    override fun onTap(liveSmashBar: LiveSmashBar) {

    }

    override fun onDismissing(bar: LiveSmashBar, isSwiped: Boolean) {

    }

    override fun onDismissProgress(bar: LiveSmashBar, progress: Float) {

    }

    override fun onDismissed(bar: LiveSmashBar, event: DismissEvent) {

    }

    override fun onShowing(bar: LiveSmashBar) {

    }

    override fun onShowProgress(bar: LiveSmashBar, progress: Float) {

    }

    override fun onShown(bar: LiveSmashBar) {

    }

    override fun onItemClick(position: Int) {
        when (position) {
            0 ->
                LiveSmashBar.Builder(this)
                        .title(getString(R.string.title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.description))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.BOTTOM)
                        .duration(3000)
                        .show();

            1 ->
                LiveSmashBar.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.description))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.BOTTOM)
                        .duration(3000)
                        .primaryActionText("DONE")
                        .primaryActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .primaryActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .show();

            2 ->
                LiveSmashBar.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.description))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.BOTTOM)
                        .backgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .setBarStyle(BarStyle.DIALOG)
                        .positiveActionText("DONE")
                        .positiveActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .positiveActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .negativeActionText("CLOSE")
                        .negativeActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .negativeActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .show();
            3 ->
                LiveSmashBar.Builder(this)
                        .showIcon()
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.flutter_title))
                        .titleColor(ContextCompat.getColor(this, R.color.slate_black))
                        .description(getString(R.string.flutter_info))
                        .descriptionColor(ContextCompat.getColor(this, R.color.slate_black))
                        .gravity(GravityView.BOTTOM)
                        .dismissOnTapOutside()
                        .showOverlay()
                        .overlayBlockable()
                        .backgroundColor(ContextCompat.getColor(this, R.color.white))
                        .show();

            4 -> {

                val liveData: MutableLiveData<Unit> = MutableLiveData()

                LiveSmashBar.Builder(this)
                        .showIcon()
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.flutter_title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.flutter_info))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.BOTTOM)
                        .duration(3000)
                        .liveDataCallback(this, liveData)

                Handler().postDelayed({
                    liveData.postValue(Unit)
                }, 2000)
            }

            5 ->
                LiveSmashBar.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.description))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.TOP)
                        .duration(3000)
                        .show()

            6 ->
                LiveSmashBar.Builder(this)
                        .showIcon()
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.flutter_title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.flutter_info))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.TOP)
                        .duration(3000)
                        .primaryActionText("DONE")
                        .primaryActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .primaryActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .show()

            7 ->
                LiveSmashBar.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.title))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .description(getString(R.string.description))
                        .descriptionColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.TOP)
                        .setBarStyle(BarStyle.DIALOG)
                        .positiveActionText("DONE")
                        .positiveActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .positiveActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .negativeActionText("CLOSE")
                        .negativeActionTextColor(ContextCompat.getColor(this, R.color.white))
                        .negativeActionEventListener(object : OnEventTapListener {
                            override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                            }
                        })
                        .show();

            8 ->
                LiveSmashBar.Builder(this)
                        .showIcon()
                        .icon(R.mipmap.ic_launcher)
                        .title(getString(R.string.flutter_title))
                        .titleColor(ContextCompat.getColor(this, R.color.slate_black))
                        .description(getString(R.string.flutter_info))
                        .descriptionColor(ContextCompat.getColor(this, R.color.slate_black))
                        .gravity(GravityView.TOP)
                        .dismissOnTapOutside()
                        .showOverlay()
                        .overlayBlockable()
                        .backgroundColor(ContextCompat.getColor(this, R.color.white))
                        .show();

            9 ->
                LiveSmashBar.Builder(this)
                        .icon(R.mipmap.ic_launcher)
                        .iconAnimation(AnimIconBuilder(this).pulse())
                        .title(getString(R.string.description))
                        .titleColor(ContextCompat.getColor(this, R.color.white))
                        .gravity(GravityView.TOP)
                        .duration(3000)
                        .show()
        }
    }
}
