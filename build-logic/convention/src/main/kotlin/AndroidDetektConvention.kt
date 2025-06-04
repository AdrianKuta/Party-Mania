import dev.adriankuta.partymania.configureDetektForNonUiModule
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused") // This is called as a string in the gradle plugin block
internal class AndroidDetektConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureDetektForNonUiModule()
        }
    }
}

