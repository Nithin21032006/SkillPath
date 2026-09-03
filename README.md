# SkillPath - Intelligent Career Roadmap Generator

**SkillPath** is a graph-driven career roadmap and learning path generator implemented in pure Java. It dynamically discovers skill dependencies, resolves learning orders via Topological Sort (Kahn's Algorithm), and provides priority-based skill recommendations using a Max Heap (Priority Queue).

The roadmap is **never hard-coded**; dependencies and skill metadata are driven directly by CSV datasets.

---

## 🏗️ Architecture

```
                 USER
                  ↓
        Select Target Skill
                  ↓
          Dataset Loader
                  ↓
   skills.csv + prerequisites.csv
                  ↓
            Build Graph
                  ↓
      ┌───────────┼────────────┐
      ↓           ↓            ↓
     DFS         BFS       Topological Sort
                              ↓
                       Generate Roadmap
                              ↓
                    Priority / Time Analysis
                              ↓
                    TERMINAL OUTPUT
```

---

## 📂 Project Structure

```
SkillPath/
│
├── src/
│   ├── Main.java              # Interactive CLI terminal interface
│   ├── Skill.java             # Skill entity data model
│   ├── Graph.java             # Adjacency list, DFS, BFS & Kahn's Topological Sort
│   ├── DatasetLoader.java     # CSV dataset parser with header & path handling
│   └── RoadmapGenerator.java  # Phase-based roadmap & Max-Heap priority engine
│
└── data/
    ├── skills.csv             # 40 curated skills across programming, AI, DSA, etc.
    └── prerequisites.csv      # Direct dependency mappings between skills
```

---

## ⚡ Key Algorithms

1. **Depth-First Search (DFS)**: Recursively traces the full closure of prerequisites needed for any chosen target skill.
2. **Topological Sort (Kahn's Algorithm)**: Resolves the exact step-by-step linear learning sequence using in-degree tracking without circular dependencies.
3. **Breadth-First Search (BFS)**: Traverses prerequisite hierarchy level by level.
4. **Priority Queue (Max-Heap)**: Evaluates high-impact skills based on a custom scoring formula:
   $$\text{Score} = (\text{Importance} \times 10) + (\text{Difficulty} \times 5) - \text{Hours}$$
   Allocates study goals dynamically based on the user's available time budget.

---

## 🚀 How to Run

### 1. Compile
```bash
javac -d bin src/*.java
```

### 2. Execute
```bash
java -cp bin Main
```

---

## 💻 Sample Menu

```text
==============================================
                 SKILLPATH
   Intelligent Career Roadmap Generator
==============================================

Dataset loaded successfully!
Skills available: 40

==============================================
                  MENU
==============================================
1. View Available Skills
2. Generate Roadmap
3. Get Priority Recommendations
4. View Skill Dependency Graph
5. BFS Skill Analysis
6. View Prerequisites
0. Exit
```
