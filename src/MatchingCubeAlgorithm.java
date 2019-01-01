import java.util.ArrayList;
import java.util.HashMap;

public class MatchingCubeAlgorithm {
    private static final double THRESHOLD = 0.5;
    private static HashMap<Point3D, ArrayList<Triangle>> triangleStore = new HashMap<>();
    public static HashMap<Point3D, ArrayList<Triangle>> doMatchingCube(DataArray dataArray) {
        calculateMatrixVertex(dataArray);
        return triangleStore;
    }

    private static void calculateMatrixVertex(DataArray dataArray) {
        ArrayList<Triangle> triangleList;
        double[][][] matrix = dataArray.getMatrix();
        int xDim = dataArray.getxDim();
        int yDim = dataArray.getyDim();
        int zDim = dataArray.getzDim();
        for (int i = 0; i < xDim - 1; i++)
            for (int j = 0; j < yDim - 1; j++)
                for (int k = 0; k < zDim - 1; k++) {
                    Point3D point3D = new Point3D(i, j, k);
                    triangleList = calculateEachCell(matrix, point3D);
                    triangleStore.put(point3D, triangleList);
                }
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
        Point3D[] point3DList = new Point3D[] {
                new Point3D(xStart, yStart, zStart + 1),
                new Point3D(xStart, yStart + 1, zStart + 1),
                new Point3D(xStart, yStart + 1, zStart),
                new Point3D(xStart, yStart, zStart),
                new Point3D(xStart + 1, yStart, zStart + 1),
                new Point3D(xStart + 1, yStart + 1, zStart + 1),
                new Point3D(xStart + 1, yStart + 1, zStart),
                new Point3D(xStart + 1, yStart, zStart)
        };
        Line[] lineList = new Line[] {
                new Line(point3DList[0], point3DList[1]),
                new Line(point3DList[1], point3DList[2]),
                new Line(point3DList[2], point3DList[3]),
                new Line(point3DList[3], point3DList[0]),
                new Line(point3DList[4], point3DList[5]),
                new Line(point3DList[5], point3DList[6]),
                new Line(point3DList[6], point3DList[7]),
                new Line(point3DList[7], point3DList[4]),
                new Line(point3DList[0], point3DList[4]),
                new Line(point3DList[1], point3DList[5]),
                new Line(point3DList[2], point3DList[6]),
                new Line(point3DList[3], point3DList[7])
        };
        int[] triangles = Pattern.patternExample[matchNumber];
        int count = 0;
        Triangle triangle = new Triangle();
        for (int i = 0; i < triangles.length; i++) {
            if (triangles[i] == -1) break;
            count++;
            switch (count) {
//                case 4:
//                    count = 1;
                case 1:
                    triangle.setPointA(getMiddle(lineList[triangles[i]]));
                    break;
                case 2:
                    triangle.setPointB(getMiddle(lineList[triangles[i]]));
                    break;
                case 3:
                    triangle.setPointC(getMiddle(lineList[triangles[i]]));
                    count = 0;
                    triangleList.add(triangle);
                    triangle = new Triangle();
                    break;
            }
        }
        return triangleList;
    }

    private static Point3D getMiddle(Line line) {
        Point3D point3D = new Point3D(
                (line.getPointStart().getStartX() + line.getPointEnd().getStartX())/2,
                (line.getPointStart().getStartY() + line.getPointEnd().getStartY())/2,
                (line.getPointStart().getStartZ() + line.getPointEnd().getStartZ())/2);
        return point3D;
    }
}
