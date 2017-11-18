package com.jackson.reader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * COBuilder class builds a java object with the given data.
 * 
 * @author Peter Jackson
 *
 */
public class COBuilder {

	/**
	 * Build method takes a collection of raw data and builds a collection of java objects
	 * specified by the passed in class.
	 * 
	 * @param clazz			The class object to built.
	 * @param data			The data collection used to build the object.
	 * @return returnObjs	The collection of built java objects.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object build(Class<?> clazz, Collection<Map<String, String>> data) {

		List returnObjs = null;
		try {
			returnObjs = new ArrayList(data.size());
			for (Map<String, String> dataMap : data) {
				Object newInstance = clazz.newInstance();
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					String methodName = "set" + entry.getKey().replaceAll("\\s+", "");
					for (Method method : newInstance.getClass().getMethods()) {
						if (method.getName().equalsIgnoreCase(methodName) && method.getModifiers() == Modifier.PUBLIC) {
							Object arg = entry.getValue();
							Class<?> paramType = method.getParameterTypes()[0];
							if (paramType.isAssignableFrom(Date.class)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								arg = dateFormat.parse((String) arg);
							} else if (paramType.isAssignableFrom(Double.class)) {
								arg = Double.valueOf((String) arg);
							} else if (paramType.isAssignableFrom(Integer.class)) {
								arg = Integer.valueOf((String) arg);
							} else if (paramType.isAssignableFrom(Boolean.class)) {
								arg = Boolean.valueOf((String) arg);
							}
							method.invoke(newInstance, arg);
						}
					}
				}
				returnObjs.add(newInstance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnObjs;
	}
}
