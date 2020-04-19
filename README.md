# libgdx-tower-defense

A basic tower defense game built in LibGDX

## What is this?

This is just a basic tower defense game. It's not very different from any other
tower defense game out there.

## Why?

This was created for two reasons:  
1. To learn more about game development, game engines, and graphics.  
2. There seems a surprising lack of tower defense games on the F-Droid store.  
   
## Platforms

* GNU+Linux
* Android
* Windows

There's a potential for any of the other platforms LibGDX supports, but
Desktop and Android are the focus.

## Is it playable?

Not really, it's in the very early stages of infancy. It's riddled with bugs and lacking
a lot of very important features.

## How do I build/run it?

### Dependencies
* Java (1.8)
* Gradle
* LibGDX
* Android SDK

### Commands
**Linux** and **Windows**:  
*build:* `./gradlew desktop:run`  
*run:* `./gradlew desktop:run`  

**Android**:  
* First download the SDK [command line tools](https://developer.android.com/studio#downloads) and place them in `/home/<user>/Android/Sdk/tools`  
* Then set the ANDROID_HOME environment variable `export ANDROID_HOME=/home/<user>/Android/Sdk`  

**build:** `./gradlew android:assembleDebug`  
**run:** `./gradlew installDebug`  

#### Troubleshooting
**License error:** run `~/Android/Sdk/tools/bin/sdkmanager --sdk_root=${ANDROID_HOME} --licenses`
