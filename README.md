# mojangAPIWrapper
A java wrapper for the Mojang API
## Getting Started
To download this API, just download the jar. To use it, you need to have a Java IDE. first, download it and install i into your IDE as a library. Then import it like this:
```
import wrapper.MojangApi;
```
## Features
This wrapper contains many of the services of the Mojang API. Here is a full list:
### API Status
This function returns the status of various Mojang services, for example "skins.minecraft.net" and "auth.mojang.com". It returns the name of the service apired with the state of the service as "green" for up, "yellow" for slow/unstable or "red" for down. The data is returned in form of a JsonArray. It will return ```null``` if it can't connect to the server. Here is an example on how to get the data:
```
JavaArray data = MojangApi.getStatus();
```
### Player UUID from player name
This function returns the UUID of a player based on their in game name. It can take just the playerName or playerName and timestamp to get the uuid at the UNIX time (without milliseconds) set as timestamp. It will return ```null``` if it can't connect to the server or if the player wasn't found at that timestamp or not found at all.
```
String playerUUID = MojangApi.getPlayerUUID(String playerName);
```
or
```
String playerUUID = MojangApi.getPlayerUUID(String playerName, long timestamp);
```
### Player name from player UUID
This function returns the in game name of a player based on their UUID. This function can also take just playerUUID or both playerUUID and timestamp to get the namee of that player at the specified UNIX time (Also wothout milliseconds). It will return ```null``` if it can't connect to the server or if the player wasn't found at that timestamp or not found at all.
```
String playerName = MojangApi.getPlayerName(String playerUUID);
```
or
```
String playerName = MojangApi.getPlayerName(String playerUUID, long timestamp);
```
### Name History
This funcion gets the history of all the different names that a player has had on that account. It returns a JsonArray with all the names and the time when it was changed. If the server is down or if no player was ound it returns ```null```.
```
JsonArray data = MojangApi.getNameHistory(String playerUUID);
```
## Examples
Here is an example of a complete file with all of the functions used:
```
import com.google.gson.JsonArray;
import wrapper.MojangApi;

public class main {
    public static void main(String[] args){
        JsonArray status = MojangApi.getStatus();

        String uuidAtTime = MojangApi.getPlayerUUID("playerName", UNIXTime);

        String uuid = MojangApi.getPlayerUUID("playerName");

        String name = MojangApi.getPlayerName("yourUUID");

        String nameAtTime = MojangApi.getPlayerName("yourUUID", UNIXTime);
        
        JsonArray data = MojangApi.getNameHistory("yourUUID");

    }
}
```
