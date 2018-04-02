package buildsrc

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object ProjectInfo {
  const val projectUrl = "https://github.com/mkobit/gradle-assertions"
  const val issuesUrl = "$projectUrl/issues"
  const val scmUrl = "$issuesUrl.git"

  // TODO: this should probably be a part of some buildSrc plugin that automatically configures the correct things
//  val automaticModuleName = "com.mkobit.gradle.test.assertj"
}
