# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Android/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html


-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService
-dontwarn com.google.ads.interactivemedia.v3.**

-dontwarn com.qsl.**
-keep class com.qsl.** {
    public protected private *;
}

-dontwarn org.codehaus.jackson.**
-keep class org.codehaus.jackson.** {
    public protected private *;
}

### END JACKSON

# START GSON

# For using GSON @Expose annotation
-keepattributes *Annotation*,EnclosingMethod,Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class net.amp.dvr.api.** { *; }
-keep class net.amp.dvr.messaging.** { *; }
# END GSON

# START JS
-keep public class android.webkit.JavascriptInterface {
    public protected private *;
}

-keepattributes JavascriptInterface

-keep public class com.vervewireless.advert.MRAIDJavascriptInterface
-keep public class * implements com.vervewireless.advert.MRAIDJavascriptInterface
-keepclassmembers class * implements com.vervewireless.advert.MRAIDJavascriptInterface {
    <methods>;
}

-keep public class com.vervewireless.advert.VRVSDKJavascriptInterface
-keep public class * implements com.vervewireless.advert.VRVSDKJavascriptInterface
-keepclassmembers class * implements com.vervewireless.advert.VRVSDKJavascriptInterface {
    <methods>;
}
# END JS

-keep class com.vervewireless.** {
    public protected private *;
}

-dontwarn com.moat.**
-keep class com.moat.** {
    public protected private *;
}

-keep class com.altbeacon.** {
    public protected private *;
}

-keep class com.gimbal.** {
    public protected private *;
}

# START MoPub

# Keep public classes and methods.
-keepclassmembers class com.mopub.** { public *; }
-keep public class com.mopub.**

# Explicitly keep any custom event classes in any package.
-keep class * extends com.mopub.mobileads.CustomEventBanner {}
-keep class * extends com.mopub.mobileads.CustomEventInterstitial {}
-keep class * extends com.mopub.nativeads.CustomEventNative {}

# Support for Android Advertiser ID.
-keep class com.google.android.gms.common.GooglePlayServicesUtil {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}

# Support for Google Play Services
# http://developer.android.com/google/play-services/setup.html
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
# END MoPub