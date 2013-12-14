package org.bpt.hash.provider;

import org.junit.Assert;
import org.junit.Test;

public class BashHashTest extends BaseHash {
	@Test
	public void get8Bits() {
		Assert.assertEquals(0b01100001, get8bits("a", 0));
	}
	
	@Test
	public void get16Bits() {
		Assert.assertEquals(0b0110001001100001, get16bits("ab", 0));
	}
	
	@Test
	public void get24Bits() {
		Assert.assertEquals(0b011000110110001001100001, get24bits("abc", 0));
	}
		
	@Test
	public void get32Bits() {
		Assert.assertEquals(0b01100100011000110110001001100001, get32bits("abcd", 0));
	}
}