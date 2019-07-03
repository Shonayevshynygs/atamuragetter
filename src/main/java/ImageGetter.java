import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageGetter {
    public static int counter = 0;

    public static void getImage(String urlString, int page) throws IOException {
        URL url = new URL(urlString);
        Image image = ImageIO.read(url);
        File output = new File("image"+page+".jpg");
        ImageIO.write((RenderedImage) image,"jpg", output);
    }

    public static void increment(){
        counter++;
    }
}
