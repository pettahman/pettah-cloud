package com.jackson.reader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

/**
 * CsvReader class uses OpenCsv to read from csv file and returns the data
 * contained.
 * 
 * @author Peter Jackson
 *
 */
public class CsvReader {

	/**
	 * Read method that reads from a csv file from the location passed in as a parameter.
	 * The class parameter MUST be an exact representation of the data contained in the
	 * csv file. 
	 * 
	 * @param fileLocation	The location of the file to be read.
	 * @param clazz			The class object the represents the data within the csv file.
	 * @return data 		The data contained in the csv file.
	 */
	public Object read(String fileLocation, Class<?> clazz) {
		CSVReader reader = null;
		List<Map<String, String>> data = new ArrayList<>();
		Map<String, String> args;
		Map<String, String> methods = new HashMap<>();
		try {
			Object obj = new Object();
			obj = clazz.newInstance();

			ArrayList<String> objectFields = new ArrayList<String>();
			for (Field field : obj.getClass().getDeclaredFields()) {
				String fieldName = field.getName().toLowerCase();
				objectFields.add(fieldName);
			}

			reader = new CSVReader(new FileReader(fileLocation));
			String[] line;
			boolean isLineOne = true;
			while ((line = reader.readNext()) != null) {
				if (isLineOne) {
					for (int i = 0; i < objectFields.size(); i++) {
						methods.put("method" + i, line[i]);
					}
					isLineOne = false;
				} else {
					args = new HashMap<>();
					for (int i = 0; i < objectFields.size(); i++) {
						args.put(methods.get("method" + i), line[i]);
					}
					data.add(args);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return data;
	}
}
