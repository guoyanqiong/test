package com.example.demo.utils.common.enums;

/**
 * Created with Intellij IDEA
 * Author: YanJun Pan.
 * Date  : 2017/10/25 0025.
 * Time  : 16:01.
 */
public enum SignReward {

    one(1,0.01), two(2, 0.02), three(3, 0.03),four(4, 0.04),five(5, 0.05),six(6, 0.06),seven(7, 0.07);



    private SignReward(Integer totleDays, double value){
        this.totleDays = totleDays;
        this.rewark = value;
    }


    /**
     * 获取签到总天数
     */
    public int getTotleDays(){
        return this.totleDays;
    }
    /**
     * 获取签到奖励
     */
    public double getRewark(){
        return this.rewark;
    }

    public static SignReward parse(int totleDays){
        SignReward s = null;
        for (SignReward signReward : SignReward.values()){
            if (signReward.getTotleDays() == totleDays){
                s = signReward;
                break;
            }
        }
        return s;
    }

    private int totleDays;
    private double rewark;
}
