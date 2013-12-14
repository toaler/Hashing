package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

/**
 *  Iteratively XOR's the previously computed hash with the next integral value.
 */
@Component
public class XorHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash = 0;
		
		for (int i = 0 ; i < key.length() ; i++) {
			hash ^= key.charAt(i);
		}
		return hash;
	}
}
