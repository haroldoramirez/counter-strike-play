rootProject.name = "counter-strike-play"

// TODO: Remove after release Play Gradle Plugin
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        mavenLocal()
    }
}
