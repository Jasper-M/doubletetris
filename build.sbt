name := "doubletetris"

scalaVersion := "2.10.4"

version := "1.0"

lazy val root = project.in(file(".")).aggregate()

lazy val doubletetrisShared = project.in(file("doubletetris-shared")).settings(scalaJSSettings: _*).settings(
  name := "doubletetris-shared"
)

lazy val doubletetrisJS = project.in(file("doubletetris-js")).settings(scalaJSSettings: _*).settings(
  name := "doubletetris-js",
  unmanagedSourceDirectories in Compile +=
    (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala"
)

lazy val doubletetrisJVM = project.in(file("doubletetris-jvm")).settings(
  name := "doubletetris-jvm",
  unmanagedSourceDirectories in Compile +=
    (baseDirectory in root).value / "doubletetris-shared" / "src" / "main" / "scala"
)
