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

### 開発の流れ

1. ブランチを切る
2. 開発
    1. コードを書く
    2. テストを書く
    3. テストを実行し、全てのテストが通ることを確認する
    4. ローカルで動作確認
    5. コードをコミット
    6. プッシュ
3. プルリクエストを作成
4. レビュー
5. マージ

#### 1. ブランチを切る

ブランチ名は `feature/` で始まるようにする。

`<feature-name>` は、作業内容を表す名前を指定する。

```sh
git checkout -b feature/<feature-name>
```

#### 2. 開発

##### 2.1. コードを書く

VSCode 等を使ってコードを書く。

##### 2.2. テストを書く

テストは、`src/test/java` に配置する。

##### 2.3. テストを実行し、全てのテストが通ることを確認する

以下コマンドでテストを実行し、全てのテストが通ることを確認する。

```sh
mvn test
```

##### 2.4. ローカルで動作確認

以下コマンドでアプリケーションを起動し、ブラウザで動作確認を行う。

```sh
mvn spring-boot:run
```

##### 2.5. コードをコミット

以下コマンドでコミットする。

`<files...>` には、自分が編集したファイルを指定する

コミットメッセージには、変更内容を簡潔に記述すること。


```sh
git add [<files...>]
git commit
```

##### 2.6. プッシュ

以下コマンドでプッシュする。

`<feature-name>` には、作業中のブランチ名を指定する。

```sh
git push origin feature/<feature-name>
```


#### 3. プルリクエストを作成

GitHub 上でプルリクエストを作成する。

- プルリクエストのタイトルには、作業内容を簡潔に記述する
- プルリクエストの本文には、作業内容、変更点、動作確認方法を記述する
- レビュアーを指定する


#### 4. レビュー

レビュアーがプルリクエストをレビューする。

- レビュアーは、コードの品質、テストの品質、動作確認の結果を確認する
- レビュアーは、コードの変更点に対してコメントを残す
- レビュアーは、コードの変更点に対して修正を要求する
- レビュアーが修正を要求した場合、修正を行い、再度プッシュする
- 修正が完了したら、再度レビュアーにレビューを依頼する


#### 5. マージ

- レビュアーが修正を要求しない場合、マージする
- マージ後、ブランチを削除する


### 開発環境起動

```sh
docker-compose up -d
```

or

Visual Studio Code Dev Container Extension でこのディレクトリを開く

### 開発時の実行

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

### 本番環境での実行

`--spring.profiles.active=prod` で本番環境の設定を読み込む。

```sh
java -jar target/todo-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```


## Spring Initializr

[See here](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.4&packaging=jar&jvmVersion=21&groupId=dev.mikoto2000&artifactId=todo&name=todo&description=Demo%20project%20for%20Spring%20Boot&packageName=dev.mikoto2000.todo&dependencies=postgresql,mybatis,web,thymeleaf,devtools,lombok,oauth2-client,oauth2-resource-server)

