set ENV=%1
IF [%1]==[] (
set ENV=dev
)
echo %ENV%
java -Xms256m -Xmx512m -server -Dspring.config.location=../config/application.properties -jar ../lib/springboot-demo-0.0.1-SNAPSHOT.jar