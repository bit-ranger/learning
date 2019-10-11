package com.rainyalley.practice.algorithm;

/**
 * 三色旗算法
 */
public class Tricolour {

    private final static String leftColor = "b";
    private final static String rightColor = "r";

    public void tricolour(Object[] src){
        //中间区域的左端点
        int mli = 0;
        //中间区域的右端点
        int mri = src.length-1;
        //遍历元素,从mli开始到mri结束,mli与mri会动态调整,但是i比mli变化快
        for (int i = mli; i <= mri; i++) {
            //如果当前元素属于左边
            if(isPartOfLeft(src[i])){
                //将当前元素换与中间区域左端点元素互换
                swap(src,mli,i);
                //mli位的元素是经过处理的,一定不是红色,所以不需要再分析i位新元素
                //左端点右移
                mli++;
            }
            //如果当前元素属于右边
            else if(isPartOfRight(src[i])){
                //从中间区域的右端点开始向左扫描元素
                while (mri > i) {
                    //如果扫描到的元素属于右边,右端点左移,继续向左扫描,否则停止扫描
                    if(isPartOfRight(src[mri])){
                        mri--;
                    } else {
                        break;
                    }
                }
                //将当前元素交换到中间区域右端点
                swap(src,mri,i);
                //右端点左移
                mri--;
                //mri位的元素可能是蓝色的,需要再分析i位新元素
                i--;
            }
        }
    }

    private boolean isPartOfLeft(Object item){
        return leftColor.equals(item);
    }

    private boolean isPartOfRight(Object item){
        return rightColor.equals(item);
    }

    private void swap(Object[] src, int fst, int snd){
        Object tmp = src[fst];
        src[fst] = src[snd];
        src[snd] = tmp;
    }

    public static void main(String[] args) {
        String[] flags =
            new String[]{"r","b","w","w","b","r","r","w","b","b","r","w","b"};
        new Tricolour().tricolour(flags);
        for (String flag : flags) {
            System.out.printf("%s,",flag);
        }
    }
}
