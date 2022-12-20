# Spring Toy Projects

## 1. mvc_practice

### 2022-12-20 문제

#### Embed Tomcat이 위치를 못잡음. webapps/로 root 경로를 지정하여 빌드해서 실행하려고 했으나 실패

- intellij Project Structure에서 compiler output 위치 변경 및 module output 위치 변경 => 실패
- Setting에서 buildTool gradle 선택시 build폴더에 빌드되고 Intellij 선택시 out에 빌드됨 => 실패
- 두 상황 모두 Tomcat 오류. webapps/WEB-INF/classes 폴더 생성 후 Tomcat 성공
- 그러나 여전히 webapps/WEB-INF/classe에는 빌드파일이 생성 되지않음

