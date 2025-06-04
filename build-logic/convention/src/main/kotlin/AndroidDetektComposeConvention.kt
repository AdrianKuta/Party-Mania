import dev.adriankuta.partymania.configureDetektForComposeModuleExceptions
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused") // This is called as a string in the gradle plugin block
internal class AndroidDetektComposeConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureDetektForComposeModuleExceptions()
        }
    }
}