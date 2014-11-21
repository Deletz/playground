name := """playground"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  javaJpa,  
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "org.webjars" % "jquery" % "2.1.1",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "font-awesome" % "4.1.0"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)
