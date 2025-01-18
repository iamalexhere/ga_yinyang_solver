package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Population<C extends Chromosome<C>> implements Iterable<C> {

    //Population
	private static final int DEFAULT_NUMBER_OF_CHROMOSOMES = 32;

    //List of chromosomes
	private List<C> chromosomes = new ArrayList<C>(DEFAULT_NUMBER_OF_CHROMOSOMES);

	private final Random random;

    public Population(Random random) {
        this.random = random;
    }

    public Population() {
        this.random = new Random();
    }

	public void addChromosome(C chromosome) {
		this.chromosomes.add(chromosome);
	}

	public int getSize() {
		return this.chromosomes.size();
	}

    //for mutation (?)
	public C getRandomChromosome() {
		int numOfChromosomes = this.chromosomes.size();
		int indx = this.random.nextInt(numOfChromosomes);
		return this.chromosomes.get(indx);
	}

	public C getChromosomeByIndex(int indx) {
		return this.chromosomes.get(indx);
	}

	public void sortPopulationByFitness(Comparator<C> chromosomesComparator) {
		Collections.shuffle(this.chromosomes);
		Collections.sort(this.chromosomes, chromosomesComparator);
	}


	/**
	 * shortening population till specific number
	 */
    //motong 
	public void trim(int len) {
		this.chromosomes = this.chromosomes.subList(0, len);
	}

	@Override
	public Iterator<C> iterator() {
		return this.chromosomes.iterator();
	}

    // Add protected methods for subclasses
    protected void setChromosomes(List<C> newChromosomes) {
        this.chromosomes = new ArrayList<>(newChromosomes);
    }

    protected List<C> getChromosomes() {
        return new ArrayList<>(this.chromosomes);
    }
}