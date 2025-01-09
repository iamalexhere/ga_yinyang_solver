package com.ai.yinyang_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//actually evolution, bukan encoding草
public class YinYangEncoding implements Chromosome<YinYangEncoding> {
    protected char[] chromosome;

    public YinYangEncoding(char[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public List<YinYangEncoding> crossover(YinYangEncoding anotherChromosome) {
        int partitionPoint = this.chromosome.length / 2; // single point crossover
        char[] firstChild = new char[chromosome.length];
        char[] secondChild = new char[chromosome.length];

        int j = 0;
        for(;j < partitionPoint;j++) {
            firstChild[j] = this.chromosome[j];
            secondChild[j] = anotherChromosome.chromosome[j];
        }

        for(;j < chromosome.length;j++) {
            firstChild[j] = anotherChromosome.chromosome[j];
            secondChild[j] = this.chromosome[j];
        }

        YinYangEncoding child1 = new YinYangEncoding(firstChild);
        YinYangEncoding child2 = new YinYangEncoding(secondChild);

        List<YinYangEncoding> children = new ArrayList<>();
        children.add(child1);
        children.add(child2);

        return children;
    }

    @Override
    public YinYangEncoding mutate() {
        int seed = 23798293;
        Random rand = new Random(seed);
        int mutatedIdx = rand.nextInt(this.chromosome.length);
        
        if (this.chromosome[mutatedIdx] == 3 || this.chromosome[mutatedIdx] == 4) {
            return this;
        }

        if (this.chromosome[mutatedIdx] == 1) {
            this.chromosome[mutatedIdx] = 2;
        } else {
            this.chromosome[mutatedIdx] = 1;
        }

        return this;
    }

}