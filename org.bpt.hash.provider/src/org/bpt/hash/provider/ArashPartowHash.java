package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class ArashPartowHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash = 0xAAAAAAAA;

		for (int i = 0; i < key.length(); i++) {
			if ((i & 1) == 0) {
				hash ^= ((hash << 7) ^ key.charAt(i) * (hash >> 3));
			} else {
				hash ^= (~((hash << 11) + key.charAt(i) ^ (hash >> 5)));
			}
		}

		return hash;
	}
}