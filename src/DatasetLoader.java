import java.io.*;
import java.util.*;

public class DatasetLoader {

    private static File resolveFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            File alt = new File("../" + filePath);
            if (alt.exists()) return alt;
        }
        return file;
    }

    public static HashMap<String, Skill> loadSkills(
            String filePath) {

        HashMap<String, Skill> skills = new HashMap<>();

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(resolveFile(filePath)));

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data = line.split(",");

                if (data.length < 6)
                    continue;

                int id =
                        Integer.parseInt(data[0].trim());

                String name =
                        data[1].trim();

                String category =
                        data[2].trim();

                int difficulty =
                        Integer.parseInt(data[3].trim());

                int hours =
                        Integer.parseInt(data[4].trim());

                int importance =
                        Integer.parseInt(data[5].trim());

                Skill skill =
                        new Skill(
                                id,
                                name,
                                category,
                                difficulty,
                                hours,
                                importance
                        );

                skills.put(
                        name.toLowerCase(),
                        skill
                );
            }

            br.close();

        } catch (IOException e) {

            System.out.println(
                    "Error loading skills dataset."
            );

            e.printStackTrace();
        }

        return skills;
    }

    public static void loadPrerequisites(
            String filePath,
            Graph graph) {

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(resolveFile(filePath)));

            String line;

            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] data =
                        line.split(",");

                if (data.length < 2)
                    continue;

                String skill =
                        data[0].trim();

                String prerequisite =
                        data[1].trim();

                graph.addEdge(
                        skill,
                        prerequisite
                );
            }

            br.close();

        } catch (IOException e) {

            System.out.println(
                    "Error loading prerequisite dataset."
            );

            e.printStackTrace();
        }
    }
}
