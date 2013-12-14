package org.bpt.hash.provider;

import org.bpt.bittools.api.Basic;
import org.bpt.hash.api.Hash;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

/**
 * 
 * @author Bob Jenkins, 2006. bob_jenkins@burtleburtle.net.
 * 
 *         You may use this code any way you wish, private, educational, or
 *         commercial. It's free. Use for hash table lookup, or anything where
 *         one collision in 2^^32 is acceptable. Do NOT use for cryptographic
 *         purposes.
 * 
 */
@Component
public class JenkinsLookup3Hash extends BaseHash implements Hash {
	private int seed;
	private Basic basic;
	
	@Reference
	public void setBasic(Basic basic) {
		this.basic = basic;
	}
	
	public JenkinsLookup3Hash() {
		this(HashUtil.SEED_INT);
	}

	/**
	 * 
	 * @param can be any 4-byte value
	 */
	public JenkinsLookup3Hash(final int seed) {
		this.seed = seed;
	}

	/*
	 * hash a variable-length key into a 32-bit value 
	 * 
	 * k : the key (the unaligned variable-length array of bytes) 
	 *
	 * Returns a 32-bit value. Every bit of the key affects every bit of the
	 * return value. Two keys differing by one or two bits will have totally
	 * different hash values.
	 * 
	 * The best hash table sizes are powers of 2. There is no need to do mod a
	 * prime (mod is sooo slow!). If you need less than 32 bits, use a bitmask.
	 * For example, if you need only 10 bits, do h = (h & hashmask(10)); In
	 * which case, the hash table should have hashsize(10) elements.
	 * 
	 * If you are hashing n strings (uint8_t **)k, do it like this: for (i=0,
	 * h=0; i<n; ++i) h = hashlittle( k[i], len[i], h);
	 * 
	 */
	
	@Override
	public int hash(final String key) {
		int length = key.length();
		int a, b, c;
		a = b = c = 0xdeadbeef + ((int) length) + seed;
		int i = 0;

		/*------ all but last block: aligned reads and affect 32 bits of (a,b,c) */
		while (length > 12) {
			a += get32bits(key, i);
			i += 4;
			b += get32bits(key, i);
			i += 4;
			c += get32bits(key, i);

			mix(a, b, c);

			length -= 12;
		}

		/*
		 * handle the last (probably partial) block
		 */

		switch (length) {
		case 12:
			c += get32bits(key, i + 2);
			b += get32bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 11:
			c += get24bits(key, i + 2);
			b += get32bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 10:
			c += get16bits(key, i + 2);
			b += get32bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 9:
			c += get8bits(key, i + 2);
			b += get32bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 8:
			b += get32bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 7:
			b += get24bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 6:
			b += get16bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 5:
			b += get8bits(key, i + 1);
			a += get32bits(key, 0);
			break;
		case 4:
			a += get32bits(key, i);
			break;
		case 3:
			a += get24bits(key, i);
			break;
		case 2:
			a += get16bits(key, i);
			break;
		case 1:
			a += key.charAt(i);
			break;
		case 0:
			return c; /* zero length strings require no mixing */
		}

		postMix(a, b, c);
		return c;

	}

	/**
	 * mix -- mix 3 32-bit values reversible.
	 * 
	 * This is reversible, so any information in (a,b,c) before mix() is still
	 * in (a,b,c) after mix().
	 * 
	 * If four pairs of (a,b,c) inputs are run through mix(), or through mix()
	 * in reverse, there are at least 32 bits of the output that are sometimes
	 * the same for one pair and different for another pair. This was tested
	 * for: pairs that differed by one bit, by two bits, in any combination of
	 * top bits of (a,b,c), or in any combination of bottom bits of (a,b,c).
	 * "differ" is defined as +, -, ^, or ~^. For + and -, I transformed the
	 * output delta to a Gray code (a^(a>>1)) so a string of 1's (as is commonly
	 * produced by subtraction) look like a single 1-bit difference. the base
	 * values were pseudorandom, all zero but one bit set, or all zero plus a
	 * counter that starts at zero.
	 * 
	 * Some k values for my "a-=c; a^=rotl(c,k); c+=b;" arrangement that satisfy
	 * this are 4 6 8 16 19 4 9 15 3 18 27 15 14 9 3 7 17 3 Well,
	 * "9 15 3 18 27 15" didn't quite get 32 bits diffing for "differ" defined
	 * as + with a one-bit base and a two-bit delta. I used
	 * http://burtleburtle.net/bob/hash/avalanche.html to choose the operations,
	 * constants, and arrangements of the variables.
	 * 
	 * This does not achieve avalanche. There are input bits of (a,b,c) that
	 * fail to affect some output bits of (a,b,c), especially of a. The most
	 * thoroughly mixed value is c, but it doesn't really even achieve avalanche
	 * in c.
	 * 
	 * This allows some parallelism. Read-after-writes are good at doubling the
	 * number of bits affected, so the goal of mixing pulls in the opposite
	 * direction as the goal of parallelism. I did what I could. Rotates seem to
	 * cost as much as shifts on every machine I could lay my hands on, and
	 * rotates are much kinder to the top and bottom bits, so I used rotates.
	 * 
	 * @param a - 32 bits
	 * @param b - 32 bits
	 * @param c - 32 bits
	 */
	private void mix(int a,int b,int c) { 
	  a -= c;  a ^= basic.rotl(c, 4);  c += b;
	  b -= a;  b ^= basic.rotl(a, 6);  a += c;
	  c -= b;  c ^= basic.rotl(b, 8);  b += a;
	  a -= c;  a ^= basic.rotl(c,16);  c += b;
	  b -= a;  b ^= basic.rotl(a,19);  a += c;
	  c -= b;  c ^= basic.rotl(b, 4);  b += a;
	}

	/**
	 * final -- final mixing of 3 32-bit values (a,b,c) into c
	 * 
	 * Pairs of (a,b,c) values differing in only a few bits will usually produce
	 * values of c that look totally different. This was tested for
	 * 
	 * - pairs that differed by one bit, by two bits, in any combination of top
	 * bits of (a,b,c), or in any combination of bottom bits of (a,b,c).
	 * 
	 * "differ" is defined as +, -, ^, or ~^. For + and -, I transformed the
	 * output delta to a Gray code (a^(a>>1)) so a string of 1's (as is commonly
	 * produced by subtraction) look like a single 1-bit difference.
	 * 
	 * the base values were pseudorandom, all zero but one bit set, or all zero
	 * plus a counter that starts at zero.
	 * 
	 * These constants passed: 14 11 25 16 4 14 24 12 14 25 16 4 14 24 and these
	 * came close: 4 8 15 26 3 22 24 10 8 15 26 3 22 24 11 8 15 26 3 22 24
	 * 
	 * @param a
	 * @param b
	 * @param c
	 */
	private void postMix(int a, int b, int c) { 
	  c ^= b; c -= basic.rotl(b,14);
	  a ^= c; a -= basic.rotl(c,11);
	  b ^= a; b -= basic.rotl(a,25);
	  c ^= b; c -= basic.rotl(b,16);
	  a ^= c; a -= basic.rotl(c,4);
	  b ^= a; b -= basic.rotl(a,14);
	  c ^= b; c -= basic.rotl(b,24);
	}

}
