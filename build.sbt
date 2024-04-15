ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"
libraryDependencies ++= Seq(
	"org.scala-lang.modules" %% "scala-swing" % "3.0.0",
	"org.scalatest" %% "scalatest" % "3.2.17" % Test
)
lazy val root = (project in file("."))
  .settings(
    name := "mowitnow"
  )
