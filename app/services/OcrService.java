package services;

import net.sourceforge.tess4j.Tesseract;
import org.opencv.core.Mat;
import utils.ImagePreprocessor;

import java.io.File;

public class OcrService {

    private final Tesseract tesseract;

    public OcrService() {
        this.tesseract = new Tesseract();
        // Define o caminho para os arquivos de idioma (tessdata)
        this.tesseract.setDatapath("./src/main/resources/tessdata");
        this.tesseract.setLanguage("eng"); // Altere para "eng" se a tabela estiver em inglês
    }

    public String extractTextFromImage(File imageFile) throws Exception {
        // 1. Carrega a imagem com OpenCV
        Mat originalImage = ImagePreprocessor.loadImage(imageFile.getAbsolutePath());

        // 2. Pré-processa a imagem
        Mat processedImage = ImagePreprocessor.preprocessImage(originalImage);

        // 3. Salva temporariamente (Tesseract lê melhor de arquivo)
        String tempPath = "temp_processed.png";
        ImagePreprocessor.saveImage(processedImage, tempPath);

        // 4. Passa para o Tesseract
        return tesseract.doOCR(new File(tempPath));
    }

}