#!/bin/sh

echo "A aplicacacao ira iniciar em ${APP_START_SLEEP}s..." && sleep ${APP_START_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -Duser.timezone=${USER_TIMEZONE} -cp /app/resources/:/app/classes/:/app/libs/* "br.com.db1.meritmoney.MeritApplication"  "$@"
