package fr.epicture.epicture.api;

import java.util.Map;

@FunctionalInterface
public interface AccountInstantiate {
    APIAccount instantiate(Map<String, String> params) throws InstantiationException;
}
