조건

JAVA = 11

GRADLE = 7.2

DB = maria version 10

DB 정보
 - database : work
 - table : product
 - user : work
 - password : work

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

사용법
-  서버IP/index 로 들어가시면 간단한 페이지로 테스트 가능합니다.

- 각 REST API를 직접 호출합니다.

  중괄호 내용은 각 해당하는 값을 넣어주시면 됩니다.
  * productCategory = 상품 상위 카테고리(Stirng)
  * productCategoryChild = 상품 하위 카테고리(String)
  * productId = 상품 ID(int)
  
    1. GET /product-list/{productCategory}/{productCategoryChild}/{productId}
        - 값을 입력하지 않으면 각각 해당하는 상위 카테고리 전체가 리턴됩니다.
    2. GET /product-info/{productId}
        - 값을 입력하지 않으면 400 에러가 리턴됩니다.
    3. POST /product-register
    4. PUT /product-changer
    5. DELETE /product-unregister/{productId}
        - 값을 입력하지 않으면 400 에러가 리턴됩니다.
    
- 테스트코드는 test/java/com/test/work/productTest 내부에 있습니다.


