include(
  "assertj-gradle",
  "assertk-gradle",
  "strikt-gradle"
)

rootProject.name = "gradle-assertions"
rootProject.children.forEach { child ->
  child.buildFileName = "${child.name}.gradle.kts"
}
