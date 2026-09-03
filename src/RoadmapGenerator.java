import java.util.*;

public class RoadmapGenerator {

    private HashMap<String, Skill> skills;

    private Graph graph;

    public RoadmapGenerator(
            HashMap<String, Skill> skills,
            Graph graph) {

        this.skills = skills;
        this.graph = graph;
    }

    public void generate(String target) {

        target = target.toLowerCase();

        if (!skills.containsKey(target)) {

            System.out.println(
                    "\nSkill not found in dataset."
            );

            return;
        }

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "           GENERATING ROADMAP"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "\nTarget: " +
                        skills.get(target).name
        );

        System.out.println(
                "\nAnalyzing prerequisites..."
        );

        ArrayList<String> path =
                graph.topologicalSort(target);

        System.out.println(
                "Found " +
                        path.size() +
                        " skills."
        );

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "              YOUR ROADMAP"
        );

        System.out.println(
                "=============================================="
        );

        int totalHours = 0;

        int phase = 1;

        String previousCategory = "";

        int step = 1;

        for (int i = 0;
             i < path.size();
             i++) {

            String skillName =
                    path.get(i);

            Skill skill =
                    skills.get(skillName);

            if (skill == null)
                continue;

            if (!skill.category
                    .equals(previousCategory)) {

                System.out.println(
                        "\nPHASE " +
                                phase +
                                " — " +
                                skill.category
                                .toUpperCase()
                );

                phase++;

                previousCategory =
                        skill.category;
            }

            System.out.printf(
                    "%02d → %-25s | %2dh | Difficulty: %d%n",
                    step++,
                    skill.name,
                    skill.hours,
                    skill.difficulty
            );

            totalHours += skill.hours;
        }

        System.out.println(
                "\n----------------------------------------------"
        );

        System.out.println(
                "TOTAL ESTIMATED TIME: "
                        + totalHours
                        + " HOURS"
        );

        System.out.println(
                "----------------------------------------------"
        );

        System.out.println(
                "\nAlgorithm Used:"
        );

        System.out.println(
                "✓ DFS - prerequisite discovery"
        );

        System.out.println(
                "✓ Graph - skill dependencies"
        );

        System.out.println(
                "✓ Topological Sort - learning order"
        );
    }

    // =====================================================
    // PRIORITY RECOMMENDATION
    // =====================================================

    public void recommend(
            String target,
            int availableHours) {

        target = target.toLowerCase();

        ArrayList<String> path =
                graph.topologicalSort(target);

        PriorityQueue<SkillScore> heap =
                new PriorityQueue<>(
                        (a, b) ->
                                Integer.compare(
                                        b.score,
                                        a.score
                                )
                );

        for (String skillName : path) {

            Skill skill =
                    skills.get(skillName);

            if (skill == null)
                continue;

            int score =
                    skill.importance * 10
                            + skill.difficulty * 5
                            - skill.hours;

            heap.add(
                    new SkillScore(
                            skill,
                            score
                    )
            );
        }

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "        HIGH PRIORITY SKILLS"
        );

        System.out.println(
                "=============================================="
        );

        int remaining =
                availableHours;

        while (!heap.isEmpty()
                && remaining > 0) {

            SkillScore item =
                    heap.poll();

            if (item.skill.hours
                    <= remaining) {

                System.out.printf(
                        "→ %-25s %2dh | Priority: %d%n",
                        item.skill.name,
                        item.skill.hours,
                        item.score
                );

                remaining -=
                        item.skill.hours;
            }
        }

        System.out.println(
                "\nRemaining study time: "
                        + remaining +
                        " hours"
        );

        System.out.println(
                "\nAlgorithm: Priority Queue / Max Heap"
        );
    }

    static class SkillScore {

        Skill skill;

        int score;

        SkillScore(
                Skill skill,
                int score) {

            this.skill = skill;
            this.score = score;
        }
    }
}
