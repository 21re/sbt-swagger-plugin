# sbt-swagger-plugin

A simple wrapper to run [Swagger2Markup](https://github.com/Swagger2Markup/swagger2markup) inside sbt.

## Usage

In plugin.sbt

```
resolvers += Resolver.url("21re-bintray-plugins", url("http://dl.bintray.com/21re/public"))(Resolver.ivyStylePatterns)

addSbtPlugin("de.21re" % "sbt-swagger-plugin" % "0.1-4")
```

Simple adds a `generateSwaggerApiDoc` task.

Folling settings are supported:
* `swaggerApiFile` the swagger yaml file to be converted (defaults to: `specs/api.yaml`)
* `swaggerApiDocTarget` the target output (defaults to: `specs/api.md`)
