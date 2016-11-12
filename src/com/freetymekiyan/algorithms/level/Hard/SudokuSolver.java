import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * Empty cells are indicated by the character '.'.
 * 
 * You may assume that there will be only one unique solution.
 * 
 * Tags: Backtracking, Hash Table
 */
class SudokuSolver {
    public static void main(String[] args) {
        char[][] board = {
            {'.', '.', '9', '7', '4', '8', '.', '.', '.'}, 
            {'7', '.', '.', '.', '.', '.', '.', '.', '.'}, 
            {'.', '2', '.', '1', '.', '9', '.', '.', '.'}, 
            {'.', '.', '7', '.', '.', '.', '2', '4', '.'}, 
            {'.', '6', '4', '.', '1', '.', '5', '9', '.'}, 
            {'.', '9', '8', '.', '.', '.', '3', '.', '.'}, 
            {'.', '.', '.', '8', '.', '3', '.', '2', '.'}, 
            {'.', '.', '.', '.', '.', '.', '.', '.', '6'}, 
            {'.', '.', '.', '2', '7', '5', '9', '.', '.'}
        };
        SudokuSolver s = new SudokuSolver();
        s.solveSudoku(board);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    public boolean solve(char[][] board){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] != '.')
                    continue;

                for(int k=1; k<=9; k++){
                    board[i][j] = (char) (k+'0');
                    if(isValid(board, i, j) && solve(board))
                        return true;
                    board[i][j] = '.';//backtrack
                }

                return false;
            }
        }

        return true; // does not matter
    }

    public boolean isValid(char[][] board, int i, int j){
        HashSet<Character> set = new HashSet<Character>();

        for(int k=0; k<9; k++){
            if(set.contains(board[i][k]))
                return false;

            if(board[i][k] != '.' ){
                set.add(board[i][k]);
            }
        }

        set.clear();

        for(int k=0; k<9; k++){
            if(set.contains(board[k][j]))
                return false;

            if(board[k][j]!='.' ){
                set.add(board[k][j]);
            }
        }

        set.clear();

        for(int m=0; m<3; m++){
            for(int n=0; n<3; n++){
                int x=i/3*3+m;
                int y=j/3*3+n;
                if(set.contains(board[x][y]))
                    return false;

                if(board[x][y]!='.'){
                    set.add(board[x][y]);
                }
            }
        }

        return true;
    }

