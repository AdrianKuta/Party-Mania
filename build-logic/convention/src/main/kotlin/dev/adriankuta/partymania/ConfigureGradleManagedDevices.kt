package dev.adriankuta.partymania

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureGradleManagedDevices(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        testOptions {
            managedDevices {
                devices.register("pixel2api30", ManagedVirtualDevice::class.java) {
                    device = "Pixel 2"
                    apiLevel = 30
                    systemImageSource = "aosp"
                }

// Get rid of devices.register block and uncomment the following once we can update our AGP version.
//                localDevices.create("pixel2api30") {
//                    // Use device profiles you typically see in Android Studio.
//                    device = "Pixel 2"
//                    // Use only API levels 27 and higher.
//                    apiLevel = 30
//                    // To include Google services, use "google".
//                    systemImageSource = "aosp"
//                }
                groups.create("default") {
                    targetDevices.add(devices.getByName("pixel2api30"))
                }
            }
        }
    }

    dependencies {
        // Something has gone awry in 8.3.0-rc01.  I robbed the following from here:
        // https://sourceforge.net/projects/guava.mirror/files/v32.1.0/
        // If we are not on 8.3.0-rc01 anymore then remove this and run a test with gradle managed
        // device (ie ./gradlew pixel2api30DevDebugAndroidTest).  If you dont see a dependancy problem
        // with guava:listenablefuture then we are good to remove it.
        modules {
            module("com.google.guava:listenablefuture") {
                replacedBy("com.google.guava:guava", "listenablefuture is part of guava")
            }
        }
    }
}