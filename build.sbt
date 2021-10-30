name := "public-transport-routing"

version := "0.1"

scalaVersion := "2.13.6"

val scalatestVersion = "3.2.10"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % scalatestVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % "test"
)
