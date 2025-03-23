# Claudeガイド for Todoアプリケーション

## ビルドコマンド
- ビルド: `mvn clean package`
- アプリケーション実行: `mvn spring-boot:run`
- 全テスト実行: `mvn test`
- 単一テスト実行: `mvn test -Dtest=テストクラス名#メソッド名`
- 開発環境起動: `docker-compose up -d`

## コーディング規約
- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)に準拠
- パッケージ構成: controller → service → repositoryの階層構造
- Lombokアノテーションを活用してボイラープレートコードを削減（@Data, @Slf4jなど）
- Spring Bootアノテーションを適切に使用（@Controller, @Serviceなど）
- コントローラーレベルで入力検証を行う
- サービスはインターフェースを使用し、依存性注入をサポート
- インポート文はアルファベット順に整理し、未使用のものは削除
- 命名規則: メソッドと変数にはキャメルケース、クラスにはパスカルケースを使用
- 例外は@ExceptionHandlerや@ControllerAdviceを使用して適切に処理
- データベース操作にはMyBatisアノテーションを使用
- 各コンポーネントには意味のあるユニットテストを記述

## 技術スタック
Java 21, Spring Boot 3.4, MyBatis, PostgreSQL, Thymeleaf, Spring Security (OIDC)