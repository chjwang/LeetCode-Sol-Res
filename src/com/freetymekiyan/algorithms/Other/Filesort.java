package com.freetymekiyan.algorithms.Other;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Filesort {
    /**
     * 题目是给一个 string[]，第一个是“latest”或者“largest”， 第二个是一个int代表返回结果的数量，
     * 第三个开始每个 string 都是”文件名,最后修改时间,文件大小“，要求根据latest 或者 largest 排序。
     *
     * 比如要求 latest 排序，如果最后修改时间一样，就按文件大小排序，如果文件大小也一样，就按文件名排序。
     * 如果文件名也一样要怎么处理了
     */

    enum FileComparator implements Comparator<String> {
        NAME_SORT {
            @Override
            public int compare(String s1, String s2) {
                String[] s1s = s1.split(",");
                String[] s2s = s2.split(",");
                return s1s[0].trim().compareTo(s2s[0].trim());
            }
        },
        LATEST_SORT {
            @Override
            public int compare(String s1, String s2) {
                String[] s1s = s1.split(",");
                String[] s2s = s2.split(",");

//                Date d1 = new Date(Long.parseLong(s1s[1]));
//                Date d2 = new Date(Long.parseLong(s2s[1]));
//                return d2.compareTo(d1);

                Long d1 = Long.parseLong(s1s[1].trim());
                Long d2 = Long.parseLong(s2s[1].trim());
                return d2.compareTo(d1);
            }
        },
        SIZE_SORT {
            @Override
            public int compare(String s1, String s2) {
                String[] s1s = s1.split(",");
                String[] s2s = s2.split(",");
                Long size1 = Long.parseLong(s1s[2].trim());
                Long size2 = Long.parseLong(s2s[2].trim());
                return size2.compareTo(size1);
            }
        };
//
//        public static Comparator<String> decending(final Comparator<String> other) {
//            return new Comparator<String>() {
//                public int compare(String o1, String o2) {
//                    return -1 * other.compare(o1, o2);
//                }
//            };
//        }

        public static Comparator<String> getComparator(final FileComparator... multipleComparators) {
            return new Comparator<String>() {
                public int compare(String o1, String o2) {
                    for (FileComparator comparator : multipleComparators) {
                        int result = comparator.compare(o1, o2);
                        if (result != 0) {
                            return result;
                        }
                    }
                    return 0;
                }
            };
        }
    }

    public String[] sortFile(String[] files) {

        if (files == null || files.length < 3)
            return files;

        Comparator fc = null;
        String sortCriteria = files[0];
        if (sortCriteria.equals("latest")) {
            fc = FileComparator.getComparator(
                    FileComparator.LATEST_SORT,
                    FileComparator.SIZE_SORT,
                    FileComparator.NAME_SORT);
        } else if (sortCriteria.equals("largest")) {
            fc = FileComparator.getComparator(
                    FileComparator.SIZE_SORT,
                    FileComparator.LATEST_SORT,
                    FileComparator.NAME_SORT);
        } else {
            throw new IllegalArgumentException("invalid sort criteria");
        }

        String[] fileArray = Arrays.copyOfRange(files, 2, files.length);
        Arrays.sort(fileArray, fc);
        return fileArray;
    }

}
