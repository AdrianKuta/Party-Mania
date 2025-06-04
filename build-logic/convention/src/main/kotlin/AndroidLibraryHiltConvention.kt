import com.android.build.gradle.LibraryExtension
import dev.adriankuta.partymania.configureHilt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused") // This is called as a string in the gradle plugin block
internal class AndroidLibraryHiltConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            // android block
            extensions.configure<LibraryExtension> {
                configureHilt()
            }
        }
    }
}
