package cscie88a.basics;

import java.util.Collection;

public class CatAggregator {
	
	public CatAggregator() {
	}
	
	public long countCatsByColorImperative(Collection<Cat> allCats, String bodyColorToMatch, String eyeColorToMatch) {
		long numOfCats = 0l;
		for (Cat cat: allCats) {
			if (cat.getBodyColor().equalsIgnoreCase(bodyColorToMatch) && 
				cat.getEyeColor().equalsIgnoreCase(eyeColorToMatch)) {
				numOfCats++;
			}
		}
		System.out.println("Imperative: Found " + numOfCats + " " + bodyColorToMatch + " cats with " + eyeColorToMatch + " eyes");
		return numOfCats;
	}
	
	public long countCatsByColorFunctional(Collection<Cat> allCats, String bodyColorToMatch, String eyeColorToMatch) {
		long numOfCats = allCats.stream()
			.filter(cat -> cat.getBodyColor().equalsIgnoreCase(bodyColorToMatch))
			.filter(cat -> cat.getEyeColor().equalsIgnoreCase(eyeColorToMatch))
			.mapToLong(cat -> 1l)
			.reduce(0, (partialSum1, partialSum2) -> partialSum1 + partialSum2);
		
		System.out.println("Functional: Found " + numOfCats + " " + bodyColorToMatch + " cats with " + eyeColorToMatch + " eyes");
		return numOfCats;
	}

}
