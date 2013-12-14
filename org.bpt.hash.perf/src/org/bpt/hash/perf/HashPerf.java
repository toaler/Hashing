package org.bpt.hash.perf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bpt.dictionary.api.DictionaryCatalog;
import org.bpt.hash.api.Hash;

import com.google.caliper.runner.CaliperMain;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component
public class HashPerf {
	Map<String, Hash> hashers = new ConcurrentHashMap<String, Hash>();
	private DictionaryCatalog catalog;
	
	@Reference
	public void addCatalog(DictionaryCatalog catalog) {
		this.catalog = catalog;
	}
	
	public void removeCatalog(DictionaryCatalog catalog) {
		catalog = null;
	}
	
	@Reference(multiple=true, dynamic=true)
	public void addHasher(org.bpt.hash.api.Hash hash) {
		hashers.put(hash.getClass().getSimpleName(), hash);
	}
	
	public void removeHasher(DictionaryCatalog catalog) {
		hashers.remove(catalog);
	}
	
	@Activate
	public void activate() {
		log(catalog.size() + " dictionaries registered.");
		log(hashers.size() + " hash functions registered.");

		log("Starting Caliper");
		CaliperMain.main(HashPerf.class, null);
		log("Finshing Caliper");
	}
	
	private void log(final String s) {
		System.out.println(String.format("%s : %s", this.getClass().getSimpleName(), s));
	}
	
//	public static void main(String args[]) {
//		CaliperMain.main(HashPerf.class, args);
//	}
//
//	private HashStrategy strategy;
//
//	@BeforeExperiment void setUp() throws Exception {
//		loadHashInstance();
//	}
//	
//	@Param({ "HashAdditive", "HashArashPartow", "HashBernstein",
//			"HashBrianKernighanDennisRitchie", "HashCrc", "HashDJBernstein",
//			"HashDonaldKnuth", "HashElf", "HashFnv1a", "HashJenkinsLookup3",
//			"HashJenkinsOneAtATimeHash", "HashJustinSobel",
//			"HashMultiplicative", "HashMurmur3_32Bit", "HashPeterJWeinberger",
//			"HashRobertSedgwicks", "HashRotl", "HashShiftAddXor", "HashSDBM",
//			"HashSuperFast", "HashXor" })
//	private String strategyParameter;
//
//	private void loadHashInstance() {
//		try {
////			strategy = HashRegistry.getInstance().getHash(strategyParameter);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
	
	
	//  May need to dup this out to avoid megamorphic CHA overhead.
//	private int hashTest(int reps, final ImmutableList<String> dictionary, HashStrategy hash) {
//		int dummy = 0;
//		
//		try {
//			for (int j = 0; j < reps; j++) {
//				dummy += hash.hash(dictionary.get(j % dictionary.size()));
//			}
//		} catch (Exception x) {
//			x.printStackTrace();
//			System.exit(1);
//		}
//		
//		return dummy;
//	}
}