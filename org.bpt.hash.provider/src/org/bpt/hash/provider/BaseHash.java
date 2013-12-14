package org.bpt.hash.provider;

public abstract class BaseHash {
	protected int get8bits(final String key, final int index)  {
		return (int)key.charAt(index);
	}

	protected int get16bits(final String key, final int index) {
		return (int)(key.charAt(index) | (key.charAt(index + 1) << 8));
	}
	
	protected int get24bits(final String key, final int index) {
		return (int)(key.charAt(index) | (key.charAt(index + 1) << 8)  | (key.charAt(index + 2) << 16));
	}
	
	protected int get32bits(final String key, final int index) {
		return (int)(key.charAt(index) | (key.charAt(index + 1) << 8)  | (key.charAt(index + 2) << 16)  | (key.charAt(index + 3) << 24));
	}
}