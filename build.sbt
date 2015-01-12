name := "doubletetris"

scalaVersion := "2.10.4"

version := "1.0"

EclipseKeys.skipProject := true    // don't generate eclipse project files for the parent project

lazy val root = project.in(file(".")).aggregate()

lazy val `doubletetris-js` = project.in(file("doubletetris-js"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "doubletetris-js",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala"
)

lazy val `doubletetris-jvm` = project.in(file("doubletetris-jvm"))
  .settings(
    name := "doubletetris-jvm",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala",
    libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.4"
)
