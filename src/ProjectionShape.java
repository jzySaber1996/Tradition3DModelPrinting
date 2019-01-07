import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                for (Triangle triangle : triangles) {
                    double[] zList = new double[]
                            {
                                    triangle.getPointA().getZ(),
                                    triangle.getPointB().getZ(),
                                    triangle.getPointC().getZ()
                            };
                    boolean overLayer = false;
                    boolean belowLayer = false;
                    for (double coordinateZ : zList) {
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

    //Projecting the triangles and getting the point.
    private static void getProjectionPoint(double layerHeight) {
        pointPrintStore = new ArrayList<>();
        ArrayList<ArrayList<PointTriangle>> nearestPointsList = new ArrayList<>();
        PointTriangle startPoint = null;
        for (Triangle triangle : triangleSatisfied) {
            PointTriangle[] points = new PointTriangle[]{
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
                        if (Math.abs(points[i].getZ() - points[j].getZ()) >= 1e-6
                                && !ifExist(nearestPoints, getLayerPoint(layerHeight, points[i], points[j])))
                            nearestPoints.add(getLayerPoint(layerHeight, points[i], points[j]));
                    }
                }
            }
            nearestPointsList.add(nearestPoints);
//            if (pointPrintStore.size() == 0) {
//                startPoint = nearestPoints.get(0);
//                pointPrintStore.addAll(nearestPoints);
//            }
//            else {
//                for (PointTriangle pointUnchecked : nearestPoints) {
//                    if (ifSame(pointUnchecked, startPoint)) {
//                        pointPrintStore.add(pointUnchecked);
//                        break;
//                    }
//                    if (!ifExist(pointPrintStore, pointUnchecked))
//                        pointPrintStore.add(pointUnchecked);
//                }
//            }
        }
        int countPrint = 0;
        int indexNearest = 0;
        nearestPointsList = removeSame(nearestPointsList);
        PointTriangle boundaryPoint = null;
        int[] hasVisited = new int[1000];
        while (countPrint < nearestPointsList.size()) {
            if (pointPrintStore.size() == 0) {
                startPoint = nearestPointsList.get(indexNearest).get(0);
                pointPrintStore.addAll(nearestPointsList.get(indexNearest));
                if (nearestPointsList.get(indexNearest).size() == 1) {
                    boundaryPoint = startPoint;
                } else {
                    boundaryPoint = nearestPointsList.get(indexNearest).get(1);
                }
                hasVisited[0] = 1; //Mark the visited index
            } else {
                for (int i = 0; i < nearestPointsList.size(); i++) {
                    if (ifExist(nearestPointsList.get(i), boundaryPoint) && hasVisited[i] == 0) {
                        hasVisited[i] = 1;
                        indexNearest = i;
                        switch (nearestPointsList.get(i).size()) {
                            case 1:
                                break;
                            case 2:
                                int indexBoundary = 0;
                                for (int j = 0; j < nearestPointsList.get(i).size(); j++) {
                                    if (ifSame(nearestPointsList.get(i).get(j), boundaryPoint)) {
                                        indexBoundary = (j == 0) ? 1 : 0;
                                        break;
                                    }
                                }
                                boundaryPoint = nearestPointsList.get(i).get(indexBoundary);
                                pointPrintStore.add(boundaryPoint);
                                break;
                        }
                        break;
                    }
                }
            }
            countPrint++;
        }
        File file = new File("store/singlePointList.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            for (PointTriangle pointPrint : pointPrintStore) {
                fw.write(String.valueOf(pointPrint.getX()) + " " +
                        String.valueOf(pointPrint.getY()) + " " +
                        String.valueOf(pointPrint.getZ()) + "\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<ArrayList<PointTriangle>> removeSame(ArrayList<ArrayList<PointTriangle>> nearestPointList) {
        ArrayList<ArrayList<PointTriangle>> nearestResult = new ArrayList<>();
        for (ArrayList<PointTriangle> nearestUnchecked : nearestPointList) {
            ArrayList<PointTriangle> nearestTemp = new ArrayList<>();
            for (PointTriangle pointUnchecked : nearestUnchecked) {
                if (!ifExist(nearestTemp, pointUnchecked)) {
                    nearestTemp.add(pointUnchecked);
                }
            }
            nearestResult.add(nearestTemp);
        }
        return nearestResult;
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
//        if (Math.abs(zEnd - zStart) <= 1e-6) {
//            int temp = 0;
//        }
        double xResult = ((layerHeight - zStart) * xEnd + (zEnd - layerHeight) * xStart) / (zEnd - zStart);
        double yResult = ((layerHeight - zStart) * yEnd + (zEnd - layerHeight) * yStart) / (zEnd - zStart);
        pointHeight = new PointTriangle(xResult, yResult, layerHeight);
        return pointHeight;
    }

    private static boolean ifExist(ArrayList<PointTriangle> nearestPoints, PointTriangle pointChecked) {
        boolean result = false;
        for (PointTriangle pointUnchecked : nearestPoints) {
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
