plugins {
    java

    id("fabric-loom") version "0.12.+"
    id("io.github.juuxel.loom-quiltflower") version "1.7.+"
}

group = "cc.polyfrost"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.polyfrost.cc")
    maven("https://maven.isxander.dev/releases")
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases")
    maven("https://jitpack.io")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
    minecraft(libs.minecraft)
    mappings("net.fabricmc:yarn:${libs.versions.minecraft.get()}+build.+:v2")

    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.mod.menu)
    modImplementation(libs.yacl)

    libs.mixin.extras.let {
        implementation(it)
        annotationProcessor(it)
        include(it)
    }

    modRuntimeOnly(libs.devauth)
}


tasks {
    withType<JavaCompile> {
        options.release.set(17)
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(
                "version" to project.version
            )
        }
    }
}
