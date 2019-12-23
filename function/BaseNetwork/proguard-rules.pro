##-------------------------------- 第三方库 ---------------

#----------------------------- 实体类 ----------------
-keep class com.tgcity.function.network.bean.**{*;}


#----------------------------- retrofit2 ------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn org.robovm.**
-keep class org.robovm.** { *; }