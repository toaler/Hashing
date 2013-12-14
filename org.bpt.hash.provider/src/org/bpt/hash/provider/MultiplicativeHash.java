package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class MultiplicativeHash extends BaseHash implements Hash {
	private final int initialValue;
	private final int multiplier;
	
	public MultiplicativeHash () {
		this(37, 37);
	}
	
	public MultiplicativeHash(final int initialValue, final int multiplier) {
		this.initialValue = initialValue;
		this.multiplier = multiplier;
	}

	@Override
	public int hash(String key) {
		int hash, i;
		
		for (hash = initialValue, i = 0; i < key.length() ; ++i) {
			hash = (multiplier * hash) + (int)key.charAt(i);
		}
		
		return hash ;
	}
}