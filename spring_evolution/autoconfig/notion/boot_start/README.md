# 스프링 부트

## 스프링 부트의 시작

스프링 부트는 main에서 단 한줄의 코드로 스프링 컨테이너를 생성하고 내장 톰캣을 생성하고 실행하는 일을 수행합니다.

```java
@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }
}
```

<br>

## Jar 파일 분석

스프링 부트의 Jar를 분석해보면 Fat Jar가 아니라 실행 가능 Jar(Executable Jar)라는 새로운 구조로 만들어져 있습니다.

실행 가능 Jar는 Jar 내부에 Jar를 포함할 수 있고 동시에 만든 Jar를 내부 Jar를 포함해서 실행할 수 있게 합니다.

실행 가능 Jar는 자바 표준은 아니고, 스프링 부트에서 새롭게 정의한 것입니다.

### Fat Jar보다 좋은 점

- 어떤 라이브러리가 포함되어 있는지 쉽게 확인 할 수 있습니다.
- 파일명 중복을 해결할 수 있습니다.

### 실행 가능 Jar의 구조

`boot-0.0.1-SNAPSHOT.jar`
  
  - `META-INF`
    - `MANIFEST.MF`
    

  - `org/springframework/boot/loader`
    - `JarLauncher.class`
    

  - `BOOT-INF`
    - `classes` : 우리가 개발한 class 파일과 리소스 파일
      - `hello/boot/BootApplication.class`
      - `hello/boot/controller/HelloController.class`
      - ...
    - `lib` : 외부 라이브러리
      - `spring-webmvc-6.0.4.jar`
      - `tomcat-embed-core-10.1.5.jar`
      - ...
    - `classpath.idx` : 외부 라이브러리 모음
    - `layers.idx` : 스프링 부트 구조 정보

### 실행 순서

Jar 파일을 실행하게 되면 먼저 `META-INF/MANIFEST.MF` 파일을 찾아서 Main-Class로 지정된 `org.springframework.boot.loader.JarLauncher` 클래스를 실행합니다.

```
Main-Class: org.springframework.boot.loader.JarLauncher
Start-Class: hello.boot.BootApplication
```

스프링 부트는 Jar 내부에 Jar를 읽어들이는 기능이 필요하고 특별한 구조에 맞춰 클래스 정보도 읽어야 합니다. 이런 작업을 `JarLauncher` 클래스가 수행합니다.

`JarLauncher`를 통해서 classes와 lib에 있는 Jar파일들을 읽어들입니다.

해당 작업을 수행한 후 Start-Class로 지정된 `hello.boot.BootApplication` 클래스를 실행합니다.

> `boot-0.0.1-SNAPSHOT-plain.jar`는 우리가 개발한 코드만 순수한 jar로 빌드한 것입니다. (무시하기)

> IDE에서 직접 실행할 때는 필요한 라이브러리를 모두 인식하도록 도와주기 때문에 JarLauncher가 필요하지 않고 BootApplication.main()을 직접 실행하는 것입니다.

<br>

## 자동 라이브러리 관리

### 라이브러리르 직접 지정

직접 라이브러리를 지정하게 되면 개발자는 수 많은 라이브러리를 알아야하고 버전을 골라야하며 라이브러리간 호환을 고려해야합니다.

```groovy
plugins {
    id 'org.springframework.boot' version '3.0.2'
    // id 'io.spring.dependency-management' version '1.1.0' 
    id 'java'
}

dependencies {
 // 스프링 웹 MVC
 implementation 'org.springframework:spring-webmvc:6.0.4'
 // 내장 톰캣
 implementation 'org.apache.tomcat.embed:tomcat-embed-core:10.1.5'
 // JSON 처리
 implementation 'com.fasterxml.jackson.core:jackson-databind:2.14.1'
 // 스프링 부트 관련
 implementation 'org.springframework.boot:spring-boot:3.0.2'
 implementation 'org.springframework.boot:spring-boot-autoconfigure:3.0.2'
 // LOG 관련
 implementation 'ch.qos.logback:logback-classic:1.4.5'
 implementation 'org.apache.logging.log4j:log4j-to-slf4j:2.19.0'
 implementation 'org.slf4j:jul-to-slf4j:2.0.6'
 // YML 관련
 implementation 'org.yaml:snakeyaml:1.33'
}
```

### 스프링 부트 라이브러리 버전 관리

스프링 부트는 개발자 대신 수 많은 라이브러리의 버전을 직접 관리 해줍니다. 스프링 부트 버전에 맞춘 최적화된 라이브러리 버전을 선택해줍니다.

```groovy
plugins {
    id 'org.springframework.boot' version '3.0.2'
    id 'io.spring.dependency-management' version '1.1.0' //추가
    id 'java'
}

dependencies {
    // 스프링 웹, MVC
    implementation 'org.springframework:spring-webmvc'
    // 내장 톰캣
    implementation 'org.apache.tomcat.embed:tomcat-embed-core'
    // JSON 처리
    implementation 'com.fasterxml.jackson.core:jackson-databind'
    // 스프링 부트 관련
    implementation 'org.springframework.boot:spring-boot'
    implementation 'org.springframework.boot:spring-boot-autoconfigure'
    // LOG 관련
    implementation 'ch.qos.logback:logback-classic'
    implementation 'org.apache.logging.log4j:log4j-to-slf4j'
    implementation 'org.slf4j:jul-to-slf4j'
    // YML 관련
    implementation 'org.yaml:snakeyaml'
}
```

`spring-boot-dependencies`는 스프링 부트 gradle 플러그인에서 사용하기 때문에 개발자 눈에 의존관계가 보이지 않습니다.

[BOM (Bil of materials)](https://github.com/spring-projects/spring-boot/blob/main/spring-boot-project/spring-boot-dependencies/build.gradle)

문서를 확인해보면 스프링 부트 버전을 참고한 버전 정보들이 bom이라는 항목에 명시되어 있습니다. 

스프링 부트가 관리하지 않는 라이브러리의 경우 직접 버전을 지정해주어야 합니다.

### 스프링 부트 스타터

웹 프로젝트 하나에 수 많은 라이브러리가 필요합니다. 스프링 부트는 이런 라이브러리들을 묶어서 스타터로 제공합니다.

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

`spring-boot-starter-web` 스타터 라이브러리 한개로 우리가 넣었던 수많은 라이브러리들을 모두 포함하고 있습니다. 즉, 의존성을 모아둔 세트입니다.

**라이브러리의 버전을 변경하고 싶을 때**

```groovy
ext['tomcat.version'] = '10.1.4' // 추가

dependencies {
    // ...
}
```

[스프링 부트가 관리하는 외부 라이브러리 변경 버전에 필요한 속성 값 확인](https://docs.spring.io/spring-boot/docs/current/reference/html/dependency-versions.html#appendix.dependency-versions.properties)
