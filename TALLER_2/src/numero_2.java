import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class numero_2 extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    // aqui añadimos de antemano varios valores, como los radios y lados y se establece valores que cambiaran segun desee el usuario
    private Color colorpincel = Color.BLACK;
    double radio = 25;
    double lado = 25;
    private BufferedImage lienzo;
    double grosor = 5;

    //arranca el programa
    public static void main(String[] args) {
        comenzar();

    }
    public numero_2(){
        lienzo = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
        lienzo_blanco();
        //se añaden las propiedades del lienzo y los distitnos tipos de bibliotecas que usaremos

        this.setTitle("pintura");
        this.setSize(800,800);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        setVisible(true);

    }
    //rellena el lienzo de blanco para poder pintar, y mas tarde nos servira para borrar todo en pantalla
    private void lienzo_blanco(){
        Graphics2D relleno = lienzo.createGraphics();
        relleno.setColor(Color.WHITE);
        relleno.fillRect(0,0, lienzo.getWidth(), lienzo.getHeight());
        relleno.dispose();
    }
    public static void comenzar(){
        SwingUtilities.invokeLater((numero_2::new));

    }
    //utilizando DrawOval creamos un circulo con las proporciones (x,y,radio)respectivamente, se deben emplear algunos
    //tecnicas para poder ubicar especificamente el centro y de alli poder usar la formula
    public void circulo_crear(double x, double y, double radio) {
        Graphics2D circulo2d = lienzo.createGraphics();
        circulo2d.setColor(colorpincel);
        circulo2d.drawOval((int)(x - radio), (int)(y - radio), (int)(radio * 2), (int)(radio * 2));
        circulo2d.dispose();
    }
    //usando DrawRect con los valores respectivos de (x,y,lado_a,lado_b) que seria la misma formula del cuadrado, podemos
    //crear la figura, aunque como los lados son iguales nos podemos ahorrar diferenciarlos y ponerlos como uno igual
    public void cuadrado_crear(double x, double y, double lado) {
        Graphics2D cuadrado2d = lienzo.createGraphics();
        cuadrado2d.setColor(colorpincel);
        cuadrado2d.drawRect((int) x, (int) y,(int)lado,(int)lado);
    }
    public void triangulo_crear(double x, double y, double lado) {
        Graphics2D triangulo2d = lienzo.createGraphics();
        triangulo2d.setColor(colorpincel);
        int[] puntox = {(int)x, (int)(x - lado / 2), (int)(x + lado / 2)};

        int[] puntoy = {(int)(y - (lado * Math.sqrt(3) / 2)), (int)(y + (lado * Math.sqrt(3) / 2)), (int)(y + (lado * Math.sqrt(3) / 2))}; //se utiliza el math.sqt para que sea un numero entero

        triangulo2d.drawPolygon(puntox, puntoy, 3);
        triangulo2d.dispose();
    }
    //parecido al del cuadrado, pero uno de sus lados lo multiplicamos por 2 para que esta adquiera la forma
    public void rectangulo_dibujar(double x, double y, double lado) {
        Graphics2D rectangulo2d = lienzo.createGraphics();
        rectangulo2d.setColor(colorpincel);
        rectangulo2d.drawRect((int) x, (int) y,(int)lado*2,(int)lado);
    }
    //usando un for y las reglas del calculo y algebra, podemos encontrar las ubicaciones de los asteriscos desde
    //el centro + el cosen o sen (depende de si es x o y), aunque un imprevisto es que queda de forma chueca en vez de
    //estar de pie, ademas de multiplicar el i por el 72 para poder calcular los grados que el corresponde 72*5 = 360
    public void pentagono_dibujar(double x, double y, double lado) {
        Graphics2D pentagono2d = lienzo.createGraphics();
        pentagono2d.setColor(colorpincel);
        int[] puntox = new int[5];
        int[] puntoy = new int[5];

        for (int i = 0; i < 5; i++) {
            double angulo = Math.toRadians(72 * i);
            puntox[i] = (int) (x + lado * Math.cos(angulo));
            puntoy[i] = (int) (y + lado * Math.sin(angulo));
        }
        pentagono2d.drawPolygon(puntox, puntoy, 5);
        pentagono2d.dispose();

    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    //KeyPressed nos sirve para que la maquin detecte la tecla presionada, y mediante Switch podemos asignarles valores
    //a las variables dependiendo de cual sea. ademas utilizamos Mouse info para poder encontrar la posicion del raton
    //y asi tenerlos como base para la creacion de las figuras geomatricas o dibujar directamente con el mouse.
    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        Point posicion_cursor = MouseInfo.getPointerInfo().getLocation();
        int x = posicion_cursor.x;
        int y = posicion_cursor.y;

        switch (keycode){
            case KeyEvent.VK_5:
                pentagono_dibujar(x,y,lado);
                repaint();
                break;
            case KeyEvent.VK_MINUS: //-
                if (grosor >5){
                    grosor -=5; //asi no bajara el valor a 0 y desaparecera el cursor
                    break;
                }
                break;
            case KeyEvent.VK_PLUS: //+
                grosor+=5;
                break;

            case KeyEvent.VK_3:
                triangulo_crear(x,y,lado);
                repaint();
                break;
            case KeyEvent.VK_B:
                colorpincel = Color.BLUE;
                break;
            case KeyEvent.VK_G:
                colorpincel = Color.GREEN;
                break;
            case KeyEvent.VK_R:
                colorpincel = Color.RED;
                break;

            case KeyEvent.VK_4:
                rectangulo_dibujar(x,y,lado);
                repaint();
                break;
            case KeyEvent.VK_C:
                guardar_dibujo();
                break;
            case KeyEvent.VK_S:
                lienzo_blanco();
                repaint();
                break;

            case KeyEvent.VK_2:
                cuadrado_crear(x,y,lado);
                repaint();
                break;
            case KeyEvent.VK_1:
                circulo_crear(x,y,radio);
                repaint();
                break;
        }


    }
    //implementamos esta definicion para poder tener una forma en especifico al momento de dibujar con el raton, en este
    //caso decidi usar como forma base el circulo con un radio propio al cual llame grosor y el cual se imprime constantemente
    //tanto como se oprime como cuando se arrastra
    private void pincel_normal(double x,double y, double grosor  ){
        Graphics2D pincel = lienzo.createGraphics();
        pincel.setColor(colorpincel);
        pincel.fillOval((int)(x - grosor), (int)(y - grosor), (int)(grosor * 2), (int)(grosor * 2));
        pincel.dispose();

    }

    private void circulo_crear(int x, int y, int radio) {
    }
    // se guarda la reciente imagen creada en los archivos locales usando el metodo de ImageIO,write
    private void guardar_dibujo(){
        try {
            ImageIO.write(lienzo, "png", new File("dibujo.png"));
            JOptionPane.showMessageDialog(this, "Dibujo guardado como dibujo.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(lienzo, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        pincel_normal(x,y,grosor);
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        pincel_normal(x,y,grosor);
        repaint();

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}