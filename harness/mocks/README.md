# Harness Mocks Directory

本目录用于存放外部依赖（数据库、Redis、RPC 远程服务、第三方 API、硬件 Gateway）的 Mock 规范、桩服务或 WireMock/MockServer 配置文件。

- **原则**: 单元测试与沙盒测试中严禁发起真实网络 I/O，必须使用本目录中的 Mocks。
