package com.ai.yinyang_solver;

public interface IterationListener<C extends Chromosome<C>, T extends Comparable<T>> {

    void update( GeneticAlgorithm<C, T> environment );
    
}