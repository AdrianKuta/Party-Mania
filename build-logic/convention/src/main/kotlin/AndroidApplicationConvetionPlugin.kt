import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import dev.adriankuta.partymania.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("dagger.hilt.android.plugin")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(commonExtension = this)
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("hilt.android").get())
                add("ksp", libs.findLibrary("hilt.compiler").get())
//              add("androidTestImplementation", libs.findLibrary("hilt.android.testing").get())
//              add("kaptAndroidTest", libs.findLibrary("hilt.android.compiler").get())
            }
        }
    }
}