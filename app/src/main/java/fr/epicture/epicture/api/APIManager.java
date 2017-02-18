package fr.epicture.epicture.api;

import java.util.ArrayList;
import java.util.List;

public class APIManager {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    /*private static final Map<APIType, API> apiByType = new HashMap<>();

    static
    {
        apiByType.put(APIType.IMGUR, new Imgur());
        apiByType.put(APIType.FLIKR, new Flikr());
    }*/

    private static final List<API> APIs = new ArrayList<>();
    private static API selectedAPI = null;

    // ========================================================================
    // STATIC METHODS
    // ========================================================================

    /*public static Map<APIType, API> getApiByType()
    {
        return Collections.unmodifiableMap(apiByType);
    }

    public static API getApi(APIType apiType)
    {
        return apiByType.get(apiType);
    }*/

    public static void addAPI(API api) {
        APIs.add(api);
    }
    public static List<API> getAPIList() {
        return APIs;
    }
    public static API getAPIByIndex(int idx) {
        return APIs.get(idx);
    }

    public static void setSelectedAPI(API api) {
        selectedAPI = api;
    }
    public static API getSelectedAPI() {
        return selectedAPI;
    }
}
