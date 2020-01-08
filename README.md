## Rest-assured
#### Bookmark
- <https://github.com/rest-assured/rest-assured/wiki/Usage#authentication>

#### [Getting Started](https://github.com/rest-assured/rest-assured/wiki/GettingStarted)
- maven 

```xml
<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>rest-assured</artifactId>
      <version>4.1.2</version>
      <scope>test</scope>
</dependency>
```

- gradle

```groovy
testImplementation 'io.rest-assured:rest-assured:4.1.2'
```

#### Notes
- Include [JsonPath](https://github.com/json-path/JsonPath)

### Rest-assured With Spring Mock MVC
#### Getting Started
- Maven
```xml
<dependency>
      <groupId>io.rest-assured</groupId>
      <artifactId>spring-mock-mvc</artifactId>
      <version>4.1.2</version>
      <scope>test</scope>
</dependency>
```
- Gradle

```groovy
testImplementation 'io.rest-assured:spring-mock-mvc:4.1.2'
```

#### To-do
- With Spring Boot
- With TestContainer


#### Reference
- <https://www.slideshare.net/ssuser59a869/ksug-2019>
- <https://martinfowler.com/articles/practical-test-pyramid.html>
- <http://woowabros.github.io/experience/2018/12/28/spring-rest-docs.html>
- <https://github.com/spring-projects/spring-restdocs>