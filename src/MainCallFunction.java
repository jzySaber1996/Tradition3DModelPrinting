import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainCallFunction {
    public static void main(String[] args) throws IOException {
        DataArray dataArray = ReadFileFromMat.readFile();
        HashMap<Point3D, ArrayList<Triangle>> triangleStore = MatchingCubeAlgorithm.doMatchingCube(dataArray);
        Iterator iterator = triangleStore.entrySet().iterator();
        File file = new File("store/pointlist.txt");
        if (!file.exists())
            file.createNewFile();
        FileWriter fw = new FileWriter(file, true);
//        fw.write("");
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Point3D pointStart = (Point3D) entry.getKey();
            ArrayList<Triangle> triangles = (ArrayList<Triangle>) entry.getValue();

            if (triangles.size() > 0) {
                for (Triangle triangle: triangles) {
                    double[] zList = new double[]
                            {
                                    triangle.getPointA().getZ(),
                                    triangle.getPointB().getZ(),
                                    triangle.getPointC().getZ()
                            };
                    boolean overLayer = false;
                    boolean belowLayer = false;
                    for (double coordinateZ: zList) {
                        if (Math.abs(coordinateZ - 0.5) <= 1e-6 ||
                                (coordinateZ - 0.5) > 1e-6) {
                            overLayer = true;
                        }
                        if (Math.abs(coordinateZ - 0.5) <= 1e-6 ||
                                (0.5 - coordinateZ) > 1e-6) {
                            belowLayer = true;
                        }
                    }
                    if (overLayer && belowLayer) {
                        ArrayList<PointTriangle> pointTriangles = new ArrayList<>();
                        pointTriangles.add(triangle.getPointA());
                        pointTriangles.add(triangle.getPointB());
                        pointTriangles.add(triangle.getPointC());
                        for (PointTriangle pointEach: pointTriangles) {
                            fw.write(String.valueOf(pointEach.getX()) + " " +
                                    String.valueOf(pointEach.getY()) + " " +
                                    String.valueOf(pointEach.getZ()) + "\n");
                        }
                        fw.write("-1\n");
                    }
                }
            }
        }
        fw.flush();
        fw.close();
        ProjectionShape.selectPoints(triangleStore);
//        int k = 0;
    }
}
