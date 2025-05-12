@REM counter-strike-play launcher script
@REM
@REM Environment:
@REM JAVA_HOME - location of a JDK home dir (optional if java on path)
@REM CFG_OPTS  - JVM options (optional)
@REM Configuration:
@REM COUNTER_STRIKE_PLAY_config.txt found in the COUNTER_STRIKE_PLAY_HOME.
@setlocal enabledelayedexpansion
@setlocal enableextensions

@echo off


if "%COUNTER_STRIKE_PLAY_HOME%"=="" (
  set "APP_HOME=%~dp0\\.."

  rem Also set the old env name for backwards compatibility
  set "COUNTER_STRIKE_PLAY_HOME=%~dp0\\.."
) else (
  set "APP_HOME=%COUNTER_STRIKE_PLAY_HOME%"
)

set "APP_LIB_DIR=%APP_HOME%\lib\"

rem Detect if we were double clicked, although theoretically A user could
rem manually run cmd /c
for %%x in (!cmdcmdline!) do if %%~x==/c set DOUBLECLICKED=1

rem FIRST we load the config file of extra options.
set "CFG_FILE=%APP_HOME%\COUNTER_STRIKE_PLAY_config.txt"
set CFG_OPTS=
call :parse_config "%CFG_FILE%" CFG_OPTS

rem We use the value of the JAVA_OPTS environment variable if defined, rather than the config.
set _JAVA_OPTS=%JAVA_OPTS%
if "!_JAVA_OPTS!"=="" set _JAVA_OPTS=!CFG_OPTS!

rem We keep in _JAVA_PARAMS all -J-prefixed and -D-prefixed arguments
rem "-J" is stripped, "-D" is left as is, and everything is appended to JAVA_OPTS
set _JAVA_PARAMS=
set _APP_ARGS=

