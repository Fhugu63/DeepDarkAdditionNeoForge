import org.gradle.api.tasks.wrapper.Wrapper

plugins {
    `java-library`
    `maven-publish`
    id("net.neoforged.gradle.userdev") version "7.1.20"
    idea
}

// Извлекаем переменные из gradle.properties
val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val minecraft_version_range: String by project
val loader_version_range: String by project
val mod_name: String by project
val mod_license: String by project

val neo_version = when {
    sc.current.parsed.matches('=1.21.1') ? "[21.1.1,21.1.219]" :
    sc.current.parsed.matches("=1.21.2") ? "[21.2.0-beta,21.2.1-beta]" :
    sc.current.parsed.matches
}

version = mod_version
base.archivesName.set(mod_id)

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.BIN
}

repositories {
    mavenCentral()
    maven {
        name = "GeckoLib"
        url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
        content {
            includeGroup("software.bernie.geckolib")
        }
    }
}

// Конфигурация NeoForge
runs {
    configureEach {
        systemProperty("forge.logging.markers", "REGISTRIES")
        systemProperty("forge.logging.console.level", "debug")
        modSource(sourceSets.main.get())
    }

    create("client") {
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
    }

    create("server") {
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
        programArgument("--nogui")
    }

    create("gameTestServer") {
        systemProperty("neoforge.enabledGameTestNamespaces", mod_id)
    }

    create("clientData") {
        programArguments.addAll(
            "--mod", mod_id,
            "--all",
            "--output", file("src/generated/resources/").absolutePath,
            "--existing", file("src/main/resources/").absolutePath
        )
    }
}

sourceSets.main {
    resources { srcDir("src/generated/resources") }
}

val localRuntime: Configuration by configurations.creating
configurations.runtimeClasspath.get().extendsFrom(localRuntime)

dependencies {
    implementation("net.neoforged:neoforge:$neo_version")



    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

tasks.withType<ProcessResources>().configureEach {
    val replaceProperties = mapOf(
        "minecraft_version" to minecraft_version,
        "minecraft_version_range" to minecraft_version_range,
        "neo_version" to neo_version,
        "loader_version_range" to loader_version_range,
        "mod_id" to mod_id,
        "mod_name" to mod_name,
        "mod_license" to mod_license,
        "mod_version" to mod_version
    )
    inputs.properties(replaceProperties)

    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/repo")
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}