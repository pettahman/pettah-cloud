package com.jackson.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import com.opencsv.CSVWriter;

/**
 * CsvWriter class uses OpenCsv to write a collection of objects to a data
 * file.
 * 
 * @author Peter Jackson
 *
 */
public class CsvWriter {

	/**
	 * Write method will take a collection of objects, grab the data populated
	 * in each object, and add the data to a String array. This is then written
	 * to a file at the location that is passed in as a parameter.
	 * 
	 * @param fileLocation	The location of the file to be written to.
	 * @param objects		The collection of objects to write to the file.
	 */
	public void write(String fileLocation, Collection<?> objects) {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(fileLocation));
			String[] methodArray = new String[0];
			boolean isLineOne = true;
			for (Object object : objects) {
				String[] argArray = new String[0];
				for (Method method : object.getClass().getDeclaredMethods()) {
					if (method.getName().contains("get") && method.getModifiers() == Modifier.PUBLIC) {
						methodArray = append(methodArray, method.getName().substring(3));
						Class<?> returnType = method.getReturnType();
						if (returnType.isAssignableFrom(Date.class)) {
							Format formatter = new SimpleDateFormat("MM/dd/yyyy");
							argArray = append(argArray, formatter.format(method.invoke(object)));
						} else if (returnType.isAssignableFrom(Double.class)) {
							argArray = append(argArray, String.valueOf(method.invoke(object)));
						} else if (returnType.isAssignableFrom(Integer.class)) {
							argArray = append(argArray, String.valueOf(method.invoke(object)));
						} else if (returnType.isAssignableFrom(Boolean.class)) {
							argArray = append(argArray, String.valueOf(method.invoke(object)));
						} else if (returnType.isAssignableFrom(String.class)) {
							argArray = append(argArray, (String) method.invoke(object));
						}
					}
				}
				if (isLineOne) {
					writer.writeNext(methodArray);
					isLineOne = false;
				}
				writer.writeNext(argArray);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Static helper method used to append an element to an array.
	 * 
	 * @param arr 		An array to append an element to.
	 * @param element 	The element to append.
	 * @return arr 		The array with the newly appended element.
	 */
	static <T> T[] append(T[] arr, T element) {
		final int N = arr.length;
		arr = Arrays.copyOf(arr, N + 1);
		arr[N] = element;
		return arr;
	}
}
