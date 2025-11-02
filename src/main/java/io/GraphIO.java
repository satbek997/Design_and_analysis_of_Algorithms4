package io;

import com.google.gson.*;
import common.Graph;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;

public class GraphIO {
    public static class Loaded {
        public Graph g;
        public Map<String,Integer> idByName = new HashMap<>();
        public java.util.List<String> names = new ArrayList<>();
        public Integer sourceCompMaybe = null; // опционально
    }

    public static Loaded loadJson(String path) throws IOException {
        String text = Files.readString(Path.of(path));
        JsonObject root = JsonParser.parseString(text).getAsJsonObject();
        Loaded L = new Loaded();

        // Вариант 1: nodes/from
        if (root.has("nodes")) {
            var namesArr = root.getAsJsonArray("nodes");
            for (int i=0;i<namesArr.size();i++){
                String name = namesArr.get(i).getAsString();
                L.names.add(name); L.idByName.put(name,i);
            }
            Graph g = new Graph(L.names.size());
            for (JsonElement e : root.getAsJsonArray("edges")) {
                JsonObject o = e.getAsJsonObject();
                int u = L.idByName.get(o.get("from").getAsString());
                int v = L.idByName.get(o.get("to").getAsString());
                double w = o.has("w") ? o.get("w").getAsDouble() : 1.0;
                g.addEdge(u,v,w);
            }
            L.g = g;
            return L;
        }

        // Вариант 2: n/u/v
        if (root.has("n")) {
            int n = root.get("n").getAsInt();
            Graph g = new Graph(n);
            for (int i=0;i<n;i++){ String name = String.valueOf(i); L.names.add(name); L.idByName.put(name,i); }
            for (JsonElement e : root.getAsJsonArray("edges")) {
                JsonObject o = e.getAsJsonObject();
                int u = o.get("u").getAsInt();
                int v = o.get("v").getAsInt();
                double w = o.has("w") ? o.get("w").getAsDouble() : 1.0;
                g.addEdge(u,v,w);
            }
            L.g = g;
            if (root.has("source")) L.sourceCompMaybe = root.get("source").getAsInt();
            return L;
        }

        throw new IllegalArgumentException("Unsupported JSON format");
    }
}
