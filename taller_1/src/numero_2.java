import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.ref.Cleaner;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class numero_2 extends JFrame{
    private BufferedImage lienzo;
    //obtener la posicion del mouse (x,y)
    public static void numero_2(String[] args) throws Exception
    {
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
    }
    //crear el lienzo
    public numero_2(){
        lienzo = new BufferedImage(800,200, BufferedImage.TYPE_INT_ARGB);


        this.setTitle("Pintura");
        this.setSize(800,600);



    }


}
