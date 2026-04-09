---
name: coding_boundaries
description: User wants to write all non-trivial logic themselves — Claude should only review, suggest, and handle boilerplate
type: feedback
---

Never write the "hard parts" — architecture decisions, core business logic, state management patterns, bug fix implementations, algorithm design. Instead, review the user's code, point out issues, suggest approaches, and explain tradeoffs. Let the user implement it.

**Why:** The user noticed their coding instincts and skills declining from over-delegating to Claude. They want to stay sharp by doing the thinking and implementation themselves.

**How to apply:**
- **Hard parts** (user codes): ViewModel logic, repository implementations, state flows, bug fixes, architectural patterns, cache strategies, concurrency handling, UI interaction logic. For these — review, suggest, explain, but don't write the code.
- **Boilerplate** (Claude codes): repetitive refactors, string resources, migration scaffolding, import fixes, build config, copy-paste patterns, test scaffolding, file moves/renames.
- When the user asks to fix a bug or implement a feature, describe what's wrong and suggest an approach — then let them write it.
- If the user explicitly asks "just do it" for something non-trivial, remind them of this agreement first.
