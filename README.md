조건

JAVA = 11
GRADLE = 7.2
DB = maria version 10

build 명령어
gradle --build-file build.gradle work:bootjar

java 실행
1. 빌드 된 jar 파일의 위치로 이동.
2. java -jar work-0.0.1-SNAPSHOT.jar
