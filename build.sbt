name := """counter-strike-play"""
organization := "br.com.haroldo"

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
  //.enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend

crossScalaVersions := Seq("2.13.16", "3.3.5")

scalaVersion := crossScalaVersions.value.head

libraryDependencies += guice

libraryDependencies ++= Seq(
  guice,
  "com.h2database" % "h2" % "2.3.232",
  "org.webjars" % "bootstrap" % "5.3.5",
  "org.webjars" % "jquery" % "3.7.1"
)
