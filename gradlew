#!/usr/bin/env sh
APP_HOME=`dirname "$0"`
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec java -Xmx64m -cp "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"