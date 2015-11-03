name := "doubletetris"

scalaVersion := "2.11.5"

EclipseKeys.skipProject := true    // don't generate eclipse project files for the parent project

lazy val root = project.in(file(".")).aggregate()

lazy val `doubletetris-js` = project.in(file("doubletetris-js"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "doubletetris-js",
    scalaVersion := "2.11.5",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala",
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.8.0"
)

lazy val `doubletetris-jvm` = project.in(file("doubletetris-jvm"))
  .settings(
    name := "doubletetris-jvm",
    scalaVersion := "2.11.5",
    unmanagedSourceDirectories in Compile +=
      (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala",
    libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.11" % "1.0.1"

)
