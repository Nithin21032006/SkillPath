import java.util.*;

public class Main {

    static Scanner sc =
            new Scanner(System.in);

    public static void main(String[] args) {

        String skillsFile =
                "data/skills.csv";

        String prerequisitesFile =
                "data/prerequisites.csv";

        // ============================================
        // LOAD DATASET
        // ============================================

        HashMap<String, Skill> skills =
                DatasetLoader.loadSkills(
                        skillsFile
                );

        Graph graph =
                new Graph();

        DatasetLoader.loadPrerequisites(
                prerequisitesFile,
                graph
        );

        RoadmapGenerator generator =
                new RoadmapGenerator(
                        skills,
                        graph
                );

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "                 SKILLPATH"
        );

        System.out.println(
                "   Intelligent Career Roadmap Generator"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "\nDataset loaded successfully!"
        );

        System.out.println(
                "Skills available: "
                        + skills.size()
        );

        while (true) {

            showMenu();

            System.out.print(
                    "\nEnter choice: "
            );

            int choice =
                    readInt();

            switch (choice) {

                case 1:

                    showSkills(skills);

                    break;

                case 2:

                    generateRoadmap(
                            skills,
                            generator
                    );

                    break;

                case 3:

                    recommendSkills(
                            skills,
                            generator
                    );

                    break;

                case 4:

                    graph.display();

                    break;

                case 5:

                    performBFS(
                            skills,
                            graph
                    );

                    break;

                case 6:

                    showPrerequisites(
                            skills,
                            graph
                    );

                    break;

                case 0:

                    System.out.println(
                            "\nThank you for using SkillPath!"
                    );

                    return;

                default:

                    System.out.println(
                            "\nInvalid choice."
                    );
            }
        }
    }

    // ================================================
    // MENU
    // ================================================

    static void showMenu() {

        System.out.println(
                "\n=============================================="
        );

        System.out.println(
                "                  MENU"
        );

        System.out.println(
                "=============================================="
        );

        System.out.println(
                "1. View Available Skills"
        );

        System.out.println(
                "2. Generate Roadmap"
        );

        System.out.println(
                "3. Get Priority Recommendations"
        );

        System.out.println(
                "4. View Skill Dependency Graph"
        );

        System.out.println(
                "5. BFS Skill Analysis"
        );

        System.out.println(
                "6. View Prerequisites"
        );

        System.out.println(
                "0. Exit"
        );
    }

    // ================================================
    // SHOW SKILLS
    // ================================================

    static void showSkills(
            HashMap<String, Skill> skills) {

        System.out.println(
                "\n========== AVAILABLE SKILLS =========="
        );

        int i = 1;

        for (Skill skill :
                skills.values()) {

            System.out.printf(
                    "%2d. %-25s | %s%n",
                    i,
                    skill.name,
                    skill.category
            );

            i++;
        }
    }

    // ================================================
    // ROADMAP
    // ================================================

    static void generateRoadmap(
            HashMap<String, Skill> skills,
            RoadmapGenerator generator) {

        System.out.println(
                "\nEnter target skill:"
        );

        System.out.print("> ");

        if (!sc.hasNextLine()) return;

        String target =
                sc.nextLine().trim();

        generator.generate(target);
    }

    // ================================================
    // RECOMMENDATION
    // ================================================

    static void recommendSkills(
            HashMap<String, Skill> skills,
            RoadmapGenerator generator) {

        System.out.print(
                "\nEnter target skill: "
        );

        if (!sc.hasNextLine()) return;

        String target =
                sc.nextLine().trim();

        if (!skills.containsKey(
                target.toLowerCase())) {

            System.out.println(
                    "Skill not found."
            );

            return;
        }

        System.out.print(
                "Available study hours: "
        );

        int hours =
                readInt();

        generator.recommend(
                target,
                hours
        );
    }

    // ================================================
    // BFS
    // ================================================

    static void performBFS(
            HashMap<String, Skill> skills,
            Graph graph) {

        System.out.print(
                "\nEnter skill: "
        );

        if (!sc.hasNextLine()) return;

        String skill =
                sc.nextLine().trim();

        if (!skills.containsKey(
                skill.toLowerCase())) {

            System.out.println(
                    "Skill not found."
            );

            return;
        }

        graph.bfs(skill);
    }

    // ================================================
    // PREREQUISITES
    // ================================================

    static void showPrerequisites(
            HashMap<String, Skill> skills,
            Graph graph) {

        System.out.print(
                "\nEnter skill: "
        );

        if (!sc.hasNextLine()) return;

        String skill =
                sc.nextLine().trim();

        if (!skills.containsKey(
                skill.toLowerCase())) {

            System.out.println(
                    "Skill not found."
            );

            return;
        }

        ArrayList<String> prerequisites =
                graph.getAllPrerequisites(
                        skill
                );

        System.out.println(
                "\nPrerequisite chain:"
        );

        for (String item :
                prerequisites) {

            Skill s =
                    skills.get(item);

            if (s != null) {

                System.out.println(
                        "→ " + s.name
                );
            }
        }
    }

    // ================================================
    // INPUT
    // ================================================

    static int readInt() {

        while (true) {

            try {

                if (!sc.hasNextLine()) return 0;

                return Integer.parseInt(
                        sc.nextLine().trim()
                );

            } catch (NumberFormatException e) {

                System.out.print(
                        "Enter a valid number: "
                );

            } catch (Exception e) {

                return 0;
            }
        }
    }
}
