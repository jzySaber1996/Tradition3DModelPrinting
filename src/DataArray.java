public class DataArray {
    private double[][][] matrix;
    private int xDim, yDim, zDim;
    public DataArray(double[][][] matrix, int xDim, int yDim, int zDim) {
        this.matrix = matrix;
        this.xDim = xDim;
        this.yDim = yDim;
        this.zDim = zDim;
    }

    public double[][][] getMatrix() {
        return matrix;
    }

    public int getxDim() {
        return xDim;
    }

    public int getyDim() {
        return yDim;
    }

    public int getzDim() {
        return zDim;
    }

    public void setMatrix(double[][][] matrix) {
        this.matrix = matrix;
    }

    public void setxDim(int xDim) {
        this.xDim = xDim;
    }

    public void setyDim(int yDim) {
        this.yDim = yDim;
    }

    public void setzDim(int zDim) {
        this.zDim = zDim;
    }
}
