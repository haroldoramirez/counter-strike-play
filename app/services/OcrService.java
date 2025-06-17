package services;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OcrService {

    private final Tesseract tesseract;

    public OcrService() {
        this.tesseract = new Tesseract();
        // Define o caminho para os arquivos de idioma (tessdata)
        this.tesseract.setDatapath("src/main/resources/tessdata");
        this.tesseract.setLanguage("por"); // Altere para "eng" se a tabela estiver em inglês
    }

    public String extractTextFromImage(File imageFile) throws TesseractException, IOException {
        // Verifica se a imagem é suportada diretamente
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            throw new IOException("Formato de imagem não suportado. Converta para PNG/JPEG.");
        }
        return tesseract.doOCR(image);
    }

}