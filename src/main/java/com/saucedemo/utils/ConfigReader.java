package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuration reader for properties files
 */
public class ConfigReader {
    private final Properties properties;
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static final String DEFAULT_CONFIG_PATH = "src/test/resources/config.properties";
    private static final String ENV_CONFIG_FORMAT = "src/test/resources/config.%s.properties";

    /**
     * Constructor
     */
    public ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Load properties from files
     */
    private void loadProperties() {
        String environment = System.getProperty("environment", "dev");
        
        // First load default properties
        loadPropertiesFromFile(DEFAULT_CONFIG_PATH);
        
        // Then try to load environment-specific properties
        String envConfigPath = String.format(ENV_CONFIG_FORMAT, environment);
        if (Files.exists(Paths.get(envConfigPath))) {
            loadPropertiesFromFile(envConfigPath);
        }
    }

    /**
     * Load properties from file
     * @param filePath path to properties file
     */
    private void loadPropertiesFromFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            logger.warn("Properties file not found: {}", filePath);
            return;
        }
        
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            logger.info("Loaded properties from file: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to load properties from file: {}", filePath, e);
        }
    }

    /**
     * Get property value
     * @param key property key
     * @return property value or null if not found
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default
     * @param key property key
     * @param defaultValue default value if not found
     * @return property value or default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}