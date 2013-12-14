package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class BrianKernighanDennisRitchieHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int seed = 131, hash = 0;
		
		for (int i = 0; i < key.length(); i++) {
			hash = (hash * seed) + key.charAt(i);
		}
		return hash;
	}
}