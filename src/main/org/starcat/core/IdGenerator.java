package org.starcat.core;

import java.util.HashMap;

/*
 * Any StarcatObject can generate an id using this generator class.  If 
 * an id has not been set prior to retrieval, one will be set at 
 * retrieval time.
 */
public class IdGenerator {
	// --------------------------------------------------------------------------
	// Private Data
	// --------------------------------------------------------------------------

	private static final int NUM = 1;
	private static final int CLASS = 2;

	/*
	 * Maps a class name to a unique incremental id object. In this
	 * implementation, it is the non package-qualified class name, a hyphen,
	 * then a unique integer
	 */
	private HashMap<Class<?>, Integer> classToNextIntMap = new HashMap<Class<?>, Integer>();
	private HashMap<Object, Integer> objToIdMap = new HashMap<Object, Integer>();
	private int selectedOpts = NUM;

	// --------------------------------------------------------------------------
	// Constructor
	// --------------------------------------------------------------------------

	public IdGenerator() {
	}

	public IdGenerator(int options) {
		classToNextIntMap = new HashMap<Class<?>, Integer>();
		objToIdMap = new HashMap<Object, Integer>();
		selectedOpts = options;
		if (selectedOpts < NUM || selectedOpts > (NUM | CLASS)) {
			throw new IllegalArgumentException(
					"arg must be NUM, CLASS, or NUM | CLASS; see static options");
		}
	}
	
	// -------------------------------------------------------------------------
	// Public Method
	// -------------------------------------------------------------------------

	public Object getId(Object obj) {
		Object id = objToIdMap.get(obj);
		if (id == null) {
			setIdentifier(obj);
			id = objToIdMap.get(obj);
		}
		switch (selectedOpts) {
		case (NUM):
			return id;
		case (CLASS):
			return obj.getClass().getSimpleName();
		case (CLASS | NUM):
			return obj.getClass().getSimpleName() + "-" + id;
		default:
			throw new IllegalArgumentException(
					"Identifier options out of range,"
							+ " must be NUM, CLASS, or NUM | CLASS");
		}
	}

	public void setIdentifier(Object obj) {
		Integer id;
		if (classToNextIntMap.containsKey(obj.getClass())) {
			id = classToNextIntMap.get(obj.getClass());
		} else {
			id = new Integer(0);
		}
		objToIdMap.put(obj, id);
		id = new Integer(id.intValue() + 1);
		classToNextIntMap.put(obj.getClass(), id);
	}

}
