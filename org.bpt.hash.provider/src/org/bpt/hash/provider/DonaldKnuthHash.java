package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class DonaldKnuthHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash = key.length();

		for (int i = 0; i < key.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ key.charAt(i);
		}

		return hash;
	}
}