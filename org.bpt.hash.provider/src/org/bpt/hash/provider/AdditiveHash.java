package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;


/**
 * 
 * Simple hash function that takes a array of integral values and adds them
 * together, finally forcing them into a specified range.
 * 
 * Typically hash functions that depend only on additive operations have poor
 * key distribution. For example the strings "dog", "god", "odg" will have the
 * same hash value.
 * 
 */

@Component
public class AdditiveHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash, i;
		
		for (hash = key.length(), i = 0; i < key.length() ; ++i) {
			hash += (int)key.charAt(i);
		}
		
		return hash;
	}
}