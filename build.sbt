name := "mongoless"

organization := "com.github.a14e"

version := "0.3.01-KINOPLAN"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "com.chuusai"       %% "shapeless"          % "2.3.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0",
  "org.scalatest"     %% "scalatest"          % "3.0.8" % "test"
)

javacOptions in(Compile, compile) ++= {
  val javaVersion = "1.8"
  Seq("-source", javaVersion, "-target", javaVersion)
}