package dev.adriankuta.partymania

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

@Suppress("EnumEntryName")
enum class FlavorDimension {
    cost
}

// The content for the app can either come from local static data which is useful for demo
// purposes, or from a production backend server which supplies up-to-date, real content.
// These two product flavors reflect this behaviour.
@Suppress("EnumEntryName")
enum class PartyManiaFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
) {
    free(FlavorDimension.cost),
    paid(FlavorDimension.cost, applicationIdSuffix = ".paid")
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.cost.name
        productFlavors {
            PartyManiaFlavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    if (this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}
