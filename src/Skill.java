public class Skill {

    int id;
    String name;
    String category;
    int difficulty;
    int hours;
    int importance;

    public Skill(
            int id,
            String name,
            String category,
            int difficulty,
            int hours,
            int importance) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.hours = hours;
        this.importance = importance;
    }

    @Override
    public String toString() {

        return name +
                " | Category: " + category +
                " | Difficulty: " + difficulty +
                " | Hours: " + hours +
                " | Importance: " + importance;
    }
}
