package org.bpt.hash.provider;

import org.bpt.bittools.api.Basic;
import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

/*
 * Murmur 3 implementation.
 */
@Component
public class Murmur3_32BitHash extends BaseHash implements Hash {
    private static final int C1 = 0xCC9E2D51;
    private static final int C2 = 0x1B873593;
    private static final int R1 = 15;
    private static final int R2 = 13;
    private static final int M = 5;
    private static final int N = 0xE6546B64;
	private static final int BYTES_PER_CHAR = 2;
	private final int seed;
	
	private Basic basic;
	
	@Reference
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	
	public Murmur3_32BitHash() {
		this(HashUtil.SEED_INT);
	}
    
	public Murmur3_32BitHash(final int seed) {
		this.seed = seed;
	}

	@Override
	public int hash(final String key) {
		int hash = seed;
		
	    for (int i = 1; i < key.length(); i += 2) {
	        int k = key.charAt(i - 1) | (key.charAt(i) << 16);
	        
	        k = mixA(k);
	        hash = mixB(hash, k);
	    }
	    
	    if ((key.length() & 1) == 1) {
	    	int r = key.charAt(key.length() - 1);
	    	r = mixA(r);
	    	hash ^= r;
	    }

        hash = mixFinal(hash, key.length() * BYTES_PER_CHAR);
	    return hash;
	}

	private int mixA(int k) {
		k *= C1; 
		k = basic.rotl(k,R1); 
		k *= C2;
		return k;
	}

	private int mixB(int hash, int k) {
		hash ^= k;
		hash = basic.rotl(hash, R2); 
		hash = hash*M+N;
		return hash;
	}

	private int mixFinal(int hash, int length) {
		hash ^= length;
		hash ^= hash >> 16;
        hash *= 0x85ebca6b;
        hash ^= hash >> 13;
        hash *= 0xc2b2ae35;
        hash ^= hash >> 16;
		return hash;
	}
}