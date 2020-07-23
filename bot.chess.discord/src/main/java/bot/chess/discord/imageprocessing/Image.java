package bot.chess.discord.imageprocessing;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

//not permanent
import javax.swing.*;


public class Image {
	
	public static void main(String args[])  //static method  
	{  
		
		String imagePath = "/bot.chess.discord/3vhjradrcup41.jpg";
		
		//ImageComponent Image = new ImageComponent(imagePath);
		
		
	   BufferedImage img2 = null;
			   
        try {
			img2 = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
	        System.out.print("error\n ");  
			e.printStackTrace();
		}
	
		
		JLabel picLabel = new JLabel(new ImageIcon(img2));
		JPanel jPanel = new JPanel();
		jPanel.add(picLabel);
		
		JFrame f = new JFrame();
		f.setSize(new Dimension(img2.getWidth(), img2.getHeight() ) );
		f.add(jPanel);
		f.setVisible(true);
	}  
	
}


//This is a new component witch takes a path string and imports an image

class ImageComponent extends Component {

   BufferedImage img;

    public void paint(Graphics g) {
       g.drawImage(img, 0, 0, null);
    }

    public ImageComponent(String path) {
       try {
          img = ImageIO.read(new File(path));
       } catch (IOException e) {
          e.printStackTrace();
       }
    }

    public Dimension getPreferredSize() {
       if (img == null) {
          return new Dimension(100,100);
       } else {
          return new Dimension(img.getWidth(), img.getHeight());
       }
    }
 }