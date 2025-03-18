package com.example.bjfood.utils;

import java.util.List;

public class CosineSimilarity {
    public static double calculateSimilarity(List<Integer> vecA, List<Integer> vecB) {
        if (vecA.size() != vecB.size()) {
            throw new IllegalArgumentException("向量长度必须相同");
        }

        int dotProduct = 0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vecA.size(); i++) {
            dotProduct += vecA.get(i) * vecB.get(i);
            normA += Math.pow(vecA.get(i), 2);
            normB += Math.pow(vecB.get(i), 2);
        }

        return (normA == 0 || normB == 0) ? 0 : (dotProduct / (Math.sqrt(normA) * Math.sqrt(normB)));
    }
}
