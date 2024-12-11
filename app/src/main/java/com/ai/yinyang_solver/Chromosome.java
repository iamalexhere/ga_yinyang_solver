package com.ai.yinyang_solver;

import java.util.List;

//metode-metode mempopulasikan generasi baru
public interface Chromosome<C extends Chromosome<C>> {
    
    List<C> crossover( C anotherChromosome);

    C mutate();
}
