plugins {
    id("java")
}

group = "com.Kingdoms"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}