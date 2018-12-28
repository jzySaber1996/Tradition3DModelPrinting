import java.util.ArrayList;
import java.util.HashMap;

public class MatchingCubeAlgorithm {
    private static final double THRESHOLD = 0.5;
    private HashMap<Point3D, ArrayList<Triangle>> trangleStore = new HashMap<>();
    public static void doMatchingCube(double[][][] matrix) {
        calculateMatrixVertex(matrix);
    }

    private static void calculateMatrixVertex(double[][][] matrix) {
        for (int i = 0; i < Pattern.LENGTH_MATRIX - 1; i++)
            for (int j = 0; j < Pattern.LENGTH_MATRIX - 1; j++)
                for (int k = 0; k < Pattern.LENGTH_MATRIX - 1; k++)
                    calculateEachCell(matrix, new Point3D(i, j, k));
    }

    private static ArrayList<Triangle> calculateEachCell(double[][][] matrix, Point3D pointStart) {
        ArrayList<Triangle> triangleList = new ArrayList<>();
        int[] binaryNumber = new int[8];
        int xStart = pointStart.getStartX();
        int yStart = pointStart.getStartY();
        int zStart = pointStart.getStartZ();
        int[] yList = new int[] {yStart, yStart + 1, yStart + 1, yStart};
        int[] zList = new int[] {zStart + 1, zStart + 1, zStart, zStart};
        int numIndex = 0;
        for (int i = xStart; i <= xStart + 1; i++) {
            for (int j = 0; j < yList.length; j++) {
                if (matrix[i][yList[j]][zList[j]] >= THRESHOLD) binaryNumber[numIndex] = 1;
                else binaryNumber[numIndex] = 0;
                numIndex++;
            }
        }
        int matchNumber = 0;
        for (int i = 0; i < 8; i++) {
            matchNumber += Math.pow(2, i) * binaryNumber[i];
        }
        int[] triangles = Pattern.patternExample[matchNumber];

        return triangleList;
    }
}
