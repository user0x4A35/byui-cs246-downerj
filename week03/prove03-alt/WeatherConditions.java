import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class WeatherConditions {
    
    private String id;
    private String name;
    @SerializedName("main")
    private Map<String, Float> measurements;

    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Map<String, Float> getMeasurements() {
        return measurements;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setMeasurements(Map<String, Float> measurements) {
        this.measurements = measurements;
    }
    
    public void print() {
        System.out.printf("ID: %s%nName: %s%n", id, name);
        for (String key : measurements.keySet()) {
            Float measurement = measurements.get(key);
            System.out.printf("%s: %.2f%n", key, measurement); 
        }
    }
}