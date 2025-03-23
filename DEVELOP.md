# 開発者向けドキュメント

## アプリケーションスタック

| 要素                | 説明
| ----                | ----
| 言語                | Java
| フレームワーク      | Spring Boot
| RDBMS               | PostgreSQL
| 認証・認可          | Spring Security(OpenID Connect)
| DB lib              | MyBatis
| テンプレート lib    | Thymleaf
| 便利 lib            | Lombok


## コーディング規約

[Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)


## ディレクトリ構成

```
dev.mikoto2000.todo
    +- advice
    +- configuration
    +- controller
    +- service
    +- repository
    +- validator
```

## 開発フロー

### 開発環境起動

```sh
docker-compose up -d
```

or

Visual Studio Code Dev Container Extension でこのディレクトリを開く

### 実行

```sh
mvn spring-boot:run
```

### ビルド

```sh
mvn clean package
```

### テスト

```sh
mvn test
```


## Spring Initializr

[See here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.4&packaging=jar&jvmVersion=21&groupId=dev.mikoto2000&artifactId=todo&name=todo&description=Demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.todo&dependencies=postgresql,mybatis,web,thymeleaf,devtools,lombok,oauth2-client,oauth2-resource-server)

