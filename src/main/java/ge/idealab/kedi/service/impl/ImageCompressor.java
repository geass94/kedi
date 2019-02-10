package ge.idealab.kedi.service.impl;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class ImageCompressor {
    public InputStream compress(InputStream inputStream, int width, int height) throws IOException {
        BufferedImage sourceImage = ImageIO.read(inputStream);

        if(height == 0) {
            height = (width * sourceImage.getHeight())/ sourceImage.getWidth();
        }
        if(width == 0) {
            width = (height * sourceImage.getWidth())/ sourceImage.getHeight();
        }

        int newW = sourceImage.getWidth();
        int newH = sourceImage.getHeight();

        if (sourceImage.getWidth() > width) {
            newW = width;
            newH = (newW * sourceImage.getHeight()) / sourceImage.getWidth();
        }
        if (newH > height) {
            newH = height;
            newW = (newH * sourceImage.getWidth()) / sourceImage.getHeight();
        }

        Image thumbnail = sourceImage.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
                thumbnail.getHeight(null),
                BufferedImage.TYPE_INT_RGB);
        bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedThumbnail, "jpeg", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
