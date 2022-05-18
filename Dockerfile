FROM gradle:jdk11 AS build

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . /home/gradle/src

RUN gradle build --no-daemon

FROM openjdk:11

ENV TZ=Asia/Bangkok
ENV JAVA_OPTS="-Xmx1000m -Xms500m -XshowSettings:vm"

WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/booking-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ADD ./entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

ENTRYPOINT /app/entrypoint.sh