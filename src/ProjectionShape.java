import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class ProjectionShape {
    private static HashMap<Point3D, ArrayList<Triangle>> thisTriangleStore;
    private static ArrayList<Triangle> triangleSatisfied;
    private static ArrayList<PointTriangle> pointPrintStore;
    static void selectPoints(HashMap<Point3D, ArrayList<Triangle>> triangleStore) {
        thisTriangleStore = triangleStore;
        getSingleLayerPoint(0.5);
        getProjectionPoint(0.5);
    }

    private static void getSingleLayerPoint(double layerHeight) {
        triangleSatisfied = new ArrayList<>();
        Iterator iterator = thisTriangleStore.entrySet().iterator();
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
                        if (Math.abs(coordinateZ - layerHeight) <= 1e-6 ||
                                (coordinateZ - layerHeight) > 1e-6) {
                            overLayer = true;
                        }
                        if (Math.abs(coordinateZ - layerHeight) <= 1e-6 ||
                                (layerHeight - coordinateZ) > 1e-6) {
                            belowLayer = true;
                        }
                    }
                    if (overLayer && belowLayer) {
                        triangleSatisfied.add(triangle);
                    }
                }
            }
        }
    }
    private static void getProjectionPoint(double layerHeight) {
        pointPrintStore = new ArrayList<>();
        PointTriangle startPoint = null;
        for (Triangle triangle: triangleSatisfied) {
            PointTriangle[] points = new PointTriangle[] {
                    triangle.getPointA(),
                    triangle.getPointB(),
                    triangle.getPointC()
            };
            ArrayList<PointTriangle> nearestPoints = new ArrayList<>();
            for (int i = 0; i < points.length; i++) {
                for (int j = 1; j < points.length; j++) {
                    double iOverHeight = points[i].getZ() - layerHeight;
                    double jOverHeight = points[j].getZ() - layerHeight;
                    double testValue = iOverHeight * jOverHeight;
                    if (Math.abs(testValue) <= 1e-6 || testValue <= -1e-6) {
                        if (!ifExist(nearestPoints, getLayerPoint(layerHeight, points[i], points[j])))
                            nearestPoints.add(getLayerPoint(layerHeight, points[i], points[j]));
                    }
                }
            }
            if (pointPrintStore.size() == 0) {
                startPoint = nearestPoints.get(0);
                pointPrintStore.addAll(nearestPoints);
            }
            else {
                for (PointTriangle pointUnchecked : nearestPoints) {
                    if (ifSame(pointUnchecked, startPoint)) {
                        pointPrintStore.add(pointUnchecked);
                        break;
                    }
                    if (!ifExist(pointPrintStore, pointUnchecked))
                        pointPrintStore.add(pointUnchecked);
                }
            }
        }
//        int z = 0;
    }

    private static PointTriangle getLayerPoint(double layerHeight,
                                               PointTriangle pointStart, PointTriangle pointEnd) {
        PointTriangle pointHeight;
        double xStart = pointStart.getX();
        double yStart = pointStart.getY();
        double zStart = pointStart.getZ();
        double xEnd = pointEnd.getX();
        double yEnd = pointEnd.getY();
        double zEnd = pointEnd.getZ();
        double xResult = ((layerHeight - zStart) * xEnd + (zEnd - layerHeight) * xStart) / (zEnd - zStart);
        double yResult = ((layerHeight - zStart) * yEnd + (zEnd - layerHeight) * yStart) / (zEnd - zStart);
        pointHeight = new PointTriangle(xResult, yResult, layerHeight);
        return pointHeight;
    }

    private static boolean ifExist(ArrayList<PointTriangle> nearestPoints, PointTriangle pointChecked) {
        boolean result = false;
        for (PointTriangle pointUnchecked: nearestPoints) {
            if (ifSame(pointUnchecked, pointChecked)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private static boolean ifSame(PointTriangle pointUnchecked, PointTriangle pointChecked) {
        boolean result = false;
        if (Math.abs(pointUnchecked.getX() - pointChecked.getX()) < 1e-6 &&
                Math.abs(pointUnchecked.getY() - pointChecked.getY()) < 1e-6 &&
                Math.abs(pointUnchecked.getZ() - pointChecked.getZ()) < 1e-6) {
            result = true;
        }
        return result;
    }
}
