public class Triangle {
    private PointTriangle pointA, pointB, pointC;

//    public Triangle(Point3D pointA, Point3D pointB, Point3D pointC) {
//        this.pointA = pointA;
//        this.pointB = pointB;
//        this.pointC = pointC;
//    }

    public void setPointA(PointTriangle pointA) {
        this.pointA = pointA;
    }

    public void setPointB(PointTriangle pointB) {
        this.pointB = pointB;
    }

    public void setPointC(PointTriangle pointC) {
        this.pointC = pointC;
    }

    public PointTriangle getPointA() {
        return pointA;
    }

    public PointTriangle getPointB() {
        return pointB;
    }

    public PointTriangle getPointC() {
        return pointC;
    }
}
