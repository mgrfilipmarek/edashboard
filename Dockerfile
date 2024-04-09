FROM openjdk:17
COPY target/edashboard-0.0.1-SNAPSHOT.jar edashboard-0.0.1.jar
ENTRYPOINT ["java","-jar","/edashboard-0.0.1.jar"]