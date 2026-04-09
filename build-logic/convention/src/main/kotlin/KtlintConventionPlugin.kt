import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtlintConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jlleitschuh.gradle.ktlint")

            extensions.configure(KtlintExtension::class.java) {
                version.set("1.8.0")
                outputToConsole.set(true)
                ignoreFailures.set(true)
                filter {
                    exclude("**/generated/**")
                    exclude("**/build/**")
                    exclude("**/*.g.kt")
                    exclude("**/schemas/**")
                }
                reporters {
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML)
                }
            }
        }
    }
}
