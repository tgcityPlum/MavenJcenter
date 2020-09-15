##-------------------------------- 第三方库 ---------------

#----------------------------- butterknife ----------------
# PS 针对butterknife7.0及以上的
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#----------------------------- glide ----------------------
-keep class com.bumptech.glide.Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#----------------------------- ImmersionBar -----------
 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**

#----------------------------- EventBus ----------------
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
# -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#     <init>(java.lang.Throwable);
# }