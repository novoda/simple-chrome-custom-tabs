# simple-chrome-custom-tabs [![](https://ci.novoda.com/buildStatus/icon?job=simple-chrome-custom-tabs)](https://ci.novoda.com/job/simple-chrome-custom-tabs/lastBuild/console) [![Download](https://api.bintray.com/packages/novoda/maven/simple-chrome-custom-tabs/images/download.svg) ](https://bintray.com/novoda/maven/simple-chrome-custom-tabs/_latestVersion) [![](https://raw.githubusercontent.com/novoda/novoda/master/assets/btn_apache_lisence.png)](LICENCE.txt)


- Navigating to external websites from your app? 
- *"Yes, that one is easy"*
- Wondering how to keep the user in context?
- *"I know... it can be done using WebView, but I don't like it that much"*
- Aha! Why don't you try Chrome Custom Tabs? 
- *"Integrating it into my app can be a pain..."*
- Not anymore!

**simple-chrome-custom-tabs** will help you with that.

## Description

**simple-chrome-custom-tabs** provides easy integration of Chrome Custom Tabs into your project.
Just connect it to your activity, and navigate to the external website styling your tab as you wish.
Moreover with Custom Tabs the navigation can be nearly instantaneous!

More information about Chrome Custom Tabs available at: https://developer.chrome.com/multidevice/android/customtabs

## Adding to your project

To start using this library, add these lines to the `build.gradle` of your project:

```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/novoda/maven" 
    }
}

dependencies {
    compile 'com.novoda:simple-chrome-custom-tabs:0.1.5'
}
```    


## Simple usage

1) Initialize `simple-chrome-custom-tabs`, you only have to this once.

```java
SimpleChromeCustomTabs.initialize(context);
```

2) Connect `simple-chrome-custom-tabs` to your `Activity` as soon as it is resumed.

```java
@Override
public void onResume() {
    super.onResume();
    SimpleChromeCustomTabs.getInstance().connectTo(this);
}
```

And don't forget to disconnect when the `Activity` is paused.

```java
@Override
public void onPause() {
    SimpleChromeCustomTabs.getInstance().disconnectFrom(this);
    super.onPause();
}
```

3) Navigate!

```java
Uri url = ANY_WEBSITE_URL;
NavigationFallback fallback = ANY_FALLBACK_MECHANISM; //In case something goes wrong.
IntentCustomizer customizer = ANY_INTENT_CUSTOMIZER; //To theme your tab.

SimpleChromeCustomTabs.getInstance().withFallback(fallback)
    .withIntentCustomizer(customizer)
    .navigateTo(url, activity);
```


**And voilà!!**

External Browser | simple-chrome-custom-tabs
--- | ---
![external_browser](https://github.com/novoda/simple-chrome-custom-tabs/blob/master/art/external_browser.gif) | ![simplechromecustomtabs](https://github.com/novoda/simple-chrome-custom-tabs/blob/master/art/simplechromecustomtabs.gif)

**Note**: 
Check the [extended demo](https://github.com/novoda/simple-chrome-custom-tabs/tree/master/demo-extended) for further usage examples.

## Links

Here are a list of useful links:

 * We always welcome people to contribute new features or bug fixes, [here is how](https://github.com/novoda/novoda/blob/master/CONTRIBUTING.md)
 * If you have a problem or you feel that important functionality is missing check the [Issues Page](https://github.com/novoda/simple-chrome-custom-tabs/issues) first to see if we are working on it
