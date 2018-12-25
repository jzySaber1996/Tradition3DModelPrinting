import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLSingle;

import java.io.IOException;

public class ReadFileFromMat {
    private static MLArray mlArray = null;//private static MLDouble mlDouble = null;

    public static double[][][] readFile() throws IOException {
//        Properties properties = new Properties();
//        properties.loadFromXML(new FileInputStream("File/printParameters.xml"));
        //String filePath = properties.getProperty("filepath");
//        MatFileReader read = new MatFileReader(properties.getProperty("filepath")); //seg1.mat
        MatFileReader read = new MatFileReader("file/fir_sub.mat");
//        String name = properties.getProperty("parameterName");
        String name = "subVolume";
//        String ifSetVolume = properties.getProperty("setVolume");
        MLArray mlArray = read.getMLArray(name); //I_Global
        MLSingle d = (MLSingle) mlArray;//MLDouble d = (MLDouble) mlArray;
//            properties.setProperty("xDim", String.valueOf(d.getDimensions()[0]));
//            properties.setProperty("yDim", String.valueOf(d.getDimensions()[1]));
//            properties.setProperty("zDim", String.valueOf(d.getDimensions()[2]));
        System.out.println(d.getDimensions()[0] + " " + d.getDimensions()[1] + " " + d.getDimensions()[2]);
//            properties.storeToXML(new FileOutputStream("File/printParameters.xml"), "printInfo");
        double[][][] matrix = transferSingle(d);
        System.out.println("Read file finished!");
//            if (ifSetVolume.equals("否")) return matrix;
//            else if (ifSetVolume.equals("是")) {
//                Integer xStart = Integer.parseInt(properties.getProperty("xStart"));
//                Integer yStart = Integer.parseInt(properties.getProperty("yStart"));
//                Integer zStart = Integer.parseInt(properties.getProperty("zStart"));
//                Integer xEnd = Integer.parseInt(properties.getProperty("xEnd"));
//                Integer yEnd = Integer.parseInt(properties.getProperty("yEnd"));
//                Integer zEnd = Integer.parseInt(properties.getProperty("zEnd"));
//                properties.setProperty("xDim", String.valueOf(xEnd - xStart + 1));
//                properties.setProperty("yDim", String.valueOf(yEnd - yStart + 1));
//                properties.setProperty("zDim", String.valueOf(zEnd - zStart + 1));
//                properties.storeToXML(new FileOutputStream("File/printParameters.xml"), "printInfo");
//                return getLimitedSubVolume(matrix, xStart, xEnd, yStart, yEnd, zStart, zEnd);
        return matrix;
    }

//    private static double[][][] transferDouble(MLDouble d) {
//        double[][][] matResult = new double[500][500][500];
//        int count = 0;
//        for (int i = 0; i < d.getDimensions()[0]; i++)
//            for (int j = 0; j < d.getDimensions()[1]; j++)
//                for (int k = 0; k < d.getDimensions()[2]; k++) {
//                    matResult[i][j][k] = d.get(i, k * d.getDimensions()[1] + j);
//                }
//        return matResult;
//    }

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
//
//    private static double[][][] getLimitedSubVolume(double[][][] matInit,
//                                                    Integer x_start, Integer x_end,
//                                                    Integer y_start, Integer y_end,
//                                                    Integer z_start, Integer z_end) {
//        double[][][] matResult = new double[100][100][100];
//        for (int i = 0; i <= x_end - x_start; i++)
//            for (int j = 0; j <= y_end - y_start; j++)
//                for (int k = 0; k <= z_end - z_start; k++) {
//                    matResult[i][j][k] = matInit[i + x_start][j + y_start][k + z_start];
//                }
//        return matResult;
//    }

}
