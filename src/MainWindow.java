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
    private Image image = null;

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

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);

                try {
                    loadImage();
                    drawImage();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void imageActionPerformed(ActionEvent e) throws IOException {
        drawImage();
    }

    private void createUIComponents() {
        center = new CenterPanel(this);
        center.setLayout(new BorderLayout());
    }

    public void loadImage() throws IOException {
        if (image == null) {
            String apikey = App.dotenv.get("API_KEY");
            String uri = "https://static-maps.yandex.ru/v1?ll=30.316235,59.939327&z=15&size=650,450" + "&apikey=" + apikey;
            URL url = new URL(uri);
            image = ImageIO.read(url);
        }
    }

    public void drawImage() {
        center.getGraphics().clearRect(0, 0, center.getSize().width, center.getSize().height);
        center.getGraphics().drawImage(image, (center.getSize().width - 650) / 2, (center.getSize().height - 450) / 2, null);
    }
}

class CenterPanel extends JPanel {
    private JFrame frame;
    public CenterPanel(JFrame frame) {
        this.frame = frame;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
