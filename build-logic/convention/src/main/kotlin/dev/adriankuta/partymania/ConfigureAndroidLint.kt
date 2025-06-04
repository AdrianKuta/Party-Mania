package dev.adriankuta.partymania

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

internal fun Project.configureAndroidLint(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        lint {
            baseline = file("lint-baseline.xml")
            warningsAsErrors = true
            disable += "AndroidGradlePluginVersion"
            warning += "LintBaseline" // Still have report remind us of baseline issues
            disable += "GradleDependency" // We want to mange dependency updates in own PRs.
            disable += "MissingTranslation" // Translations are apart from our normal process at the moment

            // region - Bug in lint/AGP apparently
            // TODO Maybe it is, maybe it isnt.  Its a transitory, hard to replicate bug.  Ideally
            // we want these lint rules applied.
            disable += "StateFlowValueCalledInComposition"
            disable += "FlowOperatorInvokedInComposition"
            disable += "WrongNavigateRouteType"
            disable += "WrongStartDestinationType"
            disable += "UnknownIssueId"
            // endregion
        }
    }
}
