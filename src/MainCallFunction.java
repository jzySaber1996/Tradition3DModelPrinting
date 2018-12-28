import java.io.IOException;

public class MainCallFunction {
    public static void main(String[] args) throws IOException {
        double[][][] matrix = ReadFileFromMat.readFile();
        MatchingCubeAlgorithm.doMatchingCube(matrix);
    }
}
