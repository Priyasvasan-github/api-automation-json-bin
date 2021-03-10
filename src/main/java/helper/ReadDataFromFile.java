package helper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReadDataFromFile {

    public static String fetch(String filepath) {
        ObjectMapper objectMapper=new ObjectMapper();
        try {
            //Read JSON file
            String json = objectMapper.readValue(Files.newInputStream(Paths.get(System.getProperty("user.dir")+"/src/main/resources/testData/"+filepath),StandardOpenOption.READ), JsonNode.class).toString();
            return json;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
