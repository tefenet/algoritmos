import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class BiggerStain {
    private static final short[] X_MOVE = { 0, 0, 1, -1 };
    private static final short[] Y_MOVE = { 1, -1, 0, 0 };
    private final int height;
    private final int width;
    private final byte[][] input;
    private final boolean[][] visited;

    public BiggerStain(byte[][] input) {
        this.input = input;
        height = input.length;
        width = input[0].length;
        visited = new boolean[height][width];
    }

    private static class Pixel {
        final int row;
        final int column;

        Pixel(int row, int column) {
            this.row = row;
            this.column = column;            
        }
        public int getRowIndex() {
            return row;
        }
        public int getColumnIndex() {
            return column;
        }
    }
    public List<Pixel> getNeighbours(Pixel pixel) {
        List<Pixel> myNeighbours = new ArrayList<>();
        IntStream.range(0, 4).forEach(position -> {
            int newRow = pixel.getRowIndex() + Y_MOVE[position];
            int newColumn = pixel.getColumnIndex() + X_MOVE[position];
            if (newRow < height && newRow >= 0 && newColumn >= 0 && newColumn < width){
                myNeighbours.add(new Pixel(newRow, newColumn));
            }
        });
        return myNeighbours;
    }
    
    public boolean areTheSameColor(Pixel pixel, Pixel otherPixel) {
        return input[pixel.getRowIndex()][pixel.getColumnIndex()] == 
                input[otherPixel.getRowIndex()][otherPixel.getColumnIndex()];
    }

    private void setVisited(Pixel pixel) {
        visited[pixel.getRowIndex()][pixel.getColumnIndex()] = true;
    }

    private boolean isVisited(Pixel pixel) {
        return visited[pixel.getRowIndex()][pixel.getColumnIndex()];
    }

    private int bfs(Pixel rootPixel) {
        int stainSize = 0;
        LinkedList<Pixel> remainingPixels = new LinkedList<>();
        remainingPixels.add(rootPixel);
        while (!remainingPixels.isEmpty()) {
            Pixel currentPixel = remainingPixels.poll();
            stainSize++;
            getNeighbours(currentPixel).stream().filter(
                    neighbourPixel -> areTheSameColor(neighbourPixel,currentPixel) && !isVisited(neighbourPixel))
                    .forEach(neighbourPixel -> {
                        setVisited(neighbourPixel);
                        remainingPixels.add(neighbourPixel);
                    });
        }
        return stainSize;
    }    
    private static class Stain{
        int color;
        int size;
        void setSize(int size){
            this.size=size;
        }
        void setColor(int color){
            this.color=color;
        }
        int getSize(){
            return size;
        }
        int getColor(){
            return color;
        }

    }
    Stain computeBiggerStain() {
        Stain biggerStain = new Stain();
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (!visited[row][column]) {
                    int stainSize;
                    visited[row][column] = true;
                    if ((stainSize = bfs(new Pixel(row, column))) > biggerStain.getSize()) {
                        biggerStain.setSize(stainSize);
                        biggerStain.setColor(input[row][column]);
                    }
                }
            }
        }
        return biggerStain;
    }    

    private static void findMaxStainOnBoard(final List<byte[]> board, StringBuilder caseNumber) {
        byte[][] input = board.toArray(new byte[board.size()][board.get(0).length]);
        Stain biggerStain = new BiggerStain(input).computeBiggerStain();
        System.out.println(caseNumber + ".  (\"" + (char) biggerStain.getColor() + "\" , " + biggerStain.getSize() + ")");
    }

    public static void main(String[] pArgs) throws IOException {
        try (Stream<String> inputLines = Files.lines(Paths.get(pArgs[0]))) {
            final List<byte[]> board = new ArrayList<>();
            StringBuilder caseNumber = new StringBuilder();
            inputLines.forEach(line -> {
                if (line.charAt(0) == '#') {
                    if (!board.isEmpty()) {
                        findMaxStainOnBoard(board, caseNumber);
                        board.clear();
                        caseNumber.setLength(0);
                    }
                    String[] newCaseLine= Pattern.compile(" ").split(line);
                    caseNumber.append(newCaseLine[newCaseLine.length-1]);                    
                } else {
                    board.add(line.getBytes());
                }
            });
            findMaxStainOnBoard(board, caseNumber);
        }
    }
}
