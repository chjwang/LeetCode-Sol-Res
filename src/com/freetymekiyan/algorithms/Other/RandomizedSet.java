package com.freetymekiyan.algorithms.Other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 Design a data structure that supports all following operations in average O(1) time.

 insert(val): Inserts an item val to the set if not already present.
 remove(val): Removes an item val from the set if present.
 getRandom: Returns a random element from current set of elements. Each element must have the same probability of being returned.

 Example:

 // Init an empty set.
 RandomizedSet randomSet = new RandomizedSet();

 // Inserts 1 to the set. Returns true as 1 was inserted successfully.
 randomSet.insert(1);

 // Returns false as 2 does not exist in the set.
 randomSet.remove(2);

 // Inserts 2 to the set, returns true. Set now contains [1,2].
 randomSet.insert(2);

 // getRandom should return either 1 or 2 randomly.
 randomSet.getRandom();

 // Removes 1 from the set, returns true. Set now contains [2].
 randomSet.remove(1);

 // 2 was already in the set, so return false.
 randomSet.insert(2);

 // Since 1 is the only number in the set, getRandom always return 1.
 randomSet.getRandom();

 思路: 要在O(1)的时间内完成插入, 删除, 取随机元素.

 用链表保存元素可以在O(1)内完成插入和删除, 但是无法在O(1)随机元素. 所以需要一个可以随机存取的容器.

 如果使用数组的话可以在O(1)内完成插入和随机, 但是删除无法在O(1)完成, 但是如果用另外一种删除方式, 即把要删除的元素
 与最后的元素交换位置, 而删除最后一个元素就可以在O(1)完成. 这样的话还需要一个hash表来记录元素在数组的位置,
 这样才可以来O(1)取到这个元素, 并移动到尾部. 还需要注意的是在删除一个元素的时候与另外一个元素交换了位置, 所以在
 hash表中还需要将另外一个元素的位置更新.

 */
public class RandomizedSet {
    HashMap<Integer, Integer> map1;
    HashMap<Integer, Integer> map2;
    Random rand;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        map1  = new HashMap<Integer, Integer>();
        map2  = new HashMap<Integer, Integer>();
        rand = new Random(System.currentTimeMillis());
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(map1.containsKey(val)){
            return false;
        }else{
            map1.put(val, map1.size());
            map2.put(map2.size(), val);
        }
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(map1.containsKey(val)){
            int index = map1.get(val);

            //remove the entry from both maps
            map1.remove(val);
            map2.remove(index);

            if(map1.size()==0){
                return true;
            }

            //if last is deleted, do nothing
            if(index==map1.size()){
                return true;
            }

            //update the last element's index
            int key1 = map2.get(map2.size());

            map1.put(key1, index);
            map2.remove(map2.size());
            map2.put(index, key1);

        }else{
            return false;
        }

        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        if(map1.size()==0){
            return -1;
        }

        if(map1.size()==1){
            return map2.get(0);
        }

        return map2.get(new Random().nextInt(map1.size()));
        //return 0;
    }



//
//    private List<Integer> list;
//    private Map<Integer,Integer> map;
//    java.util.Random rand = new java.util.Random();
//    /** Initialize your data structure here. */
//    public RandomizedSet() {
//        list=new ArrayList<>();
//        map=new HashMap<>();
//    }
//
//    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
//    public boolean insert(int val) {
//        if(map.containsKey(val)) return false;
//        map.put(val,list.size());
//        list.add(val);
//        return true;
//    }
//
//    /** Removes a value from the set. Returns true if the set contained the specified element. */
//    public boolean remove(int val) {
//        if(!map.containsKey(val)) return false;
//        int index=map.get(val);
//        if(index!=list.size()-1){
//            int last=list.get(list.size()-1);
//            list.set(index,last);
//            map.put(last,index);
//        }
//        map.remove(val);
//        list.remove(list.size()-1);
//        return true;
//    }
//
//    /** Get a random element from the set. */
//    public int getRandom() {
//        return list.get(rand.nextInt(list.size()));
//    }
}
