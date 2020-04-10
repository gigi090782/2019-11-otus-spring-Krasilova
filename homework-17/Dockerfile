
FROM maven:3.5.4-jdk-8
MAINTAINER Marina Krasilova <filatovamarina@mail.ru>

ENV DIR=/opt/project
RUN mkdir -p $DIR
WORKDIR $DIR

ADD pom.xml $DIR
RUN mvn dependency:resolve

ADD src/ $DIR/src
RUN mvn install -DskipTests

FROM openjdk:8-jdk

ENV DIR=/opt/project
RUN mkdir -p $DIR
WORKDIR $DIR

COPY --from=0 $DIR/target/My-app-library* $DIR/

RUN ln -svf /dev/stdout /var/log/nginx/access.log
RUN ln -svf /dev/stderr /var/log/nginx/error.log

EXPOSE 8080

CMD ["java", "-jar", "/opt/project/My-app-library.jar"]




