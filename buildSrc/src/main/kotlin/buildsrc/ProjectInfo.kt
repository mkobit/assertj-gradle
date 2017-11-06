package buildsrc

object ProjectInfo {
  val projectUrl = "https://github.com/mkobit/assertj-gradle"
  val issuesUrl = "https://github.com/mkobit/assertj-gradle/issues"
  val scmUrl = "https://github.com/mkobit/assertj-gradle.git"

  // TODO: this should probably be a part of some buildSrc plugin that automatically configures the correct things
  val automaticModuleName = "com.mkobit.gradle.test.assertj"
}
