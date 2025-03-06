pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Superfast Delivery"
include(":app")
include(":common")
include(":feature:auth:ui")
include(":feature:search:ui")
include(":feature:auth:data")
include(":feature:auth:domain")
include(":schema")
include(":feature:account:ui")
include(":feature:account:data")
include(":feature:account:domain")
include(":feature:search:domain")
include(":feature:search:data")
