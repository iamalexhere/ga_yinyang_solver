package com.ai.yinyang_solver;

public interface FitnessFunction<C extends Chromosome<C>, T extends Comparable<T>> {
    T calculate(C chromosome);
}
