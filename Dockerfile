FROM adoptopenjdk:14-jre-hotspot
RUN mkdir /opt/batch-processor \
    && mkdir /opt/batch-processor/data && mkdir /opt/batch-processor/data/in/
COPY ./recipient_data_schema.xsd /opt/batch-processor	
COPY ./target/batch-processor-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/batch-processor
WORKDIR /opt/batch-processor
CMD ["java", "-jar", "batch-processor-1.0-SNAPSHOT-jar-with-dependencies.jar"]
RUN chown -R root /opt/batch-processor
RUN chmod -R 777 /opt/batch-processor