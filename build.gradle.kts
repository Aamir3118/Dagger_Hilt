import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins{
    id("com.google.dagger.hilt.android") version "2.47" apply false
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = URI("https://jitpack.io") }
    }
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs +=
            "-Xuse-experimental=" +
                    "kotlin.Experimental," +
                    "kotlinx.coroutines.ExperimentalCoroutinesApi," +
                    "kotlinx.coroutines.InternalCoroutinesApi," +
                    "kotlinx.coroutines.FlowPreview"
    }
}