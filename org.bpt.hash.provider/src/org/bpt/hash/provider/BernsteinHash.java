package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class BernsteinHash extends BaseHash implements Hash {
	private final int seed;
	
	public BernsteinHash() {
		this(HashUtil.SEED_INT);
	}

	public BernsteinHash(final int seed) {
		this.seed = seed;
	}

	@Override
	public int hash(final String key) {
		int hash = seed;
		for (int i = 0 ; i < key.length(); i++) {
			hash = 33 * hash + key.charAt(i);
		}
		return hash;
	}
}