public class Line {
    private Point3D pointStart, pointEnd;

    public Line(Point3D pointStart, Point3D pointEnd) {
        this.pointStart = pointStart;
        this.pointEnd = pointEnd;
    }

    public Point3D getPointStart() {
        return pointStart;
    }

    public Point3D getPointEnd() {
        return pointEnd;
    }

    public void setPointStart(Point3D pointStart) {
        this.pointStart = pointStart;
    }

    public void setPointEnd(Point3D pointEnd) {
        this.pointEnd = pointEnd;
    }
}
