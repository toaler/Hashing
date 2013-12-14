package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class ShiftAddXorHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash = 0;

		for (int i = 0; i < key.length(); i++) {
			hash = (( hash << 5 ) + ( hash >> 2 )) + key.charAt(i);
		}

		return hash;
	}
}