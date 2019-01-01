import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainCallFunction {
    public static void main(String[] args) throws IOException {
        DataArray dataArray = ReadFileFromMat.readFile();
        HashMap<Point3D, ArrayList<Triangle>> triangleStore = MatchingCubeAlgorithm.doMatchingCube(dataArray);
        int k = 0;
    }
}