// ////////////////////////////////////////////////
// OO design

    class Cell {
        private Optional<Byte> value = Optional.empty();
        private boolean editable = false;

        public Cell(Optional<Byte> value){
            this.value = value;
            if (! value.isPresent()) editable = true;
        }

        public Optional<Byte> getMark(){
            return value;
        }

        public boolean setMark(Optional<Byte> mark){
            if (! editable) return false;
            this.value = mark;
            return true;
        }
        public boolean isEditable(){
            return editable;
        }
    }

    class Constraint {
        private Set<Cell> cells = new HashSet<>();

        public Constraint() {}
        public Constraint(Cell ... cells) {
            add(cells);
        }

        public void add(Cell ... cells) {
            this.cells.addAll(Arrays.asList(cells));
        }

        public Set<Optional<Byte>> allMarks() {
            Set<Optional<Byte>> all = new HashSet<>();
            for( int i = 0; i < cells.size(); i++)
                all.add(Optional.of((byte)i));
            return all;
        }

        public boolean isViolated() {
            Set<Optional<Byte>> marks = allMarks();
            for (Cell cell: cells)
                if (cell.getMark().isPresent() && ! marks.remove(cell.getMark())) return true;
            return false;
        }
        public boolean isSatisfied(){
            Set<Optional<Byte>> marks = allMarks();
            marks.remove(Optional.empty());
            for(Cell cell: cells)
                if(!cell.getMark().isPresent() || !marks.remove(cell.getMark())) return false;
            return true;
        }
    }

    class Board {
        static final int DEFAULT_SIZE = 9;

        public Cell [][] cells;
        Set<Constraint> constraints;

        public Board(String contents){
            this(DEFAULT_SIZE, contents);
        }

        public Board(int size, String contents){

            cells = new Cell[size][size];
            constraints = new HashSet<>(3*size);

            List<Constraint> rows = new ArrayList<>(size),
                    columns = new ArrayList<>(size),
                    blocks = new ArrayList<>(size);

            for(int i = 0; i < size; i++){
                rows.add(new Constraint());
                columns.add(new Constraint());
                blocks.add(new Constraint());
            }

            int sqsize = (int)Math.sqrt(size);

            for(int i = 0; i < contents.length(); i++){
                Cell cell = new Cell(markFor(contents.charAt(i), DEFAULT_SIZE));

                cells[i/size][i%size] = cell;
                rows.get(i/size).add(cell);
                columns.get(i%size).add(cell);
                blocks.get(i/sqsize + sqsize*i%sqsize).add(cell);
            }

            constraints.addAll(rows);
            constraints.addAll(columns);
            constraints.addAll(blocks);
        }

        Optional<Byte> markFor(char ch, int range){
            if(ch == ' ')
                return Optional.empty();
            else
                return Optional.of(Byte.valueOf(String.valueOf(ch), range));
        }


        public Set<Constraint> constraintsViolated(){
            return constraints.stream()
                    .filter(c->c.isViolated())
                    .collect(Collectors.toCollection(HashSet::new));
        }

        public Set<Constraint> constraintsNotSatisfied(){
            return constraints.stream()
                    .filter(c-> ! c.isSatisfied())
                    .collect(Collectors.toCollection(HashSet::new));
        }
    }


    // wikipedia implementation
    public class Sudoku {
        private int mBoard[][];
        private int mBoardSize;
        private int mBoxSize;
        private boolean mRowSubset[][];
        private boolean mColSubset[][];
        private boolean mBoxSubset[][];

        public Sudoku(int board[][]) {
            mBoard = board;
            mBoardSize = mBoard.length;
            mBoxSize = (int)Math.sqrt(mBoardSize);
        }

        public void initSubsets() {
            mRowSubset = new boolean[mBoardSize][mBoardSize];
            mColSubset = new boolean[mBoardSize][mBoardSize];
            mBoxSubset = new boolean[mBoardSize][mBoardSize];
            for(int i = 0; i < mBoard.length; i++) {
                for(int j = 0; j < mBoard.length; j++) {
                    int value = mBoard[i][j];
                    if(value != 0) {
                        setSubsetValue(i, j, value, true);
                    }
                }
            }
        }

        private void setSubsetValue(int i, int j, int value, boolean present) {
            mRowSubset[i][value - 1] = present;
            mColSubset[j][value - 1] = present;
            mBoxSubset[computeBoxNo(i, j)][value - 1] = present;
        }

        public boolean solve() {
            return solve(0, 0);
        }

        public boolean solve(int i, int j) {
            if(i == mBoardSize) {
                i = 0;
                if(++j == mBoardSize) {
                    return true;
                }
            }
            if(mBoard[i][j] != 0) {
                return solve(i + 1, j);
            }
            for(int value = 1; value <= mBoardSize; value++) {
                if(isValid(i, j, value)) {
                    mBoard[i][j] = value;
                    setSubsetValue(i, j, value, true);
                    if(solve(i + 1, j)) {
                        return true;
                    }
                    setSubsetValue(i, j, value, false);
                }
            }

            mBoard[i][j] = 0;
            return false;
        }

        private boolean isValid(int i, int j, int val) {
            val--;
            boolean isPresent = mRowSubset[i][val] || mColSubset[j][val] || mBoxSubset[computeBoxNo(i, j)][val];
            return !isPresent;
        }

        private int computeBoxNo(int i, int j) {
            int boxRow = i / mBoxSize;
            int boxCol = j / mBoxSize;
            return boxRow * mBoxSize + boxCol;
        }

        public void print() {
            for(int i = 0; i < mBoardSize; i++) {
                if(i % mBoxSize == 0) {
                    System.out.println(" -----------------------");
                }
                for(int j = 0; j < mBoardSize; j++) {
                    if(j % mBoxSize == 0) {
                        System.out.print("| ");
                    }
                    System.out.print(mBoard[i][j] != 0 ? ((Object) (Integer.valueOf(mBoard[i][j]))) : " ");
                    System.out.print(' ');
                }

                System.out.println("|");
            }

            System.out.println(" -----------------------");
        }
    }
}