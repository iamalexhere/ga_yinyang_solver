// package com.ai.yinyang_solver;

// import java.util.Comparator;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.Map;
// import java.util.WeakHashMap;

// public class GeneticAlgorithm<C extends Chromosome<C>, T extends Comparable<T>> {

// 	private static final int ALL_PARENTAL_CHROMOSOMES = Integer.MAX_VALUE;

// 	private class ChromosomesComparator implements Comparator<C> {	//class buat ngebandingin fitness antar kromosom
// 		private final Map<C, T> cache = new WeakHashMap<C, T>();	//populasi
		
// 		@Override
// 		public int compare(C chr1, C chr2) {
// 			//ambil fitness kedua kromosom, terus bandingin
// 			T fit1 = this.fit(chr1);
// 			T fit2 = this.fit(chr2);
// 			int ret = fit1.compareTo(fit2);
// 			return ret;
// 		}

// 		public T fit(C chr) {
// 			T fit = this.cache.get(chr);	//ambil kromosom dari hashmap
// 			if (fit == null) {
// 				fit = GeneticAlgorithm.this.fitnessFunc.calculate(chr);	//hitung fitnessnya
// 				this.cache.put(chr, fit);	//taro di hashmap
// 			}
// 			return fit;
// 		};

// 		public void clearCache() {
// 			this.cache.clear();	//kosongin hash map
// 		}
// 	}

// 	private final ChromosomesComparator chromosomesComparator;

// 	private final FitnessFunction<C, T> fitnessFunc;

// 	private Population<C> population;

// 	// listeners of genetic algorithm iterations (handle callback afterwards)
// 	private final List<IterationListener<C, T>> iterationListeners = new LinkedList<IterationListener<C, T>>();

// 	private boolean terminate = false;

// 	// number of parental chromosomes, which survive (and move to new
// 	// population)
// 	private int parentChromosomesSurviveCount = ALL_PARENTAL_CHROMOSOMES;

// 	private int iteration = 0;

// 	public GeneticAlgorithm(Population<C> population, FitnessFunction<C, T> fitnessFunc) {	//constructor
// 		this.population = population;
// 		this.fitnessFunc = fitnessFunc;
// 		this.chromosomesComparator = new ChromosomesComparator();
// 		this.population.sortPopulationByFitness(this.chromosomesComparator);
// 	}

// 	public void evolve() {
// 		int parentPopulationSize = this.population.getSize();

// 		Population<C> newPopulation = new Population<C>();

// 		for (int i = 0; (i < parentPopulationSize) && (i < this.parentChromosomesSurviveCount); i++) {
// 			newPopulation.addChromosome(this.population.getChromosomeByIndex(i)); //nambah kromosom ke populasi baru dari populasi lama
// 		}

// 		for (int i = 0; i < parentPopulationSize; i++) {	//looping seluruh populasi, crossover semuanya, mutasi semuanya
// 			C chromosome = this.population.getChromosomeByIndex(i);
// 			C mutated = chromosome.mutate();	//ini gak pake probabilitas mutasinya, tapi nanti kita pake

// 			C otherChromosome = this.population.getRandomChromosome();
// 			List<C> crossovered = chromosome.crossover(otherChromosome);

// 			newPopulation.addChromosome(mutated);
// 			for (C c : crossovered) {
// 				newPopulation.addChromosome(c);
// 			}
// 		}

// 		newPopulation.sortPopulationByFitness(this.chromosomesComparator);
// 		newPopulation.trim(parentPopulationSize);
// 		this.population = newPopulation;
// 	}

// 	public void evolve(int count) {	//loop semua iterasi, lakukan algoritma genetik
// 		this.terminate = false;

// 		for (int i = 0; i < count; i++) {
// 			if (this.terminate) {
// 				break;
// 			}
// 			this.evolve();
// 			this.iteration = i;
// 			for (IterationListener<C, T> l : this.iterationListeners) {
// 				l.update(this);
// 			}
// 		}
// 	}

// 	public int getIteration() {
// 		return this.iteration;
// 	}

// 	public void terminate() {
// 		this.terminate = true;
// 	}

// 	public Population<C> getPopulation() {
// 		return this.population;
// 	}

// 	public C getBest() {
// 		return this.population.getChromosomeByIndex(0);
// 	}

// 	public C getWorst() {
// 		return this.population.getChromosomeByIndex(this.population.getSize() - 1);
// 	}

// 	public void setParentChromosomesSurviveCount(int parentChromosomesCount) {
// 		this.parentChromosomesSurviveCount = parentChromosomesCount;
// 	}

// 	public int getParentChromosomesSurviveCount() {
// 		return this.parentChromosomesSurviveCount;
// 	}

// 	public void addIterationListener(IterationListener<C, T> listener) {
// 		this.iterationListeners.add(listener);
// 	}

// 	public void removeIterationListener(IterationListener<C, T> listener) {
// 		this.iterationListeners.remove(listener);
// 	}

// 	public T fitness(C chromosome) {
// 		return this.chromosomesComparator.fit(chromosome);
// 	}

// 	public void clearCache() {
// 		this.chromosomesComparator.clearCache();
// 	}
// }
