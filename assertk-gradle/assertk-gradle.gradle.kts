import org.gradle.jvm.tasks.Jar

plugins {
  `java-library`
  kotlin("jvm")
  id("com.jfrog.bintray")
}

description = "AssertK (https://github.com/willowtreeapps/assertk) extensions for Gradle"

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
  withType<Jar> {
    manifest {
      attributes(mapOf(
        "Automatic-Module-Name" to "com.mkobit.gradle.test.assertk"
      ))
    }
  }

  val sourcesJar by creating(Jar::class) {
    val main by java.sourceSets
    classifier = "sources"
    from(main.allSource)
    description = "Assembles a JAR of the source code"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
  }

  "assemble" {
    dependsOn(sourcesJar)
  }
}

dependencies {
  api("com.willowtreeapps.assertk", "assertk", "0.9")
}
