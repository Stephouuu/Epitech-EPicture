package fr.epicture.epicture.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fr.epicture.epicture.api.flikr.Flikr;
import fr.epicture.epicture.api.imgur.Imgur;

public class APIManager {

    // ========================================================================
    // STATIC FIELDS
    // ========================================================================

    private static final Map<APIType, API> apiByType = new HashMap<>();

    static
    {
        apiByType.put(APIType.IMGUR, new Imgur());
        apiByType.put(APIType.FLIKR, new Flikr());
    }

    // ========================================================================
    // STATIC METHODS
    // ========================================================================

    public static Map<APIType, API> getApiByType()
    {
        return Collections.unmodifiableMap(apiByType);
    }

    public static API getApi(APIType apiType)
    {
        return apiByType.get(apiType);
    }
}
