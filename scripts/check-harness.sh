#!/usr/bin/env bash
set -euo pipefail

echo "=== [Harness Engineering] Running Test Rig & Sandbox Validation ==="

STATUS=0

# Step 1: Run Harness Runners & Fixtures
if [ -f "./harness/runners/spec_runner.sh" ]; then
    echo "--> Running harness/runners/spec_runner.sh..."
    bash ./harness/runners/spec_runner.sh || STATUS=$?
fi

# Step 2: Run Spec Drift Check
if [ -f "./scripts/check-spec-drift.sh" ]; then
    echo "--> Running scripts/check-spec-drift.sh..."
    bash ./scripts/check-spec-drift.sh || STATUS=$?
fi

if [ $STATUS -eq 0 ]; then
    echo "✅ [Harness Engineering] All tests and BDD assertions passed successfully!"
else
    echo "❌ [Harness Engineering] Test failure detected! Generating diagnostic report for .agents/TASK.md..."
    
    # Generate structured Harness Diagnostic Report if TASK.md exists
    if [ -f ".agents/TASK.md" ]; then
        TIMESTAMP=$(date -u +"%Y-%m-%dT%H:%M:%SZ")
        cat <<EOF >> .agents/TASK.md

### 🚨 Harness Failure Report [Timestamp: ${TIMESTAMP}]
- **Status:** Failed (Exit Code: ${STATUS})
- **Instruction:** Please inspect the failed scenario logs and fix the root cause.
- **Action Required:** Update implementation to satisfy Mapped Test & Spec Invariants.
EOF
    fi
    exit $STATUS
fi
