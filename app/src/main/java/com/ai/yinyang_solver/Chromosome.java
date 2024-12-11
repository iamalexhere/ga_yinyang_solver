package com.ai.yinyang_solver;

import java.util.List;

public interface Chromosome<C extends Chromosome<C>> {
    List<C> crossover( C anotherChromosome);
    C mutate();
}
