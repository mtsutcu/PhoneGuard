// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
}
buildscript{
    dependencies{
        classpath( Dependencies.hiltAgp)
        classpath("com.google.gms:google-services:4.4.0")


    }
    repositories {
        google()
        mavenCentral()
    }
}

