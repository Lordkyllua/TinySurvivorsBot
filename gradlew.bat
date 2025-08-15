@echo off
set APP_HOME=%~dp0
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
java -Xmx64m -cp "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*