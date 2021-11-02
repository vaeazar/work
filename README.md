조건

JAVA = 11
GRADLE = 7.2
DB = maria version 10

build 명령어
gradle --build-file build.gradle work:bootjar

java 실행
1. 빌드 된 jar 파일의 위치로 이동.
2. java -jar work-0.0.1-SNAPSHOT.jar

sql
1. 첨부된 work.sql 파일 안의 sql을 실행
2. create user 'work'@'%' identified by 'work';
3. grant all privileges on *.* to 'work'@'%'; 
4. FLUSH PRIVILEGES;
