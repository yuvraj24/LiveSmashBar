# LiveSmashBar
A highly customizable, elegant and easy-to-use informative library for Android.

[![Download](https://api.bintray.com/packages/yuvraj24/maven/smashbar/images/download.svg)](https://bintray.com/yuvraj24/maven/smashbar/_latestVersion) [![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

LiveSmashBar allows you a great alternative for native snackbar & toast in Android. It allows a great extent of customization & flexibility in terms of usage & behaviour.

Also its has support for **LiveData** which can be beneficial for displaying repetative messages just be single initialization.

Library has been designed & developed purely in *Kotlin*. ❤️

### Spread Your ❤️:
[![GitHub followers](https://img.shields.io/github/followers/yuvraj24.svg?style=social&label=Follow)](https://github.com/yuvraj24)  [![Twitter Follow](https://img.shields.io/twitter/follow/yuvrajpandey24.svg?style=social)](https://twitter.com/yuvrajpandey24)

# Samples
You can check the <a href="https://github.com/yuvraj24/LiveSmashBar/blob/master/app/src/main/java/com/yuvraj/livesmashbardemo/SampleActivity.kt">Demo Project</a> developed in kotlin for better understanding of concepts & usage.

# Download

This library is available in **JCenter** which can be imported from source as a module.
 
```groovy
dependencies {
    // other dependencies here
    implementation 'com.yuvraj.livesmashbar:smashbar:1.0.0'
}
```
# Get Started

### Basic



![Alt text](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/simple_livesmashbar.png)



Shows a simple LiveSmashBar with description & duration.

```Kotlin
LiveSmashBar.Builder(this)
            .description(getString(R.string.description))
            .descriptionColor(ContextCompat.getColor(this, R.color.white))
            .gravity(GravityView.BOTTOM)
            .duration(3000)
            .show();
```

Also you can show both Title & Description along with duration in milliseconds. Duration is set to indefinite which means it won't dismiss until & unless specified for.Also you can use predefined parameters i.e DURATION_SHORT = 1000, DURATION_LONG = 2500 for specifying duration for LiveSmashBar.

```Kotlin
LiveSmashBar.Builder(this)
            .title(getString(R.string.title))
            .titleColor(ContextCompat.getColor(this, R.color.white))
            .description(getString(R.string.description))
            .descriptionColor(ContextCompat.getColor(this, R.color.white))
            .gravity(GravityView.BOTTOM)
            .duration(DURATION_SHORT)
            .show();
```


## Gravity


![Gravity_top](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/gravity_view_top.png)


You can show LiveSmashBar at both Bottom ae well as Top of the screen by specifying GravityView.BOTTOM / GravityView.TOP.

### GravityView.BOTTOM :
```Kotlin
LiveSmashBar.Builder(this)
            .title(getString(R.string.title))
            .description(getString(R.string.description))
            .gravity(GravityView.BOTTOM)
            .duration(DURATION_SHORT)
            .show();
```

### GravityView.TOP :
```Kotlin
LiveSmashBar.Builder(this)
            .title(getString(R.string.title))
            .description(getString(R.string.description))
            .gravity(GravityView.TOP)
            .duration(DURATION_SHORT)
            .show();
```


## LiveData Support


![LiveData](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/livedata.png)


LiveSmashBar allows support for LiveData , which can be used for showing similar group of messages with single initialization thereby avoiding code redundancy. This can be achieved by simply creating a LiveSmashBar object & passing the livedata object as parameter. So when ever you post anything to livedata, your LiveSmashBar will receive that call back and will display the same to the end user. Following is a sample demostrating the use of livedata,

```Kotlin
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
```


## Icon


![Gravity_top](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/icon.png)


You can add icons to make details displayed on LiveSmashBar more meaningful & intuitive.

```Kotlin
LiveSmashBar.Builder(this)
            .icon(R.mipmap.ic_launcher)
            .title(getString(R.string.title))
            .titleColor(ContextCompat.getColor(this, R.color.white))
            .description(getString(R.string.description))
            .descriptionColor(ContextCompat.getColor(this, R.color.white))
            .gravity(GravityView.BOTTOM)
            .duration(DURATION_SHORT)
            .show();
```

Also you can apply animation to icons, with the following snippet,

```Kotlin
LiveSmashBar.Builder(this)
            .icon(R.mipmap.ic_launcher)
            .iconAnimation(AnimIconBuilder(this).pulse())
            .title(getString(R.string.description))
            .titleColor(ContextCompat.getColor(this, R.color.white))
            .gravity(GravityView.TOP)
            .duration(3000)
            .show()
```


## Primary Button


![Primary_Action](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/primary_action.png)


Similar to Snackbar, a message can be accompanied by an action button which can be used to perform some functionality. Below example listens for action button clikc to dismiss LiveSmashBar displayed to user.

```Kotlin
LiveSmashBar.Builder(this)
            .icon(R.mipmap.ic_launcher)
            .title(getString(R.string.title)) 
            .description(getString(R.string.description)) 
            .primaryActionText("DONE") 
            .primaryActionEventListener(object : OnEventTapListener {
                      override fun onActionTapped(bar: LiveSmashBar) {
                                bar.dismiss()
                       }
             })
            .show();
```


## Dialog Style


![Dialog_Style](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/dialog_style.png)


LiveSmashBar can also be used to display a dialog view with a message & action buttons. By defalut the view type set is BarStyle.DEFAULT_MESSAGE which shows basic message with action button. For displaying dialog style LiveSmashBar use the following snippet,

```Kotlin
LiveSmashBar.Builder(this)
            .icon(R.mipmap.ic_launcher)
            .title(getString(R.string.title)) 
            .description(getString(R.string.description)) 
            .backgroundColor(ContextCompat.getColor(this, R.color.orange))
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
```

## Overlay


![Dialog_Style](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/overlay.png)


LiveSmashBar allows to show modal overlay messages that dims the background & highlights the message to user. It blocks the UI if function overlayBlockable() is called thereby blocking user taps on the underlying content. You can dismiss the overlay by calling function dismissOnTapOutside() for dismissing the overlay.

```Kotlin
LiveSmashBar.Builder(this)
            .showIcon()
            .icon(R.mipmap.ic_launcher)
            .title(getString(R.string.flutter_title))
            .description(getString(R.string.flutter_info))
            .gravity(GravityView.TOP)
            .dismissOnTapOutside()
            .showOverlay()
            .overlayBlockable()
            .backgroundColor(ContextCompat.getColor(this, R.color.white))
            .show();            
```
