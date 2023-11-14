package poly.edu.untinity;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import java.awt.Image;

public class XImage {

    public static Image getIcon() {
        URL url = XImage.class.getResource("/poly/edu/images/logo.png");
        return new ImageIcon(url).getImage();
    }

    public static boolean saveIMG(File src) {
        File dst = new File("src\\poly\\edu\\images", src.getName());
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ImageIcon read(String fileName) {
        File path = new File("src\\poly\\edu\\images", fileName);
        return new ImageIcon(path.getAbsolutePath());
    }
}
