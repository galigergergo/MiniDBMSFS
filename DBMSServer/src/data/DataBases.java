package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class DataBases {
    public ArrayList<Database> Databases = new ArrayList<>();

    /**
     * Converts file to String.
     * @param filePath - name of file.
     * @return string with json text
     */
    private static String readLineByLineJava8(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            System.out.println("DATA.json not found!");
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    /**
     * Creates DataBases class from Json file.
     * De-Serializing.
     * @return DataBases big class container.
     */
    public static DataBases FromJson() {
        String filename = "DATA.json";
        String json = readLineByLineJava8(filename);

        // debuggin purposes
        // this contains the whole json text
        //System.out.println(json);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        return gson.fromJson(json, DataBases.class);
    }

    /**
     * String to file writing.
     */
    public static void WriteToFile(String fileName, String json) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".json"));
        writer.write(json);
        writer.close();
    }

    /**
     * From DataBases class, writes to Json file.
     */
    public static void ToJson(DataBases db) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();    // without this, the output would be just one line lol
        Gson gson = builder.create();

        String json = gson.toJson(db);

        String filename = "DATA";
        WriteToFile(filename, json);
    }
}
