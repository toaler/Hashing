package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class Fnv1aHash extends BaseHash implements Hash {
	private static final int PRIME = 37;

	private final int offsetBasis;
	private final int prime;
	
	public Fnv1aHash() {
		this(216613626, PRIME);
	}

	public Fnv1aHash(final int offsetBasis, final int prime) {
		this.offsetBasis = offsetBasis;
		this.prime = prime;
	}

	@Override
	public int hash(final String key) {
		int hash = offsetBasis;
		
		for (int i = 0; i < key.length(); i++) {
			int octect = key.charAt(i);
			hash ^= octect;
			hash *= prime;
		}
		
		return hash;
	}
}