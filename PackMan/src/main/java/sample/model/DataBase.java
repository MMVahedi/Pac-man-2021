package sample.model;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss");

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDateTime));
    }
}

class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(),
                DateTimeFormatter.ofPattern("d::MMM::uuuu HH::mm::ss").withLocale(Locale.ENGLISH));
    }
}

public class DataBase {
    private static DataBase instance;
    private final String USER_DATABASE_PATH = "src/main/resources/sample/users/";

    private DataBase() {
    }

    public static DataBase getInstance() {
        if (instance == null) instance = new DataBase();
        return instance;
    }

    public void updateAllUsersArrayList() {
        ArrayList<User> allUsers = new ArrayList<>();
        File[] listOfFiles = new File(USER_DATABASE_PATH).listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(USER_DATABASE_PATH + file.getName()));
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
                    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
                    Gson gson = gsonBuilder.setPrettyPrinting().create();
                    User user = gson.fromJson(reader, User.class);
                    allUsers.add(user);
                } catch (FileNotFoundException ignored) {
                }
            }
        }
        User.setAllUsers(allUsers);
    }

    public void addNewUser(User user) {
        try {
            File newUser = new File(pathFinder(user));
            newUser.createNewFile();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateUserInformation(User user) {
        writeToJson(user, pathFinder(user));
    }

    public void removeUser(User user) {
        new File(pathFinder(user)).delete();
    }

    private String pathFinder(Object object) {
        return USER_DATABASE_PATH + ((User) object).getUsername() + ".json";
    }

    private void writeToJson(Object object, String filePath) {
        try {
            Writer writer = new FileWriter(filePath);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
            Gson gson = gsonBuilder.setPrettyPrinting().create();
            writer.write(gson.toJson((User) object));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
