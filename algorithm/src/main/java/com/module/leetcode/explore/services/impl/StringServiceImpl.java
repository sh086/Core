package com.module.leetcode.explore.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.module.leetcode.explore.services.StringService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 字符串类练习算法 实现类
 * @author suhe
 * @since 2018年11月10日08:19:00
 * */
@Service
public class StringServiceImpl implements StringService {

    @Override
    public String reverseString(String s) {
        char[] chars = s.toCharArray();
        for (int i = 0,j = s.length()-1; i < j; i++,j--){
            //交换i、j位置
            chars[i] += chars[j];
            chars[j] =(char)(chars[i] - chars[j]);
            chars[i] =(char)(chars[i] - chars[j]);
        }
        return String.valueOf(chars);
    }

    @Override
    public int reverse(int x) {
        int flag = x<0 ? -1:1;
        int number = Math.abs(x);
        Long reverse = 0L;
        while (number > 0){
            reverse = reverse * 10 +number%10;
            number /= 10;
        }
        if(reverse > Integer.MAX_VALUE){
            return 0;
        }
        return reverse.intValue()*flag;
    }

    @Override
    public int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        //记录已经出现过的字符值次数
        Map<Character,Integer> map = new HashMap<>();
        for(int i = 0; i < chars.length; i++){
            if(map.containsKey(chars[i])){
                map.put(chars[i],map.get(chars[i])+1);
            }else {
                map.put(chars[i],1);
            }
        }
        //去除第一个不重复的字符
        int index = -1;
        for(int i = 0; i < chars.length; i++){
            if(map.get(chars[i]) == 1){
                index = i;break;
            }
        }
        return index;
    }

    @Override
    public boolean isAnagram(String s, String t) {
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        //排序
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        if(chars1.length != chars2.length ){
            return false;
        }
        for(int i = 0; i < chars1.length ;i++){
            if(chars1[i] != chars2[i]){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPalindrome(String string) {
        char[] digest = string.toCharArray();
        //去除特殊字符、转化字符大小写
        //m : 正数第一个有效值
        //n : 索引查询有效值
        int m= -1,n = 0;
        while (n < digest.length){
            boolean flag = false;
            boolean sample = (digest[n] <= 'z' && digest[n] >= 'a')
                    || (digest[n] <= '9' && digest[n] >= '0');
            if(digest[n] <= 'Z' && digest[n] >= 'A'){
                digest[n] -= 'A'-'a';   flag = true;
            }else if(sample){
                flag = true;
            }
            if(flag){
                digest[++m] = digest[n++];
            }else {
                n++;
            }
        }
        //验证字符串
        int i = 0;
        while (i <= m){
            if (digest[i++] != digest[m--]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int myAtoi(String str) {
        int m = 0,n = 0;
        //去除前后空格
        str = str.trim();
        //确定最后一个数字位值
        char[] chars = str.toCharArray();
        for(int i = 0 ; i < chars.length; i++){
            if(i == 0 && ( chars[i] == '-' || chars[i] == '+')){
                m = 0;n = 1;
                continue;
            }
            if(!(chars[i] >= '0' && chars[i] <= '9')){
                break;
            }
            n = i+1;
        }
        Double temp = 0D;
        //可以进行有效的转换
        if(m < n){
            String string = str.substring(m,n);
            if(!("+".equals(string) || "-".equals(string))){
                temp = Double.parseDouble(str.substring(m,n));
            }
        }
        //判断是否越界
        if(temp > Integer.MAX_VALUE){
            return Integer.MAX_VALUE;
        }else if(temp < Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        return temp.intValue();
    }

    @Override
    public int strStr(String haystack, String needle) {
        char[] chars1 = haystack.toCharArray();
        char[] chars2 = needle.toCharArray();
        if(chars1.length > 0  && chars2.length > 0 ){
            //遍历源数组，最大长度为 (desc - src)
            for(int i = 0; i <= chars1.length - chars2.length; i++){
                if(chars1[i] == chars2[0]){
                    //表示是否包含
                    Boolean flag = true;
                    for(int j = 0; j < chars2.length; j++){
                        if(chars1[i+j] != chars2[j]){
                            flag = false; break;
                        }
                    }
                    if(flag){
                        //返回needle 字符串出现的第一个位置 (从0开始)
                        return i;
                    }
                }
            }
        }
        if(chars2.length == 0){
            //若为空字符串，则需返回0
            return 0;
        }
        //不存在，则返回  -1。
        return -1;
    }

    @Override
    public String countAndSay(int n) {
        //约定取值范围
        if( n > 30 || n < 1){
            return "";
        }
        //递归结束条件
        if(n == 1){
            return "1";
        }
        String last = countAndSay(n-1);
        char[] chars = last.toCharArray();
        StringBuilder buffer = new StringBuilder();
        //数量、数值
        int count = 0;
        char str = chars[0];
        for(int i = 0; i < chars.length ; i++){
            if(str == chars[i]){
                count++;
            }else {
                buffer.append(count).append(str);
                count = 1;
                str = chars[i];
            }
        }
        buffer.append(count).append(str);
        return buffer.toString();
    }

    @Override
    public String longestCommonPrefix(String[] strs) {
        String prefix = "";
        //约定取值范围
        if(strs.length > 0){
            //获取第一个字符串
            String firstString = strs[0];
            //倒叙处理 [0,i)，左闭右开区间
            for(int i = firstString.length(); i > 0; i--){
                String string = firstString.substring(0,i);
                int count = 1;
                for(int n = 1; n < strs.length; n++){
                    if(i <= strs[n].length() &&
                            //或使用startWith函数判别
                            string.equals(strs[n].substring(0,i))){
                        count++;
                    }
                }
                //判断
                if(count == strs.length){
                    prefix = string; break;
                }
            }
        }
        return prefix;
    }

    @Override
    public String longestCommonString(String[] strs) {
        System.out.println(JSONObject.toJSONString(strs));
        String prefix = "";
        //约定取值范围
        if(strs.length <= 0){
            return prefix;
        }
        //推入公共前缀到Set中
        Set<String> set = new HashSet<>();
        String firstString = strs[0];
        //左闭右开区间
        for(int i = 0; i < firstString.length(); i++){
            for(int j = i+1; j <= firstString.length(); j++){
                String string = firstString.substring(i,j);
                Boolean flag = true;
                for(int n = 1; n < strs.length; n++){
                    int index = strs[n].indexOf(string);
                    if(index == -1){
                        flag = false;break;
                    }
                }
                if(flag){
                    set.add(firstString.substring(i,j));
                }
            }
        }
        //获取最长公共前缀
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String string = iterator.next();
            if(string.length() > prefix.length()){
                prefix = string;
            }
        }
        return prefix;
    }
}