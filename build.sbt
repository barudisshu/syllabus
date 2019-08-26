name := "Genetic-Algorithm"
maintainer := "galudisu@gmail.com"

version := "0.1"

scalaVersion := "2.12.8"

organization := "comic"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

logLevel := Level.Error

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq(
  "-Xlog-reflective-calls",
  "-unchecked",
  "-encoding",
  "UTF-8",
  "-Xlint"
)

libraryDependencies ++= Seq(
  // --GA lib--
  "net.sf.jgap" % "jgap" % "3.4.4",
  "org.scalatest" %% "scalatest" % "3.0.3"
)