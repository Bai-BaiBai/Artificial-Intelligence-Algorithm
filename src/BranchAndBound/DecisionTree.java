package BranchAndBound;

import java.util.ArrayList;
import java.util.List;

/**
 * 决策树--Branch and Bound分支限界算法的实现
 * 未考虑松弛，只按整数规划解决
 * 解决0-1背包问题
 */
public class DecisionTree {

    public int totalRoom;//背包容量
    public Node root;//根节点
    public int quantity;//物品数量
    public int[][] commodities;//物品价值、重量矩阵 [0]为value，[1]为weight

    private class Node{
        public int value;//当前背包价值
        public int room;//剩余空间
        public int estimate;//期望值
        public Node left, right;
        public Node parent;//父指针

        public Node(int value, int room, int estimate, Node parent){
            this.value = value;
            this.room = room;
            this.estimate = estimate;
            this.left = null;
            this.right = null;
            this.parent = parent;
        }
    }

    public DecisionTree(int[][] commodities, int totalRoom){
        this.totalRoom = totalRoom;
        this.quantity = commodities[0].length;
        this.commodities = commodities;
        int sumValue = 0;
        for (int i = 0; i < commodities[0].length; i++) {
            sumValue += commodities[0][i];
        }
        this.root = new Node(0, totalRoom, sumValue, null);
    }

    //递归构建决策树
    //初始对root建立左右节点，depth为树的深度(同时也是待决策物品的下标)，maxValue为树中各个节点的最大背包价值
    public void createTree(){
        __createTree(root, 0, 0);
    }

    //commodities[depth]为要判断拿不拿的物品
    //如果当前记录的最大value > new节点的estimate，则剪枝
    public void __createTree(Node node, int depth, int maxValue){
        if (node == null) return;
        if (depth >= quantity) return;

        int newRoom = node.room - commodities[1][depth];

        //判断commodities[depth]物品能不能装下，也就是node节点是否创建左孩子
        if (newRoom >= 0){//说明下一物品可以装下
            int newValue = node.value + commodities[0][depth];
            node.left = new Node(newValue, newRoom, node.estimate, node);
            if (newValue > maxValue){
                maxValue = newValue;//更新maxValue
            }
        }else {//下一物品装不下
            node.left = null;
        }

        int rightEstimate = node.estimate - commodities[0][depth];
        if (maxValue >= rightEstimate){//如果当前的maxValue比右孩子的期望价值大，则说明右孩子可以剪枝
            node.right = null;
        }else {//如果右孩子的期望价值更大，则不剪
            node.right = new Node(node.value, node.room, rightEstimate, node);
        }

        __createTree(node.left, depth+1, maxValue);
        __createTree(node.right, depth+1, maxValue);
    }

    //寻找可装的最大价值
    public int findMaxValue(){
        Node maxNode = new Node(0, 0, 0, null);
        return __findMaxNode(root, maxNode).value;
    }

    //按给定的商品顺序 ！倒序！ 表示每个商品是否装到包里，1代表装了，0代表每装
    public List<Integer> loadedItem(){
        Node maxNode = __findMaxNode(root, new Node(0,0,0,null));
        ArrayList<Integer> list = new ArrayList<>();
        while (maxNode.parent != null){
            if (maxNode.value == maxNode.parent.value){
                list.add(0);
            }else {
                list.add(1);
            }
            maxNode = maxNode.parent;
        }
        return list;
    }

    //辅助函数，返回存有当前最优解的节点
    private Node __findMaxNode(Node node, Node maxNode){
        if (node == null) return maxNode;

        if (node.value > maxNode.value) maxNode = node;
        Node leftMax = __findMaxNode(node.left, maxNode);
        Node rightMax = __findMaxNode(node.right, maxNode);
        return leftMax.value > rightMax.value ? leftMax : rightMax;
    }

}
