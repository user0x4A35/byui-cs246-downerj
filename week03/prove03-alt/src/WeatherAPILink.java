import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

public class WeatherAPILink {
    
    private String keyFileName;
    private boolean debugging;
    private boolean unsecure;
    public static final Scanner in = new Scanner(System.in);
    
    public WeatherAPILink() {
        keyFileName = null;
        debugging = false;
        unsecure = false;
    }
    
    public void setKeyFileName(String keyFileName) {
        this.keyFileName = keyFileName;
    }
    
    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }
    
    public void setUnsecure(boolean unsecure) {
        this.unsecure = unsecure;
    }
    
    private String loadKey() {
        String key = null;
        File file = new File(keyFileName);
        
        try (Scanner fin = new Scanner(file)) {
            key = fin.nextLine();
            fin.close();
        } catch (FileNotFoundException fnfe) {
            System.err.printf("File Error: %s%n", fnfe.getMessage());
        }
        
        return key;
    }
    
    private String promptCity() {
        System.out.print("Please enter the city name: ");
        String city = null;
        try {
            while (city == null) {
                city = in.nextLine().trim();
                if (city == null || city.length() == 0) {
                    System.out.print("Please enter a name: ");
                    city = null;
                }
            }
        } catch (NoSuchElementException nsee) {
            System.err.println();
            city = null;
        }
        
        return city;
    }
    
    private String getHTTPResource(String protocol, String host, String path, String charset) {
        String responseBody = null;
        
        try {
            URL url = new URL(protocol, host, path);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            Scanner urlin = new Scanner(connection.getInputStream());
            responseBody = urlin.useDelimiter("\\A").next();
            urlin.close();
        } catch (MalformedURLException murle) {
            System.err.printf("URL Error: %s%n", murle.getMessage());
        } catch (IOException ioe) {
            System.err.printf("I/O Error: %s%n", ioe.getMessage());
        } catch (NoSuchElementException nsee) {
            System.err.printf("Read Error: %s%n", nsee.getMessage());
        }
        
        return responseBody;
    }
    
    private String constructURL(String key, String city, String charset) {
        String version = "2.5";
        String param1_name = "q";
        String param2_name = "apiKey";
        String filePath = String.format("data/%s/weather", version);
        String query = null;
        
        try {
            query = String.format("%s=%s&%s=%s",
                param1_name,
                URLEncoder.encode(city, charset),
                param2_name,
                URLEncoder.encode(key, charset)
            );
        } catch (UnsupportedEncodingException uee) {
            System.err.printf("Encoding Error: %s%n", uee.getMessage());
            return null;
        }
        String fullPath = String.format("/%s?%s", filePath, query);
        
        return fullPath;
    }
    
    private WeatherConditions parseWeatherConditions(String json) {
        WeatherConditions weather = null;
        
        Gson gson = new Gson();
        try {
            weather = gson.fromJson(json, WeatherConditions.class);
        } catch (JsonParseException jsonpe) {
            System.err.printf("JSON Error: %s%n", jsonpe.getMessage());
        }
        
        return weather;
    }
    
    public boolean run() {
        String key = loadKey();
        if (key == null) {
            return false;
        }
        
        String city = promptCity();
        if (city == null) {
            return false;
        }
        
        String charset = "UTF-8";
        String path = constructURL(key, city, charset);
        if (path == null) {
            return false;
        }
        
        String protocol = "https";
        String host = "api.openweathermap.org";
        String response = getHTTPResource(protocol, host, path, charset);
        if (response == null) {
            return false;
        }
        if (debugging) {
            if (unsecure) {
                System.err.printf("%s://%s%s%n%n", protocol, host, path);
            }
            
            System.err.printf("%s%n%n", response);
        }
        
        WeatherConditions weather = parseWeatherConditions(response);
        if (weather == null) {
            return false;
        }
        
        weather.print();
        
        return true;
    }
}