# Testing Guidelines & Standards

为了保证代码质量与自动化测试的高效运行，Agent 和开发者必须遵循以下测试规范：

## 1. 测试层级划分与目录约定
- **单元测试 (Unit Tests):** 放在与被测代码同级的目录下。
  - *原则:* 运行速度极快，**严禁产生真实网络 I/O 或本地文件硬盘写盘**。
- **集成/E2E 测试 (Integration Tests):** 统一放在根目录 `testings/` 下。
  - *原则:* 允许通过 Mock Server 或内存数据库/桩模拟真实依赖流转。

## 2. 框架与工具链
- **Test Runner:** 根据 Kotlin / Android (Wear OS) 选用标准测试框架（如 Go `testing` + `testify` / Vitest / Jest / PyTest）。
- **Mock 工具:** 使用打桩/ Mock 库生成接口 Mock，或者基于 HTTP 测试桩进行模拟。
- **测试命名规范:** `Test<Component>_<Scenario>`
  - 示例: `TestPeerTable_HeartbeatTimeout`

## 3. Mock 编写原则
- 针对外部依赖（数据库、缓存、RPC 远程服务、第三方 API、硬件 Gateway），**必须抽象为 Interface/Trait 并使用 Mock**。
- 不得在测试代码中硬编码任何本地绝对路径或敏感 Token。
- 禁止写敷衍测试（例如仅测试 Getter/Setter），测试必须覆盖真实边界分支与异常流程。

## 4. 跑测试的标准命令
- **运行全量单元测试:** 根据 Kotlin / Android (Wear OS) 配置对应的单元测试运行指令（例如 `go test -v -race ./...` 或 `npm test`）。
- **运行指定规范的集成测试:** 按照 `specs/modules/*.spec.md` 中的 `Mapped Test` 指定的入口运行测试。
