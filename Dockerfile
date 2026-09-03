FROM tomcat:10.1-jdk21

RUN rm -rf /usr/local/tomcat/webapps/*

COPY SmartHire.war /usr/local/tomcat/webapps/SmartHire.war

EXPOSE 8080

CMD ["catalina.sh", "run"]