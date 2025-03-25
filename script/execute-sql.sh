#!/bin/bash

# データベース接続情報
DB_NAME="todo"
DB_USER="admin"
DB_PASSWORD="password"
DB_HOST="postgres"
DB_PORT="5432"

# 引数のチェック
if [ $# -lt 1 ]; then
    echo "使用法: $0 <SQLファイルパス>"
    exit 1
fi

SQL_FILE=$1

# SQLファイルの存在確認
if [ ! -f "$SQL_FILE" ]; then
    echo "エラー: SQLファイル '$SQL_FILE' が見つかりません"
    exit 1
fi

# SQL実行
echo "SQLファイル実行: $SQL_FILE"
echo "データベース: $DB_NAME"
echo "ホスト: $DB_HOST:$DB_PORT"

PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d "$DB_NAME" -f "$SQL_FILE"

# 実行結果のチェック
if [ $? -eq 0 ]; then
    echo "SQL実行完了"
else
    echo "エラー: SQL実行失敗"
    exit 1
fi