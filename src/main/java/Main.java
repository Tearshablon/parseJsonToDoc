import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.AllureModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<File> files = Main.getFilesFromFolder();
        List<AllureModel> jsonStructureList = Main.transferJsonToPojo(files);
    }


    private static List<File> getFilesFromFolder() throws IOException {
        return Files.walk(Paths.get("src/main/resources/json"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }


    private static List<AllureModel> transferJsonToPojo(List<File> files) throws FileNotFoundException {
        List<AllureModel> jsonStructureList = new ArrayList<>();
        Type REVIEW_TYPE = new TypeToken<AllureModel>() {
        }.getType();

        Gson gson = new Gson();
        for (File file : files) {
            if (file.getName().contains(".json")) {
                JsonReader reader = new JsonReader(new FileReader(file));
                AllureModel data = gson.fromJson(reader, REVIEW_TYPE);
                jsonStructureList.add(data);
            }
        }
        return jsonStructureList;
    }

}
