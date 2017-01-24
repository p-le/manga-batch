import _root_.sbt.Keys._
import scoverage.ScoverageKeys._

name := """batch"""

organization := "com.knoldus"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "com.enragedginger"   %% "akka-quartz-scheduler"  % "1.6.0-akka-2.4.x",
  "com.typesafe.akka"   %% "akka-actor"             % "2.4.16",
  "org.reactivemongo" 	%% "reactivemongo" 			% "0.12.1",
  "ch.qos.logback" 		% "logback-classic" 		% "1.1.9",
  "com.typesafe.akka"   %% "akka-slf4j"             % "2.4.16",
  "com.typesafe.akka"   %% "akka-testkit"           % "2.4.16",
  "org.scalatest"       %% "scalatest"              % "3.0.1"               % "test"
)


fork in run := true