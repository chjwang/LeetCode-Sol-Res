package com.freetymekiyan.algorithms.level.Medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 */
public class MeetingRoom {

    public int minMeetingRooms(Interval[] intervals) {
        if (intervals == null || intervals.length == 0)
            return 0;

        // Sort the intervals by start time
        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) { return a.start - b.start; }
        });

        // Use a min heap to track the minimum end time of merged intervals
        PriorityQueue<Interval> heap = new PriorityQueue<Interval>(intervals.length, new Comparator<Interval>() {
            public int compare(Interval a, Interval b) { return a.end - b.end; }
        });

        // start with the first meeting, put it to a meeting room
        heap.offer(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            // get the meeting room that finishes earliest
            Interval interval = heap.poll();

            if (intervals[i].start >= interval.end) {
                // if the current meeting starts right after
                // there's no need for a new room, merge the interval
                interval.end = intervals[i].end;
            } else {
                // otherwise, this meeting needs a new room
                heap.offer(intervals[i]);
            }

            // don't forget to put the meeting room back
            heap.offer(interval);
        }

        return heap.size();
    }

    public int minMeetingRooms1(Interval[] intervals) {
        if(intervals == null || intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> (a.start - b.start));
        int max = 0;
        PriorityQueue<Interval> queue = new PriorityQueue<>(intervals.length, (a, b) -> (a.end - b.end));
        for(int i = 0; i < intervals.length; i++){
            while(!queue.isEmpty() && intervals[i].start >= queue.peek().end)
                queue.poll();
            queue.offer(intervals[i]);
            max = Math.max(max, queue.size());
        }
        return max;
    }



    public int minMeetingRooms2(Interval[] intervals) {
        Arrays.sort(intervals, new intervalComparator());
        List<List<Interval>> list = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int idx = findIdx(list, intervals[i]);
            if (list.size() == 0 || idx == -1) {
                List<Interval> tmp = new ArrayList<>();
                tmp.add(intervals[i]);
                list.add(tmp);
            } else {
                list.get(idx).add(intervals[i]);
            }
        }
        return list.size();
    }

    public int findIdx(List<List<Interval>> list, Interval interval) {
        int idx = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (interval.start >= list.get(i).get(list.get(i).size() - 1).end) {
                return i;
            }
        }
        return idx;
    }

    class intervalComparator implements Comparator<Interval> {
        public int compare(Interval o1, Interval o2) {
            return o1.start - o2.start;
        }
    }


    /**
     * put start and negative end into list, sort it based on the absolute value.
     * use two variables to record minimum meeting room
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms3(Interval[] intervals) {
        int res = 0;
        if(intervals.length == 0) return res;
        if(intervals.length == 1) return res+1;
        List<Integer> points = new ArrayList<>();
        for(int i=0;i<intervals.length;i++) {
            points.add(intervals[i].start);
            points.add(-intervals[i].end);
        }
        Collections.sort(points, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return Math.abs(o1) - Math.abs(o2);
            }
        });

        int local = 0;
        for(int i=0;i<points.size();i++) {
            if(points.get(i)>=0)
                local++;
            else
                local--;
            res = Math.max(local, res);
        }

        return res;
    }

    public int minMeetingRooms4(Interval[] intervals) {
        int n=intervals.length;
        int[] start=new int[n];
        int[] end=new int[n];
        for (int i=0; i<n; i++) {
            start[i]=intervals[i].start;
            end[i]=intervals[i].end;
        }
        Arrays.sort(start);
        Arrays.sort(end);
        int i=0, j=0, res=0;
        while (i<n) {
            if (start[i]<end[j]) i++;
            else if (start[i]>end[j]) j++;
            else {
                i++;
                j++;
            }
            res=Math.max(res,i-j);
        }
        return res;
    }

    public class Interval {

        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

}
