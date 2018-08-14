import allureDTO.AllureModelDTO;
import allureDTO.LabelsDTO;
import alluremapping.AllureModelMapping;
import alluremodel.AllureModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

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
        List<File> files = Main.getJsonFilesFromFolder();
        List<AllureModel> jsonModelList = Main.transferJsonToPojo(files);
        List<AllureModelDTO> dtoModelList = transferPojoToDto(jsonModelList);
        List<LabelsDTO> filterLabelByName = filterLabelByName(dtoModelList);
    }

    private static List<File> getJsonFilesFromFolder() throws IOException {
        return Files.walk(Paths.get("src/main/resources/json"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    private static List<AllureModel> transferJsonToPojo(List<File> files) throws FileNotFoundException {
        List<AllureModel> jsonModelList = new ArrayList<>();
        Gson gson = new Gson();
        Type REVIEW_TYPE = new TypeToken<AllureModel>() {
        }.getType();


        for (File file : files) {
            if (file.getName().contains(".json")) {
                JsonReader reader = new JsonReader(new FileReader(file));
                AllureModel data = gson.fromJson(reader, REVIEW_TYPE);
                jsonModelList.add(data);
            }
        }
        return jsonModelList;
    }

    private static List<AllureModelDTO> transferPojoToDto(List<AllureModel> jsonModelList) {
        return jsonModelList
                .stream()
                .map(AllureModelMapping::fromJson)
                .collect(Collectors.toList());
    }

    private static List<LabelsDTO> filterLabelByName(List<AllureModelDTO> jsonModelList) {
        return jsonModelList
                .stream()
                .map(i -> i.getLabels()
                        .stream()
                        .filter(k -> !k.getName().equals("suite"))
                        .filter(f -> !f.getName().equals("package"))
                        .collect(Collectors.toList()))
                .findAny().orElse(null);
    }
}
