organization  := "se.weln"

name := "NoYt"

version := "0.1"

scalaVersion := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

resolvers ++= Seq(
  "spray repo" at "http://repo.spray.io/",
  "spray nightly repo" at "http://nightlies.spray.io/"
)

libraryDependencies ++= Seq(
  // spray
  "io.spray"            %   "spray-can"     % "1.1-20131004",
  "io.spray"            %   "spray-routing" % "1.1-20131004",
  "io.spray"            %   "spray-testkit" % "1.1-20131004",
  "io.spray"            %%  "spray-json"    % "1.2.5",
  "com.typesafe.akka"   %%  "akka-actor"    % "2.1.4",
  "org.specs2"          %%  "specs2"        % "1.13"    %   "test",
  // config
  "com.typesafe"        %   "config"        % "1.0.2"
)

seq(Revolver.settings: _*)

seq(Twirl.settings: _*)

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)
