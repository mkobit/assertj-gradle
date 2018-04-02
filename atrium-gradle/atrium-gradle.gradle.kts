import buildsrc.DependencyInfo

plugins {
  `java-library`
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

dependencies {
  api(DependencyInfo.atriumUk)
}
