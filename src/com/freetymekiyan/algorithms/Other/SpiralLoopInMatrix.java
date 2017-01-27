package com.freetymekiyan.algorithms.Other;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 loop through the elements of an NxM matrix (N and M are odd) in a spiral way starting from a point.
 */
public class SpiralLoopInMatrix {

  private enum Direction {
    E(1, 0) {
      Direction next() {
        return N;
      }
    },
    N(0, 1) {
      Direction next() {
        return W;
      }
    },
    W(-1, 0) {
      Direction next() {
        return S;
      }
    },
    S(0, -1) {
      Direction next() {
        return E;
      }
    },;

    private int dx;
    private int dy;

    Point advance(Point point) {
      return new Point(point.x + dx, point.y + dy);
    }

    abstract Direction next();

    Direction(int dx, int dy) {
      this.dx = dx;
      this.dy = dy;
    }
  }

  private final static Point ORIGIN = new Point(0, 0);
  private final int width;
  private final int height;
  private Point point;
  private Direction direction = Direction.E;
  private List<Point> list = new ArrayList<>();

  public SpiralLoopInMatrix(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public List<Point> spiral() {
    point = ORIGIN;
    int steps = 1;
    while (list.size() < width * height) {
      advance(steps);
      advance(steps);
      steps++;
    }
    return list;
  }

  private void advance(int n) {
    for (int i = 0; i < n; ++i) {
      if (inBounds(point)) {
        list.add(point);
      }
      point = direction.advance(point);
    }
    direction = direction.next();
  }

  private boolean inBounds(Point p) {
    return between(-width / 2, width / 2, p.x) && between(-height / 2, height / 2, p.y);
  }

  private static boolean between(int low, int high, int n) {
    return low <= n && n <= high;
  }

////////////////////////////

  boolean shouldTurn(int row, int col, int height, int width) {

    int same = 1;

    if (row > height - 1 - row) {
      row = height - 1 - row;
      same = 0; // Give precedence to top-left over bottom-left
    }

    if (col >= width - 1 - col) {
      col = width - 1 - col;
      same = 0; // Give precedence to top-right over top-left
    }

    row -= same; // When the row and col doesn't change, this will reduce row by 1
    return row == col;

  }

  int directions[][] = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 4 * 2

  void printSpiral(int arr[][], int height, int width) {
    int directionIdx = 0, i = 0;
    int curRow = 0, curCol = 0;

    for (i = 0; i < height * width; i++) {
      System.out.print(arr[curRow][curCol]);

      if (shouldTurn(curRow, curCol, height, width)) {
        directionIdx = (directionIdx + 1) % directions.length;
      }
      curRow += directions[directionIdx][0];
      curCol += directions[directionIdx][1];
    }
    System.out.println();
  }


  void print_spiral(int[][] matrix) {
    int size = matrix.length;

    int x = 0; // current position; x
    int y = 0; // current position; y
    int d = 0; // current direction; 0=RIGHT, 1=DOWN, 2=LEFT, 3=UP
    int c = 0; // counter
    int s = 1; // chain size

    // starting point
    x = ((int) Math.floor(size / 2.0)) - 1;
    y = ((int) Math.floor(size / 2.0)) - 1;

    for (int k = 1; k <= (size - 1); k++) {
      for (int j = 0; j < (k < (size - 1) ? 2 : 3); j++) {
        for (int i = 0; i < s; i++) {
          System.out.print(matrix[x][y] + " ");
          c++;

          switch (d) {
            case 0: y = y + 1; break;
            case 1: x = x + 1; break;
            case 2: y = y - 1; break;
            case 3: x = x - 1; break;
          }
        }
        d = (d + 1) % 4;
      }
      s = s + 1;
    }
  }



}