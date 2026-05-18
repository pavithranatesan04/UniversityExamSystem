
package university;

public class Assessment {
    private final String id;
    private String name;
    private double weight;
    private double maxMarks;

    public Assessment(String id, String name, double weight, double maxMarks) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.maxMarks = maxMarks;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getWeight() { return weight; }
    public double getMaxMarks() { return maxMarks; }

    @Override
    public String toString() {
        return id + " - " + name + " (weight=" + weight + "%)";
    }
}

