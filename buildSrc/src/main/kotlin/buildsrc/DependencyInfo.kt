package buildsrc

object DependencyInfo {
  val junitPlatformVersion: String = "1.1.0"
  val junitJupiterVersion: String = "5.1.0"
  val junit5Log4jVersion: String = "2.10.0"


  val junitPlatformRunner = mapOf("group" to "org.junit.platform", "name" to "junit-platform-runner", "version" to junitPlatformVersion)
  val junitJupiterApi = mapOf("group" to "org.junit.jupiter", "name" to "junit-jupiter-api", "version" to junitJupiterVersion)
  val junitJupiterParams = mapOf("group" to "org.junit.jupiter", "name" to "junit-jupiter-params", "version" to junitJupiterVersion)

  val junitTestImplementationArtifacts = listOf(
      junitPlatformRunner,
      junitJupiterApi,
      junitJupiterParams
  )

  val assertJCore = "org.assertj:assertj-core:3.9.1"
  val mockitoCore = "org.mockito:mockito-core:2.15.0"
  val mockitoKotlin = "com.nhaarman:mockito-kotlin:1.5.0"
  val junitJupiterEngine = mapOf("group" to "org.junit.jupiter", "name" to "junit-jupiter-engine", "version" to junitJupiterVersion)
  val log4jCore = mapOf("group" to "org.apache.logging.log4j", "name" to "log4j-core", "version" to junit5Log4jVersion)
  val log4jJul = mapOf("group" to "org.apache.logging.log4j", "name" to "log4j-jul", "version" to junit5Log4jVersion)

  val junitTestRuntimeOnlyArtifacts = listOf(
      junitJupiterEngine,
      log4jCore,
      log4jJul
  )
}
