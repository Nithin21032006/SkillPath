import java.util.*;

public class Graph {

    private HashMap<String, ArrayList<String>>
            adjacencyList = new HashMap<>();

    // Add a skill to graph
    public void addNode(String skill) {

        adjacencyList.putIfAbsent(
                skill.toLowerCase(),
                new ArrayList<>()
        );
    }

    // Add dependency
    public void addEdge(
            String skill,
            String prerequisite) {

        skill = skill.toLowerCase();
        prerequisite = prerequisite.toLowerCase();

        addNode(skill);
        addNode(prerequisite);

        if (!adjacencyList.get(skill).contains(prerequisite)) {
            adjacencyList
                    .get(skill)
                    .add(prerequisite);
        }
    }

    // Get prerequisites
    public ArrayList<String> getPrerequisites(
            String skill) {

        return adjacencyList.getOrDefault(
                skill.toLowerCase(),
                new ArrayList<>()
        );
    }

    // =====================================================
    // DFS
    // =====================================================

    public void dfs(
            String skill,
            HashSet<String> visited,
            ArrayList<String> result) {

        skill = skill.toLowerCase();

        if (visited.contains(skill))
            return;

        visited.add(skill);

        for (String prerequisite :
                getPrerequisites(skill)) {

            dfs(
                    prerequisite,
                    visited,
                    result
            );
        }

        result.add(skill);
    }

    // =====================================================
    // GET ALL PREREQUISITES
    // =====================================================

    public ArrayList<String> getAllPrerequisites(
            String skill) {

        ArrayList<String> result =
                new ArrayList<>();

        HashSet<String> visited =
                new HashSet<>();

        dfs(
                skill,
                visited,
                result
        );

        return result;
    }

    // =====================================================
    // TOPOLOGICAL SORT
    // =====================================================

    public ArrayList<String> topologicalSort(
            String target) {

        ArrayList<String> nodes =
                getAllPrerequisites(target);

        HashSet<String> nodeSet =
                new HashSet<>(nodes);

        HashMap<String, Integer> indegree =
                new HashMap<>();

        for (String node : nodes) {

            indegree.put(node, 0);
        }

        /*
         * Calculate indegree.
         *
         * If:
         *
         * Graphs -> Trees
         *
         * then Graphs depends on Trees.
         */

        for (String node : nodes) {

            for (String prerequisite :
                    getPrerequisites(node)) {

                if (nodeSet.contains(prerequisite)) {

                    indegree.put(
                            node,
                            indegree.get(node) + 1
                    );
                }
            }
        }

        Queue<String> queue =
                new LinkedList<>();

        for (String node : nodes) {

            if (indegree.get(node) == 0) {

                queue.add(node);
            }
        }

        ArrayList<String> result =
                new ArrayList<>();

        while (!queue.isEmpty()) {

            String current =
                    queue.poll();

            result.add(current);

            for (String node : nodes) {

                if (getPrerequisites(node)
                        .contains(current)) {

                    indegree.put(
                            node,
                            indegree.get(node) - 1
                    );

                    if (indegree.get(node) == 0) {

                        queue.add(node);
                    }
                }
            }
        }

        return result;
    }

    // =====================================================
    // BFS
    // =====================================================

    public void bfs(String start) {

        start = start.toLowerCase();

        Queue<String> queue =
                new LinkedList<>();

        HashSet<String> visited =
                new HashSet<>();

        queue.add(start);

        visited.add(start);

        System.out.println(
                "\nBFS traversal:"
        );

        while (!queue.isEmpty()) {

            String current =
                    queue.poll();

            System.out.print(
                    current + " → "
            );

            for (String next :
                    getPrerequisites(current)) {

                if (!visited.contains(next)) {

                    visited.add(next);

                    queue.add(next);
                }
            }
        }

        System.out.println();
    }

    // =====================================================
    // DISPLAY GRAPH
    // =====================================================

    public void display() {

        System.out.println(
                "\n========== SKILL GRAPH =========="
        );

        for (String skill :
                adjacencyList.keySet()) {

            System.out.print(
                    skill + " → "
            );

            ArrayList<String> list =
                    adjacencyList.get(skill);

            if (list.isEmpty()) {

                System.out.println(
                        "No prerequisite"
                );

            } else {

                for (int i = 0;
                     i < list.size();
                     i++) {

                    System.out.print(
                            list.get(i)
                    );

                    if (i < list.size() - 1)
                        System.out.print(", ");
                }

                System.out.println();
            }
        }
    }
}
