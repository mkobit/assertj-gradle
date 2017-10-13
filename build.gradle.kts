import com.jfrog.bintray.gradle.BintrayExtension
import java.io.ByteArrayOutputStream
import org.gradle.api.internal.HasConvention
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.platform.console.options.Details
import org.junit.platform.gradle.plugin.JUnitPlatformExtension
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

buildscript {
  repositories {
    mavenCentral()
    jcenter()
  }
  dependencies {
    // TODO: load from properties or script plugin
    classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
  }
}

plugins {
  id("com.gradle.build-scan") version "1.9.1"
  `java-library`
  `maven-publish`
  kotlin("jvm")
  id("com.github.ben-manes.versions") version "0.15.0"
  id("com.jfrog.bintray") version "1.7.3"
}

version = "0.1.0"
group = "com.mkobit.gradle.test"
description = "AssertJ extensions for Gradle"

val projectUrl by extra { "https://github.com/mkobit/assertj-gradle" }
val issuesUrl by extra { "https://github.com/mkobit/assertj-gradle/issues" }
val scmUrl by extra { "https://github.com/mkobit/assertj-gradle.git" }

val gitCommitSha: String by lazy {
  ByteArrayOutputStream().use {
    project.exec {
      commandLine("git", "rev-parse", "HEAD")
      standardOutput = it
    }
    it.toString(Charsets.UTF_8.name()).trim()
  }
}

val SourceSet.kotlin: SourceDirectorySet
  get() = withConvention(KotlinSourceSet::class) { kotlin }

buildScan {
  fun env(key: String): String? = System.getenv(key)

  setLicenseAgree("yes")
  setLicenseAgreementUrl("https://gradle.com/terms-of-service")

  // Env variables from https://circleci.com/docs/2.0/env-vars/
  if (env("CI") != null) {
    logger.lifecycle("Running in CI environment, setting build scan attributes.")
    tag("CI")
    env("CIRCLE_BRANCH")?.let { tag(it) }
    env("CIRCLE_BUILD_NUM")?.let { value("Circle CI Build Number", it) }
    env("CIRCLE_BUILD_URL")?.let { link("Build URL", it) }
    env("CIRCLE_SHA1")?.let { value("Revision", it) }
    env("CIRCLE_COMPARE_URL")?.let { link("Diff", it) }
    env("CIRCLE_REPOSITORY_URL")?.let { value("Repository", it) }
    env("CIRCLE_PR_NUMBER")?.let { value("Pull Request Number", it) }
    link("Repository", "https://github.com/mkobit/jenkins-pipeline-shared-libraries-gradle-plugin")
  }
}

tasks {
  "wrapper"(Wrapper::class) {
    gradleVersion = "4.2.1"
    distributionType = Wrapper.DistributionType.ALL
  }
}

repositories {
  jcenter()
  mavenCentral()
}

apply {
  from("gradle/junit5.gradle.kts")
  plugin("org.junit.platform.gradle.plugin")
}

val kotlinVersion by project
val junitPlatformVersion: String by rootProject.extra
val junitTestImplementationArtifacts: Map<String, Map<String, String>> by rootProject.extra
val junitTestRuntimeOnlyArtifacts: Map<String, Map<String, String>> by rootProject.extra

dependencies {
  api(gradleApi())
  api(gradleTestKit())
  compileOnly("com.google.code.findbugs:jsr305:3.0.2")
  api("org.assertj", "assertj-core", "3.8.0")
  testImplementation(kotlin("stdlib-jre8", kotlinVersion as String))
  testImplementation(kotlin("reflect", kotlinVersion as String))
  testImplementation("org.mockito:mockito-core:2.10.0")
  testImplementation("com.nhaarman:mockito-kotlin:1.5.0")
  junitTestImplementationArtifacts.values.forEach {
    testImplementation(it)
  }
  junitTestRuntimeOnlyArtifacts.values.forEach {
    testRuntimeOnly(it)
  }
}

extensions.getByType(JUnitPlatformExtension::class.java).apply {
  platformVersion = junitPlatformVersion
  filters {
    engines {
      include("junit-jupiter")
    }
  }
  logManager = "org.apache.logging.log4j.jul.LogManager"
  details = Details.TREE
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

val main = java.sourceSets["main"]!!
// No Kotlin in main source set
main.kotlin.setSrcDirs(emptyList<Any>())

val sourcesJar by tasks.creating(Jar::class) {
  classifier = "sources"
  from(main.allSource)
  description = "Assembles a JAR of the source code"
  group = JavaBasePlugin.DOCUMENTATION_GROUP
}

val javadocJar by tasks.creating(Jar::class) {
  val javadoc by tasks.getting(Javadoc::class)
  dependsOn(javadoc)
  from(javadoc.destinationDir)
  classifier = "javadoc"
  description = "Assembles a JAR of the generated Javadoc"
  group = JavaBasePlugin.DOCUMENTATION_GROUP
}

tasks {
  withType(Jar::class.java) {
    manifest {
      attributes(mapOf(
        "Build-Revision" to gitCommitSha,
        "Implementation-Version" to project.version
        // TODO: include Gradle version?
      ))
    }
    from(project.projectDir) {
      include("LICENSE.txt")
      into("META-INF")
    }
  }

  withType(Javadoc::class.java) {
    options {
      header = project.name
      encoding = "UTF-8"
    }
  }

  withType(KotlinCompile::class.java) {
    kotlinOptions.jvmTarget = "1.8"
  }

  "assemble" {
    dependsOn(sourcesJar, javadocJar)
  }

  val gitDirtyCheck by creating {
    doFirst {
      val output = ByteArrayOutputStream().use {
        exec {
          commandLine("git", "status", "--porcelain")
          standardOutput = it
        }
        it.toString(Charsets.UTF_8.name()).trim()
      }
      if (output.isNotBlank()) {
        throw GradleException("Workspace is dirty:\n$output")
      }
    }
  }

  val gitTag by creating(Exec::class) {
    description = "Tags the local repository with version ${project.version}"
    group = PublishingPlugin.PUBLISH_TASK_GROUP
    commandLine("git", "tag", "-a", project.version, "-m", "Gradle created tag for ${project.version}")
  }

  val pushGitTag by creating(Exec::class) {
    description = "Pushes Git tag ${project.version} to origin"
    group = PublishingPlugin.PUBLISH_TASK_GROUP
    dependsOn(gitTag)
    commandLine("git", "push", "origin", "refs/tags/${project.version}")
  }

  val bintrayUpload by getting {
    dependsOn(gitDirtyCheck, gitTag)
  }

  "release" {
    group = PublishingPlugin.PUBLISH_TASK_GROUP
    description = "Publishes the library and pushes up a Git tag for the current commit"
    dependsOn(bintrayUpload, pushGitTag)
  }
}

val publicationName = "assertjGradle"
publishing {
  publications.invoke {
    publicationName(MavenPublication::class) {
      from(components["java"])
      artifact(sourcesJar)
      artifact(javadocJar)
      pom.withXml {
        asNode().apply {
          appendNode("description", project.description)
          appendNode("url", projectUrl)
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
    websiteUrl = projectUrl
    issueTrackerUrl = issuesUrl
    vcsUrl = scmUrl
  })
}

