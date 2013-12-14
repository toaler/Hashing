package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class SDBMHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash = 0;

		for (int i = 0; i < key.length(); i++) {
			hash = key.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}

		return hash;
	}
}
