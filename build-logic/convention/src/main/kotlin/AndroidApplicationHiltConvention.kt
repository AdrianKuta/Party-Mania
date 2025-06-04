import com.android.build.api.dsl.ApplicationExtension
import dev.adriankuta.partymania.configureHilt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused") // This is called as a string in the gradle plugin block
internal class AndroidApplicationHiltConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("dagger.hilt.android.plugin")
            }

            // android block
            extensions.configure<ApplicationExtension> {
                configureHilt()
            }
        }
    }
}
