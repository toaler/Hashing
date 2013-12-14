package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class ElfHash extends BaseHash implements Hash {
	@Override
	public int hash(String key) {
	  int hash = 0, x = 0;

	  for(int i = 0; i < key.length(); i++) {
	     hash = (hash << 4) + key.charAt(i);

	     if((x = hash & 0xF0000000) != 0) {
	         hash ^= (x >> 24);
	     }
	     hash &= ~x;
	  }
	  
	  return hash;
	}
}