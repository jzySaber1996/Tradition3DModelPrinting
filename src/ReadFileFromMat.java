import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLSingle;

import java.io.IOException;

public class ReadFileFromMat {
    private static MLArray mlArray = null;//private static MLDouble mlDouble = null;

    public static double[][][] readFile() throws IOException {
        MatFileReader read = new MatFileReader("file/fir_sub.mat");
        String name = "subVolume";
//        String ifSetVolume = properties.getProperty("setVolume");
        MLArray mlArray = read.getMLArray(name); //I_Global
        MLSingle d = (MLSingle) mlArray;//MLDouble d = (MLDouble) mlArray;
        System.out.println(d.getDimensions()[0] + " " + d.getDimensions()[1] + " " + d.getDimensions()[2]);
//            properties.storeToXML(new FileOutputStream("File/printParameters.xml"), "printInfo");
        double[][][] matrix = transferSingle(d);
        System.out.println("Read file finished!");
        return matrix;
    }

    private static double[][][] transferSingle(MLSingle d) {
        double[][][] matResult = new double[500][500][500];
        int count = 0;
        for (int i = 0; i < d.getDimensions()[0]; i++)
            for (int j = 0; j < d.getDimensions()[1]; j++)
                for (int k = 0; k < d.getDimensions()[2]; k++) {
                    matResult[i][j][k] = d.get(i, k * d.getDimensions()[1] + j);
                }
        return matResult;
    }

}
