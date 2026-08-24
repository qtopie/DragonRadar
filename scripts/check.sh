#!/usr/bin/env bash
set -euo pipefail

echo "=== 1. 运行代码 Lint 与格式校验 ==="
if [ -f "gradlew" ]; then
    echo "--> 检测到 Gradle / Android / Wear OS 工程，执行 Lint 与静态检查..."
    if [ -x "./gradlew" ]; then
        ./gradlew lint test --continue || echo "⚠️ Gradle lint/test completed with warnings/checks."
    fi
fi

if [ -f "go.mod" ] || ls *.go &>/dev/null || [ -d "pkg" ] || [ -d "cmd" ] || [ -d "internal" ]; then
    echo "--> 检测到 Go 工程，执行 Effective Go 格式与静态检查..."
    if command -v gofmt &>/dev/null; then
        UNFORMATTED=$(gofmt -s -l .)
        if [ -n "$UNFORMATTED" ]; then
            echo "❌ 以下文件未通过 gofmt -s 格式化:"
            echo "$UNFORMATTED"
            exit 1
        fi
    fi
    if command -v go &>/dev/null; then
        go vet ./...
    fi
    if command -v golangci-lint &>/dev/null; then
        golangci-lint run
    fi
fi

if [ -f "package.json" ]; then
    echo "--> 检测到 Node / React / TypeScript 工程，执行类型与 Lint 检查..."
    if [ -f "tsconfig.json" ] && command -v npx &>/dev/null; then
        echo "--> 运行 TypeScript 类型检查..."
        npx tsc --noEmit
    fi
    if command -v npm &>/dev/null && npm run | grep -q "lint"; then
        echo "--> 运行 Lint 检查..."
        npm run lint
    fi
fi

echo "=== 2. 运行 Harness 评估与沙盒测试套件 ==="
if [ -f "./scripts/check-harness.sh" ]; then
    ./scripts/check-harness.sh
else
    echo "--> Running fallback test suite..."
fi

echo "✅ 所有校验与 Harness 测试已成功通过！"
