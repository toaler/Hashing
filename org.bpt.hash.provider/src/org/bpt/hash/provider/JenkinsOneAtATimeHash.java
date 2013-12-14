package org.bpt.hash.provider;

import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;

@Component
public class JenkinsOneAtATimeHash extends BaseHash implements Hash {
	@Override
	public int hash(final String key) {
		int hash, i;
		for (hash = i = 0; i < key.length(); ++i) {
			hash += key.charAt(i);
			hash += (hash << 10);
			hash ^= (hash >> 6);
		}
		hash += (hash << 3);
		hash ^= (hash >> 11);
		hash += (hash << 15);
		return hash;
	}
}
