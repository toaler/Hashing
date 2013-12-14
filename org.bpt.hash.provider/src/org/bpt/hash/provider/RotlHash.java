package org.bpt.hash.provider;

import org.bpt.bittools.api.Basic;
import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class RotlHash extends BaseHash implements Hash {
	private Basic basic;
	
	@Reference
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	
	@Override
	public int hash(final String key) {
		int hash = 0;
		
		for (int i = 0 ; i < key.length() ; i++) {
			hash = basic.rotl(key.charAt(i), 28) ^ key.charAt(i);
		}
		return hash;
	}
}