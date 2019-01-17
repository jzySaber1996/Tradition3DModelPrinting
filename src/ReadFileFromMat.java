import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLSingle;
import com.jmatio.types.MLUInt8;

import java.io.IOException;

public class ReadFileFromMat {
    private static MLArray mlArray = null;//private static MLDouble mlDouble = null;
    private static final int N = 1;
//    private static int xDim, yDim, zDim;

    public static DataArray readFile() throws IOException {
        MatFileReader read = new MatFileReader("file/fir_sub.mat");
        String name = "subVolume";
//        String ifSetVolume = properties.getProperty("setVolume");
        MLArray mlArray = read.getMLArray(name); //I_Global
        MLSingle d = (MLSingle) mlArray;//MLDouble d = (MLDouble) mlArray;
        System.out.println(d.getDimensions()[0] + " " + d.getDimensions()[1] + " " + d.getDimensions()[2]);
//            properties.storeToXML(new FileOutputStream("File/printParameters.xml"), "printInfo");
        double[][][] matrix = transferSingle(d);
        DataArray dataArray = new DataArray(matrix, d.getDimensions()[0] / N,
                d.getDimensions()[1] / N, d.getDimensions()[2] / N);
        System.out.println("Read file finished!");
        return dataArray;
    }

    private static double[][][] transferSingle(MLSingle d) {
        double[][][] matResult = new double[550][550][300];
        int count = 0;
        for (int i = 0; i < d.getDimensions()[0]; i++)
            for (int j = 0; j < d.getDimensions()[1]; j++)
                for (int k = 0; k < d.getDimensions()[2]; k++) {
                    matResult[i][j][k] = d.get(i, k * d.getDimensions()[1] + j);
                    if (matResult[i][j][k] > 0.5)
                        count++;
                }
        System.out.println(count);
        double[][][] matSampling = new double[200][200][200];
        int sizeX, sizeY, sizeZ;
        sizeX = d.getDimensions()[0] / N;
        sizeY = d.getDimensions()[1] / N;
        sizeZ = d.getDimensions()[2] / N;
//        int count = 0;
        count = 0;
        for (int i = 0; i < sizeX; i++)
            for (int j = 0; j < sizeY; j++)
                for (int k = 0; k < sizeZ; k++) {
                    matSampling[i][j][k] = matResult[i * N][j * N][k * N];
                    if (matSampling[i][j][k] > 0.5)
                        count++;
                }
        System.out.println(count);
        return matSampling;
    }

}
