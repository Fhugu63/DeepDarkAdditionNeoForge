luginManagement {
    repositories {
        maven { url = uri("https://neoforged.forgecdn.net") }
        gradlePluginPortal()
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven {
            url = uri("https://maven.kikugie.dev/snapshots")
            name = "KikuGie Snapshots"
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("dev.kikugie.stonecutter") version "0.8.3"
}

// Настройка Stonecutter для работы с Kotlin-скриптами
extensions.configure<dev.kikugie.stonecutter.StonecutterSettings> {
    // Теперь используем Kotlin DSL контроллер
    centralScript = "build.gradle.kts"

    create(rootProject) {
        versions("1.21.1", "1.21.2", "1.21.3", "1.21.4")
        vcsVersion = "1.21.4"
    }
}