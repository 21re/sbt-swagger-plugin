package de.twentyone.sbt

import io.github.swagger2markup.Swagger2MarkupConverter
import sbt.Keys._
import sbt._

object SwaggerPlugin extends AutoPlugin {
  object autoImport {
    val generateSwaggerApiDoc = TaskKey[Unit]("generate-swagger-api-doc")

    val swaggerApiDocTarget = SettingKey[File]("swagger-api-doc-target")

    val swaggerApiFile = SettingKey[File]("swagger-api-file")
  }
  import autoImport._

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    swaggerApiFile := baseDirectory.value / "specs" / "api.yaml",
    swaggerApiDocTarget := baseDirectory.value / "specs" / "api",
    generateSwaggerApiDoc := {
      Swagger2MarkupConverter
        .from(swaggerApiFile.value.toPath)
        .withConfig(SafeSwagger2MarkupConfig)
        .build()
        .toFile(swaggerApiDocTarget.value.toPath)
    }
  )
}
