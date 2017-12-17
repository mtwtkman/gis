import Dependencies._

lazy val circeVersion = "0.8.0"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "mtwtkman",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Mj",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )
  )
