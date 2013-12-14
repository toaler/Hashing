package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class PeterJWeinbergerHash extends BaseHash implements Hash {
	static final int BITS_IN_UNSIGNED_INT = 4 * 8;
    static final int THREE_QUARTERS = (BITS_IN_UNSIGNED_INT  * 3) / 4;
    static final int ONE_EIGTH = (BITS_IN_UNSIGNED_INT / 8);
    static final int HIGH_BITS = (0xFFFFFFFF) << (BITS_IN_UNSIGNED_INT - ONE_EIGTH);
    
	@Override
	public int hash(final String key) {
	    int hash = 0, test = 0;

	    for(int i = 0; i < key.length(); i++) {
	       hash = (hash << ONE_EIGTH) + key.charAt(i);

	       if((test = hash & HIGH_BITS)  != 0) {
	            hash = (( hash ^ (test >> THREE_QUARTERS)) & (~HIGH_BITS));
	       }
	    }

	    return hash;
	}
}
