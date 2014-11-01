name := "doubletetris"

scalaVersion := "2.10.4"

version := "1.0"

EclipseKeys.skipProject := true    // don't generate eclipse project files for the parent project

lazy val root = project.in(file(".")).aggregate()

lazy val doubletetrisJS = project.in(file("doubletetris-js"))
  .settings(scalaJSSettings: _*)
  .settings(
    name := "doubletetris-js",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala"
)

lazy val doubletetrisJVM = project.in(file("doubletetris-jvm"))
  .settings(
    name := "doubletetris-jvm",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala",
    libraryDependencies += "org.scala-lang" % "scala-swing" % "2.10.4"
)
