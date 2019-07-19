import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageGetter {
    public static int counter = 0;

    public static void getImages(String urlString, int page) throws IOException {
        URL url = new URL(urlString);
        Image image = ImageIO.read(url);
        File output = new File("image"+page+".jpg");
        ImageIO.write((RenderedImage) image,"jpg", output);
    }

    public static Image getSingleImage(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void increment(){
        counter++;
    }
}
