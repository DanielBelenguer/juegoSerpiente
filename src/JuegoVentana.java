import javax.swing.*;

public class JuegoVentana extends JFrame {
    JuegoVentana () {
        this.setTitle("Serpiente");
        this.add(new JuegoContenido());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
