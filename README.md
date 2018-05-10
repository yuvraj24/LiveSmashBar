# LiveSmashBar

[![Download](https://api.bintray.com/packages/yuvraj24/maven/smashbar/images/download.svg)](https://bintray.com/yuvraj24/maven/smashbar/_latestVersion) [![API](https://img.shields.io/badge/API-16%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=16) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

LiveSmashBar allows you a great alternative for native snackbar & toast in Android. It allows a great extent of customization & flexibility in terms of usage & behaviour.

Also its has support for **LiveData** which can be beneficial for displaying repetative messages just be single initialization.

Library has been designed & developed purely in *Kotlin*. ❤️

![Alt text](https://github.com/yuvraj24/LiveSmashBar/blob/master/images/LiveSmashBar.png)

### Spread Your ❤️:
[![GitHub followers](https://img.shields.io/github/followers/yuvraj24.svg?style=social&label=Follow)](https://github.com/yuvraj24)  [![Twitter Follow](https://img.shields.io/twitter/follow/yuvrajpandey24.svg?style=social)](https://twitter.com/yuvrajpandey24)

# Samples
You can check the <a href="https://github.com/yuvraj24/LiveSmashBar/blob/master/app/src/main/java/com/yuvraj/livesmashbardemo/SampleActivity.kt">Demo Project</a> developed in kotlin for better understanding of concepts & usage.

# Download

This library is available in maven & jcenter which can also be imported from source as a module.

### maven

```Maven
<dependency>
    <groupId>com.yuvraj.livesmashbar</groupId>
    <artifactId>smashbar</artifactId>
    <version>1.0.0</version>
    <type>pom</type>
</dependency>
```

### gradle
 
```JCenter
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

## Event Listeners
You can subscribe to events like when the LiveSmashBar is showing, or dismissing. You can also subscribe to progress updates when the LiveSmashBar is being shown or dismissed to perform animations on other views if needed.

You can also subscribe to tap events inside or outside the bar.

### Show Event

You can subscribe to events on `OnEventShowListener` as follows,

```kotlin
LiveSmashBar.Builder(this) 
        .title("Hello World!")
        .description("You can listen to events when the LiveSmashBar is shown")
        .barShowListener(object : LiveSmashBar.OnEventShowListener {
            override fun onShowing(bar: LiveSmashBar) {
                Log.d(TAG, "LiveSmashBar is showing")
            }

            override fun onShown(bar: LiveSmashBar) {
                Log.d(TAG, "LiveSmashBar is shown")
            }
        })
        .show()
```
### Dismiss
You can listen to events on `OnEventDismissListener` for dismissing events. 
You can also specifically get to know the reason behind the bar dismiss action - `TIMEOUT`, `MANUAL`, `TAP_OUTSIDE` and `SWIPE`.

```kotlin
LiveSmashBar.Builder(this) 
        .title("Hello World!")
        .duration(500)
        .description("You can listen to events when the LiveSmashBar is dismissed")
        .barDismissListener(object : LiveSmashBar.OnEventDismissListener {
            override fun onDismissing(bar: LiveSmashBar, isSwiped: Boolean) {
                Log.d(TAG, "LiveSmashBar is dismissing with $isSwiped")
            }

            override fun onDismissed(bar: LiveSmashBar, event: LiveSmashBar.DismissEvent) {
                Log.d(TAG, "LiveSmashBar is dismissed with event $event")
            }
        }) 
        .show()
```

### Taps

You can listen to tap events inside or outside of the LiveSmashBar.

```kotlin
LiveSmashBar.Builder(this) 
        .title("Hello World!")
        .description("You can listen to tap events inside or outside the LiveSmashBar.")
        .listenBarTaps(object : LiveSmashBar.OnEventListener {
            override fun onTap(bar: LiveSmashBar) {
                Log.d(TAG, "Bar tapped")
            }
        })
        .listenOutsideTaps(object : LiveSmashBar.OnTapListener {
            override fun onTap(bar: LiveSmashBar) {
                Log.d(TAG, "Outside tapped")
            }
        })
        .show()
```

# About Me

### Yuvraj Pandey

for more tech updates follow me,

<a href="https://twitter.com/yuvrajpandey24" target="_blank"><img src="https://github.com/yuvraj24/LiveSmashBar/blob/master/images/twitter.png" width="40" height="40"></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://www.linkedin.com/in/yuvraj24" target="_blank"><img src="https://github.com/yuvraj24/LiveSmashBar/blob/master/images/linkedin.png" width="40" height="40"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://github.com/yuvraj24" target="_blank"><img src="https://github.com/yuvraj24/LiveSmashBar/blob/master/images/github.png" height="40"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://medium.com/@yuvrajpandey24" target="_blank"><img src="https://github.com/yuvraj24/LiveSmashBar/blob/master/images/medium.png" width="40" height="40"></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="https://play.google.com/store/apps/developer?id=Yuvraj+Pandey"><img src="https://github.com/yuvraj24/LiveSmashBar/blob/master/images/playstore.png" width="40" height="40"></a>
 
# License

```Kotlin
Copyright 2018 Yuvraj Pandey 

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at, 

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, 
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
