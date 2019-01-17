public class Point3D {
    private double startX, startY, startZ;

    public Point3D(double startX, double startY, double startZ) {
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getStartZ() {
        return startZ;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setStartZ(double startZ) {
        this.startZ = startZ;
    }
}
