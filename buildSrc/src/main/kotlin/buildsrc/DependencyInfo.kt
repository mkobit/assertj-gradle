package buildsrc

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object DependencyInfo {
  const val junitPlatformVersion: String = "1.1.0"
  const val junitJupiterVersion: String = "5.1.0"
  const val junit5Log4jVersion: String = "2.11.0"

  const val assertJCore = "org.assertj:assertj-core:3.9.1"
  const val mockitoCore = "org.mockito:mockito-core:2.17.0"
  const val mockitoKotlin = "com.nhaarman:mockito-kotlin:1.5.0"
  const val assertk = "com.willowtreeapps.assertk:assertk:0.10"
  const val atriumVersion = "0.6.0"
  const val atriumUk = "ch.tutteli:atrium-cc-en_UK-robstoll:$atriumVersion"
  const val checkerQual = "org.checkerframework:checker-qual:2.5.0"

  val junitPlatformRunner = junitPlatform("runner")
  val junitJupiterApi = junitJupiter("api")
  val junitJupiterEngine = junitJupiter("engine")
  val junitJupiterParams = junitJupiter("params")
  val log4jCore = log4j("core")
  val log4jJul = log4j("jul")

  val junitTestImplementationArtifacts = listOf(
      junitPlatformRunner,
      junitJupiterApi,
      junitJupiterParams
  )

  val junitTestRuntimeOnlyArtifacts = listOf(
      junitJupiterEngine,
      log4jCore,
      log4jJul
  )

  fun junitJupiter(module: String) = "org.junit.jupiter:junit-jupiter-$module:$junitJupiterVersion"
  fun junitPlatform(module: String) = "org.junit.platform:junit-platform-$module:$junitPlatformVersion"
  fun log4j(module: String) = "org.apache.logging.log4j:log4j-$module:$junit5Log4jVersion"
}
