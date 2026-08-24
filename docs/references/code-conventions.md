# Code Conventions (Kotlin / Android (Wear OS))

## 1. Formatting & Naming
- 遵循 Kotlin / Android (Wear OS) 社区标准命名规范与格式化工具。

## 2. Error Handling
- 所有错误必须明确捕获与处理，禁止吞掉异常。
- 错误信息需具备上下文说明。

## 3. Logging & Telemetry
- 使用结构化日志。
- 禁止输出密码、密钥、Token 等敏感数据。

## 4. Security Red Lines
- 防范 SQL 注入、XSS、任意文件读写等安全隐患。
