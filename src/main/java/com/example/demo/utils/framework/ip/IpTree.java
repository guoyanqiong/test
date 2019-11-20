package com.example.demo.utils.framework.ip;


import com.example.demo.utils.common.entity.IpInfo;

/**
 * Created by Wang Zhe on 2015/8/11.
 */
public class IpTree {

    private static IpTree instance = null;
    private IpNode rootNode = new IpNode();

    private final String NO_ADDRESS = "未知";

    private IpTree() {

    }

    public static IpTree getInstance() {
        if (instance == null) {
            instance = new IpTree();
        }
        return instance;
    }

    public void train(String ipStart, String ipEnd, String addressCode) {

        int ip_s = ipToInt(ipStart);
        int ip_e = ipToInt(ipEnd);

        if (ip_e == -1 || ip_s == -1)
            return;

        IpNode curNode = rootNode;
        IpNode leftNode = null;
        IpNode rightNode = null;
        boolean flag = false;

        for (int i = 0; i < 32; i++) {

            int ip_s_bit = (0x80000000 & ip_s) >>> 31;
            int ip_e_bit = (0x80000000 & ip_e) >>> 31;

            if (flag == false) {

                if ((ip_s_bit ^ ip_e_bit) == 0) {

                    if (ip_s_bit == 1) {
                        if (curNode.rightNode == null) {
                            curNode.rightNode = new IpNode();
                        }
                        curNode = curNode.rightNode;
                    } else {
                        if (curNode.leftNode == null) {
                            curNode.leftNode = new IpNode();
                        }
                        curNode = curNode.leftNode;
                    }
                    if (i == 31) {
                        curNode.addressCode = addressCode;
                    }

                } else {
                    flag = true;
                    if (curNode.leftNode == null) {
                        curNode.leftNode = new IpNode();
                    }
                    leftNode = curNode.leftNode;

                    if (curNode.rightNode == null) {
                        curNode.rightNode = new IpNode();
                    }

                    rightNode = curNode.rightNode;

                    if (i == 31) {
                        leftNode.addressCode = addressCode;
                        rightNode.addressCode = addressCode;
                    }
                }
            } else {
                if (ip_s_bit == 1) {
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode = leftNode.rightNode;
                } else {
                    if (leftNode.leftNode == null) {
                        leftNode.leftNode = new IpNode();
                    }
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode.rightNode.addressCode = addressCode;
                    leftNode = leftNode.leftNode;
                }
                if (i == 31)
                    leftNode.addressCode = addressCode;

                if (ip_e_bit == 1) {
                    if (rightNode.rightNode == null) {
                        rightNode.rightNode = new IpNode();
                    }
                    if (rightNode.leftNode == null) {
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode.leftNode.addressCode = addressCode;
                    rightNode = rightNode.rightNode;
                } else {
                    if (rightNode.leftNode == null) {
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode = rightNode.leftNode;
                }
                if (i == 31)
                    rightNode.addressCode = addressCode;
            }

            ip_s = ip_s << 1;
            ip_e = ip_e << 1;
        }
    }

    public void train(IpRelation ipRelation) {
        String ipStart = ipRelation.getIpStart();
        String ipEnd = ipRelation.getIpEnd();
        String addressCode = ipRelation.getProvince();
        String city = ipRelation.getCity();
        String cityCode = String.valueOf(ipRelation.getIpCode()).substring(4);
        String provinceCode = cityCode.substring(1, 4) + "00";
        int ip_s = ipToInt(ipStart);
        int ip_e = ipToInt(ipEnd);

        if (ip_e == -1 || ip_s == -1)
            return;

        IpNode curNode = rootNode;
        IpNode leftNode = null;
        IpNode rightNode = null;
        boolean flag = false;

        for (int i = 0; i < 32; i++) {

            int ip_s_bit = (0x80000000 & ip_s) >>> 31;
            int ip_e_bit = (0x80000000 & ip_e) >>> 31;

            if (flag == false) {

                if ((ip_s_bit ^ ip_e_bit) == 0) {

                    if (ip_s_bit == 1) {
                        if (curNode.rightNode == null) {
                            curNode.rightNode = new IpNode();
                        }
                        curNode = curNode.rightNode;
                    } else {
                        if (curNode.leftNode == null) {
                            curNode.leftNode = new IpNode();
                        }
                        curNode = curNode.leftNode;
                    }
                    if (i == 31) {
                        curNode = getIpNode(ipRelation);
                        ;
                    }

                } else {
                    flag = true;
                    if (curNode.leftNode == null) {
                        curNode.leftNode = new IpNode();
                    }
                    leftNode = curNode.leftNode;

                    if (curNode.rightNode == null) {
                        curNode.rightNode = new IpNode();
                    }

                    rightNode = curNode.rightNode;

                    if (i == 31) {
                        leftNode = getIpNode(ipRelation);
                        ;
                        rightNode = getIpNode(ipRelation);
                        ;
                    }
                }
            } else {
                if (ip_s_bit == 1) {
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode = leftNode.rightNode;
                } else {
                    if (leftNode.leftNode == null) {
                        leftNode.leftNode = new IpNode();
                    }
                    if (leftNode.rightNode == null) {
                        leftNode.rightNode = new IpNode();
                    }
                    leftNode.rightNode = getIpNode(ipRelation);
                    leftNode = leftNode.leftNode;
                }
                if (i == 31) {
                    leftNode = getIpNode(ipRelation);
                }
                if (ip_e_bit == 1) {
                    if (rightNode.rightNode == null) {
                        rightNode.rightNode = new IpNode();
                    }
                    if (rightNode.leftNode == null) {
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode.leftNode = getIpNode(ipRelation);
                    rightNode = rightNode.rightNode;
                } else {
                    if (rightNode.leftNode == null) {
                        rightNode.leftNode = new IpNode();
                    }
                    rightNode = rightNode.leftNode;
                }
                if (i == 31) {
                    rightNode = getIpNode(ipRelation);
                }
            }

            ip_s = ip_s << 1;
            ip_e = ip_e << 1;
        }
    }

    private IpNode getIpNode(IpRelation ipRelation) {
        String addressCode = ipRelation.getProvince();
        String city = ipRelation.getCity();
        String cityCode = String.valueOf(ipRelation.getIpCode()).substring(4);
        String provinceCode = cityCode.substring(0, 2) + "0000";
        IpNode ipNode = new IpNode();
        ipNode.addressCode = addressCode;
        ipNode.city = city;
        ipNode.cityCode = cityCode;
        ipNode.provinceCode = provinceCode;
        return ipNode;
    }

    public IpInfo findIp(String ip) {
        IpInfo ipInfo = null;
        IpNode curNode = rootNode;
        int ip_int = ipToInt(ip);
        if (ip_int == -1)
            return ipInfo;

        for (int i = 0; i < 32; i++) {

            int ip_s_bit = (0x80000000 & ip_int) >>> 31;

            if (ip_s_bit == 0)
                curNode = curNode.leftNode;
            else
                curNode = curNode.rightNode;

            if (curNode == null) {
                return ipInfo;
            }

            if (curNode.addressCode != null && !curNode.addressCode.trim().equals("")) {
                ipInfo = new IpInfo();
                ipInfo.setCity(curNode.city);
                ipInfo.setCity_id(curNode.cityCode);
                ipInfo.setRegion_id(curNode.provinceCode);
                ipInfo.setRegion(curNode.addressCode);
                ipInfo.setIp(ip);
                return ipInfo;
            }
            ip_int = ip_int << 1;
        }

        return ipInfo;
    }

    private int ipToInt(String strIP) {
        try {

            int[] ip = new int[4];

            int position1 = strIP.indexOf(".");
            int position2 = strIP.indexOf(".", position1 + 1);
            int position3 = strIP.indexOf(".", position2 + 1);

            ip[0] = Integer.parseInt(strIP.substring(0, position1));
            ip[1] = Integer.parseInt(strIP.substring(position1 + 1, position2));
            ip[2] = Integer.parseInt(strIP.substring(position2 + 1, position3));
            ip[3] = Integer.parseInt(strIP.substring(position3 + 1));
            int ip_int = (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];

            return ip_int;

        } catch (Exception e) {
            return -1;
        }
    }

    private class IpNode {
        private IpNode leftNode;

        private IpNode rightNode;

        private String addressCode;

        private String city;

        private String cityCode;

        private String provinceCode;

    }

    private void loopTree(IpNode ipNode, int depth) {
        System.out.println(depth + "\t" + ipNode.addressCode);
        if (ipNode.leftNode != null) {
            System.out.println("left");
            loopTree(ipNode.leftNode, depth + 1);
        }
        if (ipNode.rightNode != null) {
            System.out.println("right");
            loopTree(ipNode.rightNode, depth + 1);
        }
    }
}
