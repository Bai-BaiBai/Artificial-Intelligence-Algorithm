package AntGroup;

import java.util.Random;

/**
 * 蚂蚁相关信息
 */
public class Ant {

    public static final int ATTRACTIVE_CONSTANT = 20;//吸引力放大倍数

    public City city;//所属城市群
    public boolean[] visitedFlag;//访问标志数组,false为可访问，true为已访问过
    public int[] path;//走过的路径
    public int startCity;//蚂蚁的出发地

    public Ant(City cities, int startCity){
        this.city = cities;
        this.startCity = startCity;
        getInitVisitedFlag();//初始化访问标志数组
        initPath();
    }

    //初始化的访问标志数组，初始全为false
    public boolean[] getInitVisitedFlag(){
        visitedFlag = new boolean[City.CITYSIZE];
        for (int i = 0; i < City.CITYSIZE; i++) {
            visitedFlag[i] = false;
        }
        return visitedFlag;
    }

    //初始化走过的路径
    public void initPath(){

        path = new int[City.CITYSIZE];
    }

    //获取走过的路径
    public int[] getPath(){
        return path;
    }

    //传入蚂蚁初始的位置，方法结束后Path数组存储这次行走的路径
    public void travel(){
        int currentCity = startCity;
        for (int i = 0; i < City.CITYSIZE; i++) {//i代表下一步走的是第i步
//            System.out.println("第" + i + "步" + startCity);
            int res = moveToNextCity(currentCity, i);
            while (res == -1 && i != City.CITYSIZE -1){
                res = moveToNextCity(currentCity, i);
            }
            currentCity = res;
        };
    }

    //蚂蚁从currentCicy走向下一个城市，返回选择的下一座城市，并更新信息素矩阵
    public int moveToNextCity(int currentCity, int stepIndex){
        visitedFlag[currentCity] = true;
        path[stepIndex] = currentCity;

        //选择下一步城市,更新信息素矩阵
        int res = chooseNextCity(currentCity);
        if (res != -1) {
            city.updatePheromone(currentCity, res);
        }
        return res;
    }

    //选择下一步要走的城市 :有可能返回-1
    public int chooseNextCity(int currentCity){

        double[] attractive = new double[City.CITYSIZE];//存储剩余可走城市对蚂蚁的吸引力

        double sumAttractive = 0.00;
        for (int i = 0; i < City.CITYSIZE; i++) {
            if (!visitedFlag[i]){//如果此城市没有被访问过
                attractive[i] = ATTRACTIVE_CONSTANT /Math.pow(city.distance[currentCity][i],2) * city.pheromone[currentCity][i];
                sumAttractive += attractive[i];
            }else {
                attractive[i] = 0;//如果访问过，吸引力为0
            }
        }

        //输出吸引力
//        outPutAttractive(attractive, currentCity);

        //轮盘赌算法找到吸引力最大的城市并判断合法性返回
        int nextCity = roulette(sumAttractive, attractive);

        return nextCity;
    }

    //轮盘赌算法，传入各城市的当前吸引力和 吸引力总和,返回选出的城市代号
    public int roulette(double sumAttractive, double[] attractive){

        double[] probability = new double[City.CITYSIZE];//存储走i城市对应的总概率

        probability[0] = attractive[0] / sumAttractive;
        for (int i = 1; i < City.CITYSIZE; i++) {
            probability[i] = probability[i-1] + attractive[i] / sumAttractive;
        }

        Random random = new Random();
        double ran = random.nextDouble();//生成随机数，范围[0..1)

        for (int i = 0; i < City.CITYSIZE; i++) {
            if (ran < probability[i]){
                return i;
            }
        }

        return -1;
    }

    public int getPathDistance(){
        int sum = 0;
        for (int i = 1; i < City.CITYSIZE; i++) {
            sum += city.distance[path[i-1]][path[i]];
        }

        return sum;
    }

    //输出在currentCity时，其它城市的吸引力
    //需要在 chooseNextCity 方法中调用
    public void outPutAttractive(double[] attractive, int currentCity){
        StringBuilder res = new StringBuilder();
        res.append("当前所在城市为：" + currentCity + " 其余城市的吸引力为：");
        for (int i = 0; i < attractive.length; i++) {
            res.append(attractive[i] + " ");
        }
        System.out.println(res.toString());
    }
}
