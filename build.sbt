lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  //.enablePlugins(PlayNettyServer).disablePlugins(PlayPekkoHttpServer) // uncomment to use the Netty backend
  .settings(
    name := "counter-strike-play",
    maintainer := "haroldoramirez@gmail.com",
    version := "1.0.0",
    crossScalaVersions := Seq("2.13.16", "3.3.5"),
    scalaVersion := crossScalaVersions.value.head,
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      //"com.h2database" % "h2" % "2.3.232",
      "org.webjars" % "bootstrap" % "5.3.7",
      "org.webjars" % "jquery" % "3.7.1",
      "org.postgresql" % "postgresql" % "42.7.7",
      // Tess4J (Tesseract OCR para Java)
      "net.sourceforge.tess4j" % "tess4j" % "5.16.0",
      // OpenCV (opcional, para pré-processamento de imagem)
      "org.openpnp" % "opencv" % "4.9.0-0",
      // JAI ImageIO Core - Para suportar outros formatos de imagens
      "com.github.jai-imageio" % "jai-imageio-core" % "1.4.0"
    ),
    (Test / testOptions) += Tests.Argument(TestFrameworks.JUnit, "-a", "-v"),
    javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")
  )