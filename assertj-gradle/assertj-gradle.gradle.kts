import buildsrc.DependencyInfo
import buildsrc.ProjectInfo
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm")
  id("org.jetbrains.dokka")
  id("com.jfrog.bintray")
}
description = "AssertJ extensions for Gradle"

val SourceSet.kotlin: SourceDirectorySet
  get() = withConvention(KotlinSourceSet::class) { kotlin }

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
  api(DependencyInfo.assertJCore)
  // Should this be an API dependency?
  compileOnly("org.checkerframework", "checker-qual", "2.3.2")
}

val main by java.sourceSets
// No Kotlin in main source set
main.kotlin.setSrcDirs(emptyList<Any>())

tasks {
  withType<Jar> {
    manifest {
      attributes(mapOf(
          "Automatic-Module-Name" to "com.mkobit.gradle.test.assertj"
      ))
    }
  }

  val sourcesJar by creating(Jar::class) {
    classifier = "sources"
    from(main.allSource)
    description = "Assembles a JAR of the source code"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
  }

  val javadocJar by creating(Jar::class) {
    val javadoc by tasks.getting(Javadoc::class)
    dependsOn(javadoc)
    from(javadoc.destinationDir)
    classifier = "javadoc"
    description = "Assembles a JAR of the generated Javadoc"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
  }

  "assemble" {
    dependsOn(sourcesJar, javadocJar)
  }
}

val publicationName = "assertjGradle"
publishing {
  publications.invoke {
    val sourcesJar by tasks.getting
    val javadocJar by tasks.getting
    publicationName(MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(javadocJar)
      pom.withXml {
        asNode().apply {
          appendNode("description", project.description)
          appendNode("url", ProjectInfo.projectUrl)
          appendNode("licenses").apply {
            appendNode("license").apply {
              appendNode("name", "The MIT License")
              appendNode("url", "https://opensource.org/licenses/MIT")
              appendNode("distribution", "repo")
            }
          }
        }
      }
    }
  }
}

bintray {
  val bintrayUser = project.findProperty("bintrayUser") as String?
  val bintrayApiKey = project.findProperty("bintrayApiKey") as String?
  user = bintrayUser
  key = bintrayApiKey
  publish = true
  setPublications(publicationName)
  pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
    repo = "gradle"
    name = project.name
    userOrg = "mkobit"

    setLabels("gradle", "assertj", "assertion", "testkit")
    setLicenses("MIT")
    desc = project.description
    websiteUrl = ProjectInfo.projectUrl
    issueTrackerUrl = ProjectInfo.issuesUrl
    vcsUrl = ProjectInfo.scmUrl
  })
}
