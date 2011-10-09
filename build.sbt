import com.typesafe.startscript.StartScriptPlugin

seq(StartScriptPlugin.startScriptForClassesSettings: _*)

name := "Async Test"

version := "1.0"

scalaVersion := "2.9.0"

libraryDependencies ++= Seq("com.ning" % "async-http-client" % "1.6.4",
                            "org.jboss.netty" % "netty" % "3.2.5.Final",
                            "commons-io" % "commons-io" % "2.0.1",
                            "org.codehaus.jackson" % "jackson-mapper-asl" % "1.8.5",
                            "org.slf4j" % "slf4j-api" % "1.6.2",
                            "ch.qos.logback" % "logback-classic" % "0.9.29",
                            "ch.qos.logback" % "logback-core" % "0.9.29",
                            "com.jayway.jsonpath" % "json-path" % "0.5.5",
                            "org.scalatest" % "scalatest_2.9.0" % "1.6.1")

autoCompilerPlugins := true

addCompilerPlugin("org.scala-lang.plugins" % "continuations" % "2.9.0")

scalacOptions += "-P:continuations:enable"

