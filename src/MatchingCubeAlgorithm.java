import java.util.ArrayList;
import java.util.HashMap;

class MatchingCubeAlgorithm {
    private static final double THRESHOLD = 0.5;
    private static HashMap<Point3D, ArrayList<Triangle>> triangleStore = new HashMap<>();

    static HashMap<Point3D, ArrayList<Triangle>> doMatchingCube(DataArray dataArray) {
        calculateMatrixVertex(dataArray);
        return triangleStore;
    }

    private static void calculateMatrixVertex(DataArray dataArray) {
        ArrayList<Triangle> triangleList;
        double[][][] matrix = dataArray.getMatrix();
        int xDim = dataArray.getxDim();
        int yDim = dataArray.getyDim();
        int zDim = dataArray.getzDim();
        for (double i = 0; i < (double) xDim; i += 0.25)
            for (double j = 0; j < (double) yDim; j += 0.25)
                for (double k = 0; k < (double) zDim; k += 0.25) {
                    Point3D point3D = new Point3D(i, j, k);
                    triangleList = calculateEachCell(matrix, point3D);
                    triangleStore.put(point3D, triangleList);
                }
    }

    private static ArrayList<Triangle> calculateEachCell(double[][][] matrix, Point3D pointStart) {
        ArrayList<Triangle> triangleList = new ArrayList<>();
        int[] binaryNumber = new int[8];
        double xStart = pointStart.getStartX();
        double yStart = pointStart.getStartY();
        double zStart = pointStart.getStartZ();
        double[] yList = new double[]{yStart, yStart + 0.25, yStart + 0.25, yStart};
        double[] zList = new double[]{zStart + 0.25, zStart + 0.25, zStart, zStart};
        int numIndex = 0;
        for (double i = xStart; i <= xStart + 0.25; i += 0.25) {
            for (int j = 0; j < yList.length; j++) {
                if (TrilinearInterpAlgorithm.interpolation(matrix, i, yList[j], zList[j]) >= THRESHOLD)
                    binaryNumber[numIndex] = 1;
                else binaryNumber[numIndex] = 0;
                numIndex++;
            }
        }
        int matchNumber = 0;
        for (int i = 0; i < 8; i++) {
            matchNumber += Math.pow(2, i) * binaryNumber[i];
        }
        Point3D[] point3DList = new Point3D[]{
                new Point3D(xStart, yStart, zStart + 0.25),
                new Point3D(xStart, yStart + 0.25, zStart + 0.25),
                new Point3D(xStart, yStart + 0.25, zStart),
                new Point3D(xStart, yStart, zStart),
                new Point3D(xStart + 0.25, yStart, zStart + 0.25),
                new Point3D(xStart + 0.25, yStart + 0.25, zStart + 0.25),
                new Point3D(xStart + 0.25, yStart + 0.25, zStart),
                new Point3D(xStart + 0.25, yStart, zStart)
        };
        Line[] lineList = new Line[]{
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

    private static PointTriangle getMiddle(Line line) {
        PointTriangle pointTriangle = new PointTriangle(
                (line.getPointStart().getStartX() + line.getPointEnd().getStartX()) / 2.0,
                (line.getPointStart().getStartY() + line.getPointEnd().getStartY()) / 2.0,
                (line.getPointStart().getStartZ() + line.getPointEnd().getStartZ()) / 2.0);
//        Point3D point3D = new Point3D(
//                (line.getPointStart().getStartX() + line.getPointEnd().getStartX())/2,
//                (line.getPointStart().getStartY() + line.getPointEnd().getStartY())/2,
//                (line.getPointStart().getStartZ() + line.getPointEnd().getStartZ())/2);
        return pointTriangle;
    }
}
