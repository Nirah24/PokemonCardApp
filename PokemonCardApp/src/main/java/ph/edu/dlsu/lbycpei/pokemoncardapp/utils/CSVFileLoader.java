package ph.edu.dlsu.lbycpei.pokemoncardapp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileLoader {

    private int totalLines = 0;
    private int skippedLines = 0;
    private final List<String> skippedLineDetails = new ArrayList<>();

    /**
     * Static method to load CSV file from resources folder
     * @param resourcePath Path to the CSV file in resources (e.g., "data.csv" or "csv/data.csv")
     * @return Array of valid CSV lines
     */
    public static String[] loadCSVFromResources(String resourcePath) {
        CSVFileLoader loader = new CSVFileLoader();
        return loader.loadCSVFromResourcesInternal(resourcePath);
    }

    /**
     * Static method to load CSV file from file system
     * @param filePath Path to the CSV file
     * @return Array of valid CSV lines
     */
    public static String[] loadCSV(String filePath) {
        return loadCSV(new File(filePath));
    }

    /**
     * Static method to load CSV file from file system
     * @param file CSV file object
     * @return Array of valid CSV lines
     */
    public static String[] loadCSV(File file) {
        CSVFileLoader loader = new CSVFileLoader();
        return loader.loadCSVToStringArray(file);
    }

    /**
     * Internal method to load CSV from resources folder
     * @param resourcePath Path to resource file
     * @return Array of valid CSV lines
     */
    private String[] loadCSVFromResourcesInternal(String resourcePath) {
        List<String> validLines = new ArrayList<>();
        totalLines = 0;
        skippedLines = 0;
        skippedLineDetails.clear();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IOException("Resource file not found: " + resourcePath);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                totalLines++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    skippedLines++;
                    skippedLineDetails.add("Line " + totalLines + ": Empty line");
                    continue;
                }

                // Validate and clean the line
                String cleanedLine = cleanCSVLine(line);
                if (isValidCSVLine(cleanedLine)) {
                    validLines.add(cleanedLine);
                } else {
                    skippedLines++;
                    skippedLineDetails.add("Line " + totalLines + ": " + line);
                    System.out.println("Skipped malformed line " + totalLines + ": " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading resource file: " + e.getMessage());
            e.printStackTrace();
        }

        return validLines.toArray(new String[0]);
    }

    /**
     * Loads CSV file into a string array, skipping malformed lines
     * @param file The CSV file to load
     * @return Array of valid CSV lines
     */
    public String[] loadCSVToStringArray(File file) {
        List<String> validLines = new ArrayList<>();
        totalLines = 0;
        skippedLines = 0;
        skippedLineDetails.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                totalLines++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    skippedLines++;
                    skippedLineDetails.add("Line " + totalLines + ": Empty line");
                    continue;
                }

                // Validate and clean the line
                String cleanedLine = cleanCSVLine(line);
                if (isValidCSVLine(cleanedLine)) {
                    validLines.add(cleanedLine);
                } else {
                    skippedLines++;
                    skippedLineDetails.add("Line " + totalLines + ": " + line);
                    System.out.println("Skipped malformed line " + totalLines + ": " + line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return validLines.toArray(new String[0]);
    }

    /**
     * Cleans a CSV line by removing extra quotes and commas
     * @param line Raw line from CSV
     * @return Cleaned line
     */
    private String cleanCSVLine(String line) {
        // Remove leading/trailing whitespace
        line = line.trim();

        // Remove trailing comma if present
        if (line.endsWith(",")) {
            line = line.substring(0, line.length() - 1);
        }

        // Remove surrounding quotes if present
        if (line.startsWith("\"") && line.endsWith("\"")) {
            line = line.substring(1, line.length() - 1);
        }

        return line;
    }

    /**
     * Validates if a CSV line has the expected format
     * Expected format: Name,Weight,Height,Stat1,Stat2,Stat3,Type
     * @param line The line to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidCSVLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }

        // Split by comma
        String[] fields = line.split(",");

        // Check if we have exactly 7 fields
        if (fields.length != 7) {
            return false;
        }

        // Validate each field
        try {
            // Field 0: Name (should not be empty)
            if (fields[0].trim().isEmpty()) {
                return false;
            }

            // Field 1: Weight (should be parseable as double and end with 'kg')
            String weight = fields[1].trim();
            if (!weight.endsWith("kg")) {
                return false;
            }
            Double.parseDouble(weight.substring(0, weight.length() - 2));

            // Field 2: Height (should be parseable as double and end with 'm')
            String height = fields[2].trim();
            if (!height.endsWith("m")) {
                return false;
            }
            Double.parseDouble(height.substring(0, height.length() - 1));

            // Fields 3, 4, 5: Stats (should be parseable as double)
            Double.parseDouble(fields[3].trim());
            Double.parseDouble(fields[4].trim());
            Double.parseDouble(fields[5].trim());

            // Field 6: Type (should not be empty)
            if (fields[6].trim().isEmpty()) {
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Getters for statistics
    public int getTotalLines() {
        return totalLines;
    }

    public int getSkippedLines() {
        return skippedLines;
    }

    public int getValidLines() {
        return totalLines - skippedLines;
    }

    public List<String> getSkippedLineDetails() {
        return new ArrayList<>(skippedLineDetails);
    }

    public String getLoadingSummary() {
        return String.format(
                "Loaded %d valid lines, skipped %d malformed lines out of %d total lines",
                getValidLines(), skippedLines, totalLines
        );
    }
}