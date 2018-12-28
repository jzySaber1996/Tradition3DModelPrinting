public class Triangle {
    private Point3D pointA, pointB, pointC;
    public Triangle(Point3D pointA, Point3D pointB, Point3D pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }

    public void setPointA(Point3D pointA) {
        this.pointA = pointA;
    }

    public void setPointB(Point3D pointB) {
        this.pointB = pointB;
    }

    public void setPointC(Point3D pointC) {
        this.pointC = pointC;
    }

    public Point3D getPointA() {
        return pointA;
    }

    public Point3D getPointB() {
        return pointB;
    }

    public Point3D getPointC() {
        return pointC;
    }
}
