name := "akka-kafka-cassandra"

version := "1.0"

scalaVersion := "2.12.2"

libraryDependencies ++= {
  lazy val Version = new {
    val akka = "2.5.3"
    val akkaHttp = "10.0.9"
    val circe = "0.8.0"
  }

  Seq(
    "com.typesafe.akka"       %% "akka-actor"           % Version.akka,
    "com.typesafe.akka"       %% "akka-stream"          % Version.akka,
    "com.typesafe.akka"       %% "akka-http"            % Version.akkaHttp,
    "com.typesafe.akka"       %% "akka-http-testkit"    % Version.akkaHttp,
    "de.heikoseeberger"       %% "akka-http-circe"      % "1.17.0",
    "io.circe"                %% "circe-core"           % Version.circe,
    "io.circe"                %% "circe-generic"        % Version.circe,
    "io.circe"                %% "circe-parser"         % Version.circe,
    "com.pauldijou"           %% "jwt-circe"            % "0.13.0",
    "com.wix"                 %% "accord-core"          % "0.6.1",
    "org.slf4j"               %  "slf4j-nop"            % "1.7.25",
    "com.github.pureconfig"   %% "pureconfig"           % "0.7.2",
    "org.apache.kafka"        %% "kafka"                % "0.11.0.0",
    "com.outworkers"          %% "phantom-dsl"          % "2.12.1",
    "org.scalatest"           %% "scalatest"            % "3.0.3"           % Test
  )
}