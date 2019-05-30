package BranchAndBound;

public class Main {

    public static void main(String[] args) {
        int[][] commodities = {{45, 48, 35},
                                {5, 8, 3}};

        DecisionTree decisionTree = new DecisionTree(commodities, 10);
        decisionTree.createTree();
        System.out.println(decisionTree.findMaxValue());
    }
}
