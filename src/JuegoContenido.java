import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class JuegoContenido extends JPanel implements ActionListener {
    // Pantalla
    static final int PANTALLA = 600;
    static final int CUADROS_SIZE = 25;
    static final int CUADROS_PARALELO = (int) PANTALLA/CUADROS_SIZE;

    // Serpiente
    static final int TOTAL_CUERPO_SERPIENTE = (PANTALLA*PANTALLA)/CUADROS_SIZE;
    int[] serpienteX = new int[TOTAL_CUERPO_SERPIENTE];
    int[] serpienteY = new int[TOTAL_CUERPO_SERPIENTE];
    int cuerpo_serpiente = 3;
    char direccion = 'd'; // Utilizaremos el awsd
    // Comida
    int comidaX;
    int comidaY;
    // Timer
    boolean running = true;
    static final int VELOCIDAD = 100;
    Timer timer;
    // Otros
    Random random = new Random();

    JuegoContenido () {
        this.setPreferredSize(new Dimension(PANTALLA,PANTALLA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new Controles());
        iniciarJuego();
    }
    public void iniciarJuego() {
        agregarComida();
        timer = new Timer(VELOCIDAD,this);
        timer.start();
    }
    public void agregarComida () {
        comidaX = random.nextInt(CUADROS_PARALELO)*CUADROS_SIZE;
        comidaY = random.nextInt(CUADROS_PARALELO)*CUADROS_SIZE;
    }
    public void moverSerpiente () {
        for (int i = cuerpo_serpiente; i > 0; i--) {
            serpienteX[i] = serpienteX[i-1];
            serpienteY[i] = serpienteY[i-1];
        }
        switch (direccion){
            case 'd':
                serpienteX[0] = serpienteX[0] + CUADROS_SIZE;
                break;
            case 'a':
                serpienteX[0] = serpienteX[0] - CUADROS_SIZE;
                break;
            case 'w':
                serpienteY[0] = serpienteY[0] - CUADROS_SIZE;
                break;
            case 's':
                serpienteY[0] = serpienteY[0] + CUADROS_SIZE;
                break;
        }
    }
    public void comprobarComida () {
        if (serpienteX[0] == comidaX && serpienteY[0] == comidaY){
            cuerpo_serpiente++;
            agregarComida();
        }
    }
    public void comprobarColisiones () {
        if (serpienteX[0] < 0) {
            running = false;
        }
        if (serpienteY[0] < 0) {
            running = false;
        }
        if (serpienteX[0] > PANTALLA - CUADROS_SIZE){
            running = false;
        }
        if (serpienteY[0] > PANTALLA - CUADROS_SIZE){
            running = false;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            moverSerpiente();
            comprobarComida();
            comprobarColisiones();
        }
        repaint();
    }
    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);

        for (int i = 0 ; i < CUADROS_PARALELO; i++){
            g.drawLine(0,CUADROS_SIZE*i, PANTALLA, CUADROS_SIZE*i);
            g.drawLine(CUADROS_SIZE*i, 0 , CUADROS_SIZE*i, PANTALLA);
        }
        g.setColor(Color.red);
        g.fillOval(comidaX,comidaY,CUADROS_SIZE,CUADROS_SIZE);
        for (int i = 0; i < cuerpo_serpiente; i++) {
            g.setColor(Color.green);
            g.fillRect(serpienteX[i], serpienteY[i],CUADROS_SIZE,CUADROS_SIZE);
        }
    }
    public class Controles extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e ){
            switch (e.getKeyChar()){
                case 'w':
                    direccion = 'w';
                    break;
                case 's':
                    direccion = 's';
                    break;
                case 'a':
                    direccion = 'a';
                    break;
                case 'd':
                    direccion = 'd';
                    break;
            }
        }
    }
}
