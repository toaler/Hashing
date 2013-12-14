package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class RobertSedgwicksHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int b = 378551;
		int a = 63689;
		int hash = 0;

		for (int i = 0; i < key.length(); i++) {
			hash = hash * a + key.charAt(i);
			a = a * b;
		}

		return hash;
	}
}