package utils;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImagePreprocessor {

    static {
        // Carrega a biblioteca OpenCV (necessário apenas uma vez)
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static Mat preprocessImage(Mat originalImage) {
        Mat processedImage = new Mat();

        // 1. Converte para escala de cinza
        Imgproc.cvtColor(originalImage, processedImage, Imgproc.COLOR_BGR2GRAY);

        // 2. Aplica threshold (binarização: preto e branco)
        Imgproc.threshold(processedImage, processedImage, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);

        // 3. Remove ruídos (opcional)
        Imgproc.medianBlur(processedImage, processedImage, 3);

        // 4. Redimensiona (se necessário)
        Imgproc.resize(processedImage, processedImage, new Size(), 2.0, 2.0, Imgproc.INTER_CUBIC);

        return processedImage;
    }

    // Metodo para carregar uma imagem de um arquivo
    public static Mat loadImage(String filePath) {
        return Imgcodecs.imread(filePath);
    }

    // Metodo para salvar uma imagem processada
    public static void saveImage(Mat image, String outputPath) {
        Imgcodecs.imwrite(outputPath, image);
    }

}