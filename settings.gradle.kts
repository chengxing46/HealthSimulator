pluginManagement {
    repositories {
        maven { url = uri("https://mirrors.huaweicloud.com/repository/maven") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri("https://mirrors.huaweicloud.com/repository/maven") }
        google()
        mavenCentral()
    }
}

rootProject.name = "HealthSimulator"
include(":app")