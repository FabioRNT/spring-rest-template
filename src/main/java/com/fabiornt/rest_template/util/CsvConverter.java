package com.fabiornt.rest_template.util;

import java.io.StringWriter;
import java.util.List;

import com.fabiornt.rest_template.domain.model.UserModel;
import com.opencsv.CSVWriter;

/**
 * Utility class for converting data to CSV format using OpenCSV.
 */
public class CsvConverter {

    /**
     * Converts a list of UserModel objects to CSV format.
     *
     * @param users List of UserModel objects to convert
     * @return CSV formatted string
     */
    public static String toCsv(List<UserModel> users) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter)) {

            // Write header
            String[] header = {"id", "username", "email"};
            csvWriter.writeNext(header);

            // Write data rows
            for (UserModel user : users) {
                String[] row = {
                    String.valueOf(user.getId()),
                    user.getUsername(),
                    user.getEmail()
                };
                csvWriter.writeNext(row);
            }

            csvWriter.flush();
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV", e);
        }
    }
}
