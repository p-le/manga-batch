import _root_.sbt.Keys._
import scoverage.ScoverageKeys._

name := """batch"""

organization := "com.knoldus"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases"
resolvers += "chrisdinn" at "http://chrisdinn.github.io/releases/"

libraryDependencies ++= Seq(
	"com.typesafe.akka" 	%% "akka-actor" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-agent" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-camel" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-cluster" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-cluster-metrics" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-cluster-sharding" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-cluster-tools" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-contrib" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-multi-node-testkit" 				% "2.4.16",
	"com.typesafe.akka" 	%% "akka-osgi" 				% "2.4.16",
	"com.typesafe.akka" 	%% "akka-persistence" 		% "2.4.16",
	"com.typesafe.akka" 	%% "akka-persistence-tck" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-remote" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-slf4j" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-stream" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-stream-testkit" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-testkit" 			% "2.4.16",
	"com.typesafe.akka" 	%% "akka-distributed-data-experimental" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-typed-experimental" 				% "2.4.16",
	"com.typesafe.akka" 	%% "akka-persistence-query-experimental" 	% "2.4.16",
	"com.typesafe.akka" 	%% "akka-http-core" 		% 	"10.0.2"	withSources(),
	"com.typesafe.akka" 	%% "akka-http" 				% 	"10.0.2"	withSources(),
	"com.typesafe.akka" 	%% "akka-http-testkit" 		% 	"10.0.2",
	"com.typesafe.akka" 	%% "akka-http-spray-json" 	% 	"10.0.2",
	"com.typesafe.akka" 	%% "akka-http-jackson" 		% 	"10.0.2",
	"com.typesafe.akka" 	%% "akka-http-xml" 			% 	"10.0.2",
 	"com.enragedginger"   	%%	"akka-quartz-scheduler"	%	"1.6.0-akka-2.4.x",
	"com.typesafe.slick" 	%%	"slick" 				%	"3.1.1",
	"com.typesafe.slick" 	%% 	"slick-hikaricp" 		%	"3.1.1",
	"com.typesafe.slick" 	%% 	"slick-testkit" 		%	"3.1.1",
	"com.typesafe.slick" 	%% 	"slick-codegen" 		%	"3.1.1",
	"org.scala-lang" 		% 	"scala-reflect" 		% 	"2.11.8",
	"com.github.etaty" 		%% 	"rediscala" 			% 	"1.8.0",
	"mysql" 				%	"mysql-connector-java" 	% 	"5.1.40",
 	"ch.qos.logback" 		%	"logback-classic" 		% 	"1.1.9",
 	"org.scalatest"       	%%	"scalatest"             % 	"3.0.1"               	% "test"
)


fork in run := true

fork in run := true