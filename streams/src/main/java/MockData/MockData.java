package MockData;

import com.google.common.io.Resources;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.Car;
import model.Person;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockData {
    public static List<Person> getPeople() throws IOException {
        InputStream inputStream = Resources.getResource("people.json").openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        
        Type wrapperType = new TypeToken<PeopleWrapper>() {}.getType();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
                        return LocalDate.parse(json.getAsString());
                    }
                })
                .registerTypeAdapter(Car.class, new JsonDeserializer<Car>() {
                    @Override
                    public Car deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                        JsonObject jsonObject = json.getAsJsonObject();


                        String brand = jsonObject.has("brand") ? jsonObject.get("brand").getAsString() : null;
                        String model = jsonObject.has("model") ? jsonObject.get("model").getAsString() : null;
                        int year = jsonObject.has("year") ? jsonObject.get("year").getAsInt() : 0;
                        String licensePlate = jsonObject.has("licensePlate") ? jsonObject.get("licensePlate").getAsString() : null;

    return new Car(UUID.randomUUID(), brand, model, "Unknown", year, 0.0, licensePlate,
                                Car.FuelType.GASOLINE, 0);
                    }
                })
                .create();



        PeopleWrapper wrapper = gson.fromJson(json, wrapperType);


        return wrapper.people;
    }


    public static List<Car> getCars() throws IOException {
        InputStream inputStream = Resources.getResource("cars.json").openStream();
        String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Type listType = new TypeToken<ArrayList<Car>>() {
        }.getType();
        return new Gson().fromJson(json, listType);
    }

    private static Gson createGsonWithLocalDateAdapter() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) {
                        return LocalDate.parse(json.getAsString());
                    }
                })
                .create();
    }

    private static class PeopleWrapper {
        List<Person> people;
    }
}
