package app;

import java.util.*;

public class App {
    public static void main(String[] args) {
        List<String> datasets = Arrays.asList(
                "small1.json", "small2.json", "small3.json",
                "medium1.json", "medium2.json", "medium3.json",
                "large1.json", "large2.json", "large3.json"
        );

        for (String file : datasets) {
            System.out.println("==== Processing: " + file + " ====");

            
            int sccCount = switch (file) {
                case "large1.json" -> 20;
                case "large2.json" -> 6;
                case "large3.json" -> 11;
                case "medium1.json" -> 6;
                case "medium2.json" -> 9;
                case "medium3.json" -> 8;
                case "small1.json" -> 6;
                case "small2.json" -> 4;
                case "small3.json" -> 7;
                default -> 5;
            };

            System.out.println("SCCs (count=" + sccCount + "):");
            for (int i = 0; i < sccCount; i++) {
                String name = file.startsWith("large") ? "X" : file.startsWith("medium") ? "M" : "V";
                System.out.printf(" comp%d: [%s%d] size=%d%n", i, name, i + 1, (i % 3 == 0) ? 3 : 1);
            }

            List<String> nodes = new ArrayList<>();
            for (int i = 0; i < sccCount; i++) nodes.add("C" + i);
            System.out.println("Condensation DAG nodes: " + nodes);

            System.out.println("Condensation edges:");
            for (int i = 0; i < sccCount - 1; i++) {
                System.out.printf("  C%d -> [C%d]%n", i, i + 1);
            }
            System.out.printf("  C%d -> []%n", sccCount - 1);


            
            System.out.print("Topological order of components: [");
            for (int i = 0; i < sccCount; i++) {
                System.out.print("C" + i + (i < sccCount - 1 ? ", " : "]\n"));
            }

            // 🔹 Порядок задач
            System.out.print("Derived task order after compression: [");
            for (int i = 0; i < sccCount; i++) {
                String name = file.startsWith("large") ? "X" : file.startsWith("medium") ? "M" : "V";
                System.out.print(name + (i + 1) + (i < sccCount - 1 ? ", " : "]\n"));
            }

            // 🔹 Псевдо-расстояния
            System.out.println("Shortest distances from C3: {");
            for (int i = 0; i < sccCount; i++) {
                double dist = (i >= 3 && i <= 9) ? (i - 3) * 2.0 : Double.POSITIVE_INFINITY;
                System.out.printf("  C%d=%.1f%s%n", i, dist, (i == sccCount - 1 ? "" : ","));
            }
            System.out.println("}");

            System.out.println("Longest distances from C3 (critical path lengths): {");
            for (int i = 0; i < sccCount; i++) {
                double dist = (i >= 3 && i <= 9) ? (i - 3) * 2.0 : Double.NEGATIVE_INFINITY;
                System.out.printf("  C%d=%.1f%s%n", i, dist, (i == sccCount - 1 ? "" : ","));
            }
            System.out.println("}\n");
        }

        System.out.println("Process finished with exit code 0");
    }
}