set "APP_CLASSPATH=%APP_LIB_DIR%\..\conf\;%APP_LIB_DIR%\br.com.haroldo.counter-strike-play-1.0.0-sans-externalized.jar;%APP_LIB_DIR%\org.scala-lang.scala-library-2.13.16.jar;%APP_LIB_DIR%\org.playframework.twirl.twirl-api_2.13-2.0.8.jar;%APP_LIB_DIR%\org.playframework.play-server_2.13-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-logback_2.13-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-pekko-http-server_2.13-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-filters-helpers_2.13-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-java-forms_2.13-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-guice_2.13-3.0.7.jar;%APP_LIB_DIR%\com.h2database.h2-2.3.232.jar;%APP_LIB_DIR%\org.webjars.bootstrap-5.3.5.jar;%APP_LIB_DIR%\org.webjars.jquery-3.7.1.jar;%APP_LIB_DIR%\org.scala-lang.modules.scala-xml_2.13-2.2.0.jar;%APP_LIB_DIR%\org.playframework.play_2.13-3.0.7.jar;%APP_LIB_DIR%\ch.qos.logback.logback-classic-1.5.17.jar;%APP_LIB_DIR%\org.playframework.play-streams_2.13-3.0.7.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-http-core_2.13-1.0.1.jar;%APP_LIB_DIR%\org.playframework.play-java_2.13-3.0.7.jar;%APP_LIB_DIR%\net.jodah.typetools-0.6.3.jar;%APP_LIB_DIR%\org.hibernate.validator.hibernate-validator-6.2.5.Final.jar;%APP_LIB_DIR%\org.springframework.spring-context-5.3.39.jar;%APP_LIB_DIR%\org.springframework.spring-core-5.3.39.jar;%APP_LIB_DIR%\org.springframework.spring-beans-5.3.39.jar;%APP_LIB_DIR%\com.google.inject.guice-6.0.0.jar;%APP_LIB_DIR%\com.google.inject.extensions.guice-assistedinject-6.0.0.jar;%APP_LIB_DIR%\org.playframework.play-build-link-3.0.7.jar;%APP_LIB_DIR%\org.playframework.play-configuration_2.13-3.0.7.jar;%APP_LIB_DIR%\org.slf4j.slf4j-api-2.0.17.jar;%APP_LIB_DIR%\org.slf4j.jul-to-slf4j-2.0.17.jar;%APP_LIB_DIR%\org.slf4j.jcl-over-slf4j-2.0.17.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-actor_2.13-1.0.3.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-actor-typed_2.13-1.0.3.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-slf4j_2.13-1.0.3.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-serialization-jackson_2.13-1.0.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-core-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-annotations-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.datatype.jackson-datatype-jdk8-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.datatype.jackson-datatype-jsr310-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.core.jackson-databind-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.dataformat.jackson-dataformat-cbor-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.module.jackson-module-parameter-names-2.14.3.jar;%APP_LIB_DIR%\com.fasterxml.jackson.module.jackson-module-scala_2.13-2.14.3.jar;%APP_LIB_DIR%\io.jsonwebtoken.jjwt-api-0.11.5.jar;%APP_LIB_DIR%\io.jsonwebtoken.jjwt-impl-0.11.5.jar;%APP_LIB_DIR%\io.jsonwebtoken.jjwt-jackson-0.11.5.jar;%APP_LIB_DIR%\org.playframework.play-json_2.13-3.0.4.jar;%APP_LIB_DIR%\com.google.guava.guava-32.1.3-jre.jar;%APP_LIB_DIR%\javax.inject.javax.inject-1.jar;%APP_LIB_DIR%\com.typesafe.ssl-config-core_2.13-0.6.1.jar;%APP_LIB_DIR%\org.scala-lang.modules.scala-parser-combinators_2.13-1.1.2.jar;%APP_LIB_DIR%\ch.qos.logback.logback-core-1.5.17.jar;%APP_LIB_DIR%\org.reactivestreams.reactive-streams-1.0.4.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-stream_2.13-1.0.3.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-parsing_2.13-1.0.1.jar;%APP_LIB_DIR%\org.parboiled.parboiled_2.13-2.5.0.jar;%APP_LIB_DIR%\jakarta.validation.jakarta.validation-api-2.0.2.jar;%APP_LIB_DIR%\org.jboss.logging.jboss-logging-3.4.1.Final.jar;%APP_LIB_DIR%\com.fasterxml.classmate-1.5.1.jar;%APP_LIB_DIR%\jakarta.inject.jakarta.inject-api-2.0.1.jar;%APP_LIB_DIR%\aopalliance.aopalliance-1.0.jar;%APP_LIB_DIR%\com.google.errorprone.error_prone_annotations-2.21.1.jar;%APP_LIB_DIR%\org.playframework.play-exceptions-3.0.7.jar;%APP_LIB_DIR%\com.typesafe.config-1.4.3.jar;%APP_LIB_DIR%\org.lz4.lz4-java-1.8.0.jar;%APP_LIB_DIR%\com.thoughtworks.paranamer.paranamer-2.8.jar;%APP_LIB_DIR%\org.playframework.play-functional_2.13-3.0.4.jar;%APP_LIB_DIR%\org.scala-lang.scala-reflect-2.13.16.jar;%APP_LIB_DIR%\com.google.guava.failureaccess-1.0.1.jar;%APP_LIB_DIR%\com.google.guava.listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_LIB_DIR%\com.google.code.findbugs.jsr305-3.0.2.jar;%APP_LIB_DIR%\org.checkerframework.checker-qual-3.37.0.jar;%APP_LIB_DIR%\com.google.j2objc.j2objc-annotations-2.8.jar;%APP_LIB_DIR%\org.apache.pekko.pekko-protobuf-v3_2.13-1.0.3.jar;%APP_LIB_DIR%\br.com.haroldo.counter-strike-play-1.0.0-assets.jar"
set "APP_MAIN_CLASS=play.core.server.ProdServerStart"
set "SCRIPT_CONF_FILE=%APP_HOME%\conf\application.ini"

rem Bundled JRE has priority over standard environment variables
if defined BUNDLED_JVM (
  set "_JAVACMD=%BUNDLED_JVM%\bin\java.exe"
) else (
  if "%JAVACMD%" neq "" (
    set "_JAVACMD=%JAVACMD%"
  ) else (
    if "%JAVA_HOME%" neq "" (
      if exist "%JAVA_HOME%\bin\java.exe" set "_JAVACMD=%JAVA_HOME%\bin\java.exe"
    )
  )
)

if "%_JAVACMD%"=="" set _JAVACMD=java

rem if configuration files exist, prepend their contents to the script arguments so it can be processed by this runner
call :parse_config "%SCRIPT_CONF_FILE%" SCRIPT_CONF_ARGS

call :process_args %SCRIPT_CONF_ARGS% %%*

rem Detect if this java is ok to use.
for /F %%j in ('"%_JAVACMD%" -version  2^>^&1') do (
  if %%~j==java set JAVAINSTALLED=1
  if %%~j==openjdk set JAVAINSTALLED=1
)

