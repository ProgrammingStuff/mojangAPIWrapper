package mojangapi;

import com.google.gson.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStreamReader;

public class Wrapper {

    private static JsonElement getData(String url) {
        JsonElement result;
        try {
            // Building a client that connects to the input URL, gets the data and returns it as a JsonElement
            CloseableHttpClient client = HttpClientBuilder.create().setUserAgent("MojangWrapper/1.0").build();
            HttpGet request = new HttpGet(url);
            JsonParser parser = new JsonParser();
            CloseableHttpResponse response = client.execute(request);
            result = parser.parse(new InputStreamReader(response.getEntity().getContent()));

            response.close();
            client.close();
        } catch (IOException e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    public static JsonArray getStatus() {
        // Get the status of the Mojang services
        JsonArray result;
        try {
            result = getData("https://status.mojang.com/check").getAsJsonArray();
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static String getPlayerUUID(String playerName) {
        // Get a players UUID from their name
        String result;
        try {
            JsonObject data = getData("https://api.mojang.com/users/profiles/minecraft/" + playerName).getAsJsonObject();
            result = data.get("id").getAsString();
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static String getPlayerUUID(String playerName, long timestamp) {
        // Get a players UUID from their name at a timestamp (UNIX time without milliseconds)
        String result;
        try {
            JsonObject data = getData("https://api.mojang.com/users/profiles/minecraft/" + playerName + "?at=" + timestamp).getAsJsonObject();
            result = data.get("id").getAsString();
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static String getPlayerName(String playerUUID) {
        // Get a players Name from their UUID
        String result;
        try {
            JsonArray data = getData("https://api.mojang.com/user/profiles/" + playerUUID + "/names").getAsJsonArray();
            JsonObject currentName = data.get(data.size() - 1).getAsJsonObject();
            result = currentName.get("name").getAsString();
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }
    public static String getPlayerName(String playerUUID, long timestamp) {
        // Get a players Name from their UUID at a timestamp (UNIX time without milliseconds)
        timestamp = timestamp *1000;
        String result;
        try {
            JsonArray data = getData("https://api.mojang.com/user/profiles/" + playerUUID + "/names").getAsJsonArray();
            result = data.get(0).getAsJsonObject().get("name").getAsString();
            for (int i = data.size()-1; i>0; i--) {
                long changedToAt = data.get(i).getAsJsonObject().get("changedToAt").getAsLong();
                if (changedToAt <= timestamp) {
                    result = data.get(i).getAsJsonObject().get("name").getAsString();
                    break;
                }
            }
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }


    public static JsonArray getNameHistory(String playerUUID) {
        // Get the name history from a players uuid, returned as JsonArray
        JsonArray result;
        try {
            result = getData("https://api.mojang.com/user/profiles/" + playerUUID + "/names").getAsJsonArray();
        } catch (NullPointerException e) {
            result = null;
        }
        return result;
    }
}