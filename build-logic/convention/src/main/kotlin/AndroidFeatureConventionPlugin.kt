import dev.adriankuta.partymania.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("partymania.android.library")
                apply("partymania.android.hilt")
            }

            dependencies {
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())

                add("testImplementation", libs.findLibrary("androidx.navigation.testing").get())
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx.lifecycle.runtimeTesting").get()
                )
            }
        }

    }
}
