//Main interpolation algorithm
public class TrilinearInterpAlgorithm {
    private static double value;
    private static double localX, localY, localZ;
    private static int xBase, yBase, zBase;
    private static double[][][] thisMatrix;

    public static double interpolation(double[][][] matrix, double xPos, double yPos, double zPos) {
        thisMatrix = matrix;
        localX = xPos;
        localY = yPos;
        localZ = zPos;
        xBase = (int) xPos;
        yBase = (int) yPos;
        zBase = (int) zPos;
        doCalculate();
        return value;
    }

    private static void doCalculate() {
        double deltaX = localX - (double) xBase;
        double deltaY = localY - (double) yBase;
        double deltaZ = localZ - (double) zBase;
        double i1 = thisMatrix[xBase][yBase][zBase] * (1 - deltaZ)
                + thisMatrix[xBase][yBase][zBase + 1] * deltaZ;
        double i2 = thisMatrix[xBase][yBase + 1][zBase] * (1 - deltaZ)
                + thisMatrix[xBase][yBase + 1][zBase + 1] * deltaZ;
        double j1 = thisMatrix[xBase + 1][yBase][zBase] * (1 - deltaZ)
                + thisMatrix[xBase + 1][yBase][zBase + 1] * deltaZ;
        double j2 = thisMatrix[xBase + 1][yBase + 1][zBase] * (1 - deltaZ)
                + thisMatrix[xBase + 1][yBase + 1][zBase + 1] * deltaZ;
        double w1 = i1 * (1 - deltaY) + i2 * deltaY;
        double w2 = j1 * (1 - deltaY) + j2 * deltaY;
        value = w1 * (1 - deltaX) + w2 * deltaX;
    }
}
