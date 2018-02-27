import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    jcenter()
  }
}

// TODO: move assertj-gradle to subproject
include("atrium-gradle")
include("assertj-gradle")
include("assertk-gradle")

rootProject.name = "assert-gradle"
rootProject.children.forEach { child ->
  child.buildFileName = "${child.name}.gradle.kts"
}