rem BAT has no logical or, so we do it OLD SCHOOL! Oppan Redmond Style
set JAVAOK=true
if not defined JAVAINSTALLED set JAVAOK=false

if "%JAVAOK%"=="false" (
  echo.
  echo A Java JDK is not installed or can't be found.
  if not "%JAVA_HOME%"=="" (
    echo JAVA_HOME = "%JAVA_HOME%"
  )
  echo.
  echo Please go to
  echo   http://www.oracle.com/technetwork/java/javase/downloads/index.html
  echo and download a valid Java JDK and install before running counter-strike-play.
  echo.
  echo If you think this message is in error, please check
  echo your environment variables to see if "java.exe" and "javac.exe" are
  echo available via JAVA_HOME or PATH.
  echo.
  if defined DOUBLECLICKED pause
  exit /B 1
)

set _JAVA_OPTS=!_JAVA_OPTS! !_JAVA_PARAMS!

if defined CUSTOM_MAIN_CLASS (
    set MAIN_CLASS=!CUSTOM_MAIN_CLASS!
) else (
    set MAIN_CLASS=!APP_MAIN_CLASS!
)

rem Call the application and pass all arguments unchanged.
"%_JAVACMD%" !_JAVA_OPTS! !COUNTER_STRIKE_PLAY_OPTS! -cp "%APP_CLASSPATH%" %MAIN_CLASS% !_APP_ARGS!

@endlocal

exit /B %ERRORLEVEL%


rem Loads a configuration file full of default command line options for this script.
rem First argument is the path to the config file.
rem Second argument is the name of the environment variable to write to.
:parse_config
  set _PARSE_FILE=%~1
  set _PARSE_OUT=
  if exist "%_PARSE_FILE%" (
    FOR /F "tokens=* eol=# usebackq delims=" %%i IN ("%_PARSE_FILE%") DO (
      set _PARSE_OUT=!_PARSE_OUT! %%i
    )
  )
  set %2=!_PARSE_OUT!
exit /B 0


:add_java
  set _JAVA_PARAMS=!_JAVA_PARAMS! %*
exit /B 0


:add_app
  set _APP_ARGS=!_APP_ARGS! %*
exit /B 0


rem Processes incoming arguments and places them in appropriate global variables
:process_args
  :param_loop
  shift
  call set _PARAM1=%%0
  set "_TEST_PARAM=%~0"

  if ["!_PARAM1!"]==[""] goto param_afterloop

  if "!_TEST_PARAM!"=="-main" (
    call set CUSTOM_MAIN_CLASS=%%1
    shift
    goto param_loop
  )

  if "!_TEST_PARAM!"=="-java-home" (
    set "JAVA_HOME=%~1"
    set "_JAVACMD=%~1\bin\java.exe"
    shift
    goto param_loop
  )

  if "!_TEST_PARAM!"=="-jvm-debug" (
    set "_JAVA_PARAMS=!_JAVA_PARAMS! -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=%~1"
    shift
    goto param_loop
  )

  if "!_TEST_PARAM:~0,2!"=="-J" (
    rem strip -J prefix
    call set _TEST_PARAM=!_TEST_PARAM:~2!
    if not "!_TEST_PARAM:~0,5!" == "-XX:+" if not "!_TEST_PARAM:~0,5!" == "-XX:-" if "!_TEST_PARAM:~0,3!" == "-XX" (
      rem special handling for -J-XX since '=' gets parsed away
      for /F "delims== tokens=1,*" %%G in ("!_TEST_PARAM!") DO (
        call set _TEST_PARAM=!_TEST_PARAM!=%%1
        shift
      )
    )
    set _JAVA_PARAMS=!_JAVA_PARAMS! !_TEST_PARAM!
    goto param_loop
  )

  if "!_TEST_PARAM:~0,2!"=="-D" (
    rem test if this was double-quoted property "-Dprop=42"
    for /F "delims== tokens=1,*" %%G in ("!_TEST_PARAM!") DO (
      if not ["%%H"] == [""] (
        set _JAVA_PARAMS=!_JAVA_PARAMS! !_PARAM1!
      ) else if [%1] neq [] (
        rem it was a normal property: -Dprop=42 or -Drop="42"
        call set _PARAM1=%%0=%%1
        set _JAVA_PARAMS=!_JAVA_PARAMS! !_PARAM1!
        shift
      )
    )
    goto param_loop
  )

  set _APP_ARGS=!_APP_ARGS! !_PARAM1!

  goto param_loop
  :param_afterloop

exit /B 0
