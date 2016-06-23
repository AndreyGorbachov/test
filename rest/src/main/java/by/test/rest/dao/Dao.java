package by.test.rest.dao;

import java.io.Serializable;

/**
 * Parent interface for DAO
 * 
 * @author Andrey Gorbachov
 *
 * @param <T>
 * @param <K>
 */

public interface Dao<T extends Serializable, K extends Serializable> {

	/**
	 * Create instance
	 * 
	 * @param newInstance
	 */
	void create(T newInstance);

	/**
	 * Read instance by key
	 * 
	 * @param key
	 * @return instance
	 */
	T read(K key);

	/**
	 * Update instance
	 * 
	 * @param instance
	 */
	void update(T instance);

	/**
	 * Delete instance by key
	 * 
	 * @param key
	 */
	void delete(K key);

}
