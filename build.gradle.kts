// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    dependencies{
        //Navigation
        classpath(libs.navigation.safeArgs.gradlePlugin)
        //Hilt
        classpath(libs.hilt.android.gradle.plugin)
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    //Navigation
    alias(libs.plugins.navigationSafeArgs) apply false
    //Hilt
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.kotlinAndroidKsp) apply false
}
