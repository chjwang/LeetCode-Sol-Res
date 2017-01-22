/**
 火柴棍拼图数正方形拼图图片链接：http://matchstickpuzzles.blogspot.com/2011/06/55-4x4-square-how-many-squares.html

 把垂直的棍子和水平的棍子抽象成两个二位数组 boolean[][] ver boolean hor[][],然后算两个dp数组 int[][]dpV（垂直方向）, int[][]dpH（水平方向）,
 其中每个值表示截至当下点有几根连续木棍，最后对dpH逐点验证是否能以该棍为左上角第一根水平棍构成长度为1~n的正方形。

 Tag: TripAdvisor
 */
public class CountSquares {

    public int countSquares ( boolean[][] ver , boolean[][] hor ) {
        if ( ver == null || hor == null || ver.length == 0 || ver[0].length == 0 || hor.length == 0 || hor[0].length == 0 ){
            return 0;
        }
        int[][] dpV = new int[ ver.length ][ ver[0].length ];
        int[][] dpH = new int[ hor.length ][ hor[0].length ];
        int res = 0;

        for ( int j = 0; j < dpV[0].length; ++j  ) {
            for ( int i = 0; i < dpV.length; ++i ) {
                if ( ver[i][j] ) {
                    dpV[i][j] = i == 0 ? 1 : (dpV[i - 1][j] + 1);
                } else {
                    dpV[i][j] = 0;
                }
            }
        }
        for ( int i = 0; i < dpH.length; ++i  ) {
            for ( int j = 0; j < dpH[0].length; ++j ) {
                if ( hor[i][j] ) {
                    dpH[i][j] = j == 0 ? 1 : (dpH[i][j - 1] + 1);
                } else {
                    dpH[i][j] = 0;
                }
            }
        }

        for ( int i = 0; i < dpH.length; ++i ) {
            for ( int j = 0; j < dpH[0].length; ++j ) {
                for ( int l = 1; l <= Math.min(dpH[0].length - j , dpV.length - i ) ; ++l  ){
                    if ( dpH[i][ l + j - 1 ] >= l &&
                            dpH[i + l][ l + j - 1 ] >= l &&
                            dpV[i + l - 1][j ] >= l &&
                            dpV[ i + l - 1 ][ l + j ] >= l ) {
                        ++res;
                    }
                    if ( !(dpH[i][ l + j - 1 ] >= l) ) {
                        break;
                    }
                }
            }
        }
        return res;
    }

}
