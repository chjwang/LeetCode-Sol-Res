import java.util.*;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 * 
 * Tags: Array, Sort
 */
class MergeIntervals {
    public static void main(String[] args) {
        
    }
    
    /**
     * Sort and merge, O(nlogn)
     * Sort the intervals according to their start value
     * Go through the intervals and update last interval
     * If last interval in result overlap with current interval
     * Remove last interval and add new interval with updated end value
     * Which is the bigger of last.end and i.end
     */
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<Interval>();

        if(intervals==null || intervals.size()==0)
            return result;

        Collections.sort(intervals, new MyComparator());

        Interval pre = intervals.get(0);
        for(int i=0; i<intervals.size(); i++){
            Interval curr = intervals.get(i);
            if(curr.start>pre.end){
                result.add(pre);
                pre = curr;
            }else{
                Interval merged = new Interval(pre.start, Math.max(pre.end, curr.end));
                pre = merged;
            }
        }
        result.add(pre);

        return result;
    }
    
    /**
     * Comparator for interval
     * Sort according to start date
     */
    class MyComparator implements Comparator<Interval> {
        
        @Override
//        public int compare(Interval i1, Interval i2) {
//            return i1.start - i2.start;
//        }
        public int compare(Interval i1, Interval i2){
            if(i1.start!=i2.start)
                return i1.start-i2.start;
            else
                return i1.end-i2.end;
        }
    }
    
    public class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }
}
