import buildsrc.DependencyInfo
import buildsrc.ProjectInfo
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm")
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
  compileOnly(DependencyInfo.checkerQual)
}

val main by sourceSets
// No Kotlin in main source set
// TODO: mkobit - gradle fails to execute java compilation when this is executed
//main.kotlin.setSrcDirs(emptyList<Any>())

tasks {
  withType<Jar>().configureEach {
    manifest {
      attributes(mapOf(
          "Automatic-Module-Name" to "com.mkobit.gradle.test.assertj"
      ))
    }
  }

  val sourcesJar by registering(Jar::class) {
    classifier = "sources"
    from(main.allSource)
    description = "Assembles a JAR of the source code"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
  }

  val javadocJar by registering(Jar::class) {
    dependsOn(javadoc)
    from(javadoc.get().destinationDir)
    classifier = "javadoc"
    description = "Assembles a JAR of the generated Javadoc"
    group = JavaBasePlugin.DOCUMENTATION_GROUP
  }

  assemble {
    dependsOn(sourcesJar, javadocJar)
  }
}

val publicationName = "assertjGradle"
publishing {
  publications {
    val sourcesJar by tasks.getting
    val javadocJar by tasks.getting
    register(publicationName, MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(javadocJar)
      pom {
        description.set(project.description)
        url.set(ProjectInfo.projectUrl)
        licenses {
          license {
            name.set("The MIT License")
            url.set("https://opensource.org/licenses/MIT")
            distribution.set("repo")
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
