package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class JustinSobelHash extends BaseHash implements Hash {
	int INITIAL_VALUE = 1315423911;
	
	@Override
	public int hash(final String key) {
		int hash = INITIAL_VALUE;

		for (int i = 0; i < key.length(); i++) {
			hash ^= ((hash << 5) + key.charAt(i) + (hash >> 2));
		}

		return hash;
	}
}