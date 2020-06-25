FROM jboss/wildfly
ADD batch-api.war /opt/jboss/wildfly/standalone/deployments/

RUN mkdir -p /opt/jboss/wildfly/standalone/app/data/downloads && \
  mkdir /opt/jboss/wildfly/standalone/app/data/uploads && \
  mkdir /opt/jboss/wildfly/standalone/app/logs

ENV TZ Asia/Bangkok

EXPOSE 8080
