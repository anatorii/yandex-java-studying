import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class MainWindow extends JFrame {
    private static int width = 800;
    private static int height = 600;
    private JPanel panel;
    private JPanel south;
    private JPanel center;
    private JButton imageButton;
    private JButton wallButton;
    private JButton triangleButton;
    private Image image = null;
    private Image wallImage = null;
    private Image triangleImage = null;
    public Image curImage = null;

    public MainWindow() {
        super("Загрузить картинку");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(MainWindow.width, MainWindow.height);
        this.setLocation(d.width / 2 - MainWindow.width / 2, d.height / 2 - MainWindow.height / 2);
        this.getContentPane().add(panel);

        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    imageActionPerformed(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        wallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    wallImageActionPerformed(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        triangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    triangleImageActionPerformed(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                try {
                    loadImage();
                    center.repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void imageActionPerformed(ActionEvent e) throws IOException {
        loadImage();
        center.repaint();
    }

    private void wallImageActionPerformed(ActionEvent e) throws IOException {
        loadWallImage();
        center.repaint();
    }

    private void triangleImageActionPerformed(ActionEvent e) throws IOException {
        loadTriangleImage();
        center.repaint();
    }

    private void createUIComponents() {
        center = new CenterPanel();
        center.setLayout(new BorderLayout());
    }

    public void loadImage() throws IOException {
        if (image == null) {
            String apikey = App.dotenv.get("API_KEY");
            String uri = "https://static-maps.yandex.ru/v1?ll=30.316235,59.939327&z=15&size=650,450" + "&apikey=" + apikey;
            URL url = new URL(uri);
            image = ImageIO.read(url);
        }
        curImage = image;
    }

    public void loadWallImage() throws IOException {
        if (wallImage == null) {
            String pt = "&pt=32.040048,54.781350,pm2dom1";
            String apikey = App.dotenv.get("API_KEY");
            String uri = "https://static-maps.yandex.ru/v1?ll=32.040043,54.781389&z=17&size=650,450" + "&apikey=" + apikey + pt;
            URL url = new URL(uri);
            wallImage = ImageIO.read(url);
        }
        curImage = wallImage;
    }

    public void loadTriangleImage() throws IOException {
        if (triangleImage == null) {
            String point = "&ll=-71,26";
            String triangle = "-80.252770,25.793303" + ",-66.415017,18.288642" + ",-64.785838,32.294166" + ",-80.252770,25.793303";
            String pl = "&pl=c:8822DDC0,f:00FF00A0,w:8," + triangle;
            String apikey = App.dotenv.get("API_KEY");
            String uri = "https://static-maps.yandex.ru/v1?z=5&size=650,450" + "&apikey=" + apikey + point + pl;
            URL url = new URL(uri);
            triangleImage = ImageIO.read(url);
        }
        curImage = triangleImage;
    }
}

class CenterPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        MainWindow frame = (MainWindow) SwingUtilities.getWindowAncestor(this);
        if (frame.curImage != null) {
            g.clearRect(0, 0, getSize().width, getSize().height);
            g.drawImage(frame.curImage, (getSize().width - 650) / 2, (getSize().height - 450) / 2, null);
        }
    }
}
