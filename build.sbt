name := """coding-challenge-backend-service"""
organization := "com.wallApp"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test
libraryDependencies ++= Seq(evolutions, jdbc)
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.4"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.wallApp.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.wallApp.binders._"
