// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // ... other top-level plugins if needed
}

buildscript {
    // ... buildscript configurations if needed
}

allprojects {
    repositories {
        // ... other repositories if needed
    }
}