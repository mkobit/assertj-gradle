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

include("assertj-gradle")
include("assertk-gradle")

rootProject.name = "gradle-assertions"
rootProject.children.forEach { child ->
  child.buildFileName = "${child.name}.gradle.kts"
}
