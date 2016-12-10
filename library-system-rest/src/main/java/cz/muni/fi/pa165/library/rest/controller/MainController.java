package cz.muni.fi.pa165.library.rest.controller;

import cz.muni.fi.pa165.library.rest.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Main controller for the REST API.
 *
 * @author Dávid Lupták
 * @version 10.12.2016
 */
@RestController
public class MainController {

    final static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    /**
     * The main entry point of the REST API.
     * Provides access to the all available resources with links to the resource URIs.
     *
     * @return resources URIs
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Map<String, String> getResources() {
        LOGGER.debug("getResources()");

        Map<String, String> resourcesMap = new HashMap<>();

        resourcesMap.put("books_uri", ApiUris.ROOT_URI_BOOKS);

        return Collections.unmodifiableMap(resourcesMap);
    }
}
