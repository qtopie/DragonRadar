# AGENTS.md - System Operating Guidelines

Welcome Agent! You are a core collaborator in this repository. You MUST strictly adhere to these operational rules.

## 1. Context Loading & Memory Rule
- **Always Check `.agents/TASK.md` First:** Before taking any action, read `.agents/TASK.md` to restore context.
- **Maintain `.agents/TASK.md`:** Update `.agents/TASK.md` checklist items as you progress. If interrupted, write the current status under `Current Context`.

## 2. Spec-First Gate (Strict Enforcement)
- **SSOT (Single Source of Truth):** All behavioral contracts belong in `specs/`. Never implement feature logic without an approved Spec.
- **No Spec, No Code:** 
  1. Draft/Update files in `specs/` or `docs/rfcs/`.
  2. Wait for explicit user approval (`APPROVE`).
  3. Generate or update test harness fixtures (`harness/`) and test stubs (`testings/`).
  4. Only then implement business logic code.

## 3. Harness Engineering & Testing Gate
- **Harness-Driven Development:** Maintain fixtures, mocks, and runners in `harness/`.
- **Structured Diagnostic Feedback:** When tests fail, read the `Harness Failure Report` automatically written to `.agents/TASK.md` to perform targeted fixes instead of guessing logs.
- **No Shallow Tests:** Never write trivial Getter/Setter tests; tests must cover real boundary conditions and error scenarios.
- **Mock External Dependencies:** Always mock databases, network I/O, external RPCs, and hardware APIs in unit tests and harness mocks (`harness/mocks/`).

## 4. Grounding & Code Rules
- **Read Before Write:** Read target files and their dependencies before editing.
- **Code Conventions:** Strictly adhere to the project language coding standards documented in `docs/references/code-conventions.md`.
- **Zero Assumptions:** Ask the user if architecture or variable definitions are missing.
- **Minimal Diff:** Modify only what is required. Do not refactor unrelated code.

## 5. Execution & Safety Red Lines
- **Prohibited Commands:** Never run `git push --force`, `rm -rf /`, or alter external systems.
- **Mandatory Self-Validation:** Run `./scripts/check.sh` (or `./scripts/check-harness.sh`) before marking a task complete.
- **Error Limit:** If test/compile fixes fail > 3 times, stop and ask the user for guidance.
