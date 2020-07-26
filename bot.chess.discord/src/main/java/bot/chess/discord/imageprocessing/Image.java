package bot.chess.discord.imageprocessing;

import java.awt.*;
import java.awt.color.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

//not permanent
import javax.swing.*;


public class Image {
	Assets assets;
    public char[][] table;

	public Image(){
		 assets = new Assets(1);
		 table = new char[8][8];
	}
	

	
	public void generateTable() {
		
	    int drawPozitionX,drawPozitionY;
	    
		
		BufferedImage finalImage = new BufferedImage(assets.chessTable.getHeight(), assets.chessTable.getWidth(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = finalImage.createGraphics();
	    g.drawImage(assets.chessTable.img, 0, 0, null);
	   
	   
	    for(int f1 = 0; f1 < 8 ; f1++) {
	        for(int f2 = 0; f2 < 8 ; f2++) {
	        	BufferedImage imageToDraw = assets.knightBlack.img;
	        	
	        	drawPozitionX =  assets.squareSize * f1 + assets.topLeftRoot.x ;
	        	drawPozitionY =  assets.squareSize * f2 + assets.topLeftRoot.y ;
	        	
	    	    g.drawImage(imageToDraw ,drawPozitionX,drawPozitionY, null);
		    }
		    
	    }
	    
	    g.dispose();

	    
        displayImage(finalImage);
	}
	
	
	
	
	private static void displayImage(BufferedImage img) {

		JLabel picLabel = new JLabel(new ImageIcon(img));
		JPanel jPanel = new JPanel();
		jPanel.add(picLabel);
		
		JFrame f = new JFrame();
		f.setSize(new Dimension(img.getWidth(), img.getHeight() +50) );
		f.add(jPanel);
		f.setVisible(true);
		
	}
	
	
}

class Assets {
	float relativeScale = 85;
	int tableSize = 0;
	int squareSize = 0;
		
	Vector2 topLeftRoot;
	Vector2 bottomRightRoot;
	
	ImageComponent chessTable;
	//White
	ImageComponent rookWhite;
	ImageComponent knightWhite;
	ImageComponent bishopWhite;
	ImageComponent queenWhite;
	ImageComponent kingWhite;
	ImageComponent pawnWhite;
	
	//Black
	ImageComponent rookBlack;
	ImageComponent knightBlack;
	ImageComponent bishopBlack;
	ImageComponent queenBlack;
	ImageComponent kingBlack;
	ImageComponent pawnBlack;

	public Assets(int skinNumber) {
		
		Skin skin = new Skin(skinNumber);
		
		topLeftRoot = skin.topLeftRoot;
		bottomRightRoot = skin.bottomRightRoot;
		
		tableSize = skin.bottomRightRoot.x - skin.topLeftRoot.x; 
		squareSize = tableSize/8;
		int scale =  Math.round(squareSize * relativeScale /100);

		//Assets
		chessTable = new ImageComponent(skin.chessTablePath);
		//White
		rookWhite = null;
		knightWhite = null;
		bishopWhite = null;
		queenWhite = null;
		kingWhite = null;
		pawnWhite = null;
		
		//Black
		rookBlack = null;
		knightBlack = scalePiece(new ImageComponent(skin.knightBlackPath), scale );
		bishopBlack = null;
		queenBlack = null;
		kingBlack = null;
		pawnBlack = null;
	}
	private ImageComponent scalePiece(ImageComponent piece ,int scale){
		
       BufferedImage scaledPiece = null;
       
        if (piece != null && piece.img != null) {
        	
        	scaledPiece = new BufferedImage(scale, scale, piece.img.getType());
            
            Graphics2D graphics2D = scaledPiece.createGraphics();
            graphics2D.drawImage(piece.img, 0, 0, scale, scale, null);
            graphics2D.dispose();
        }
        
        piece.img = scaledPiece;
        
		return piece;
	}

}

class Skin {
	
	Vector2 topLeftRoot;
	Vector2 bottomRightRoot;
	//Images
	String chessTablePath;
	//White pieces
	String rookWhitePath;
	String knightWhitePath;
	String bishopWhitePath;
	String queenWhitePath;
	String kingWhitePath;
	String pawnWhitePath;

	//Black pieces
	String rookBlackPath;
	String knightBlackPath;
	String bishopBlackPath;
	String queenBlackPath;
	String kingBlackPath;
	String pawnBlackPath;

	//Here is contained every path to every image used
	public Skin(int selectedSkin) {

		switch(selectedSkin) {
		  case 1:
			  	topLeftRoot = new Vector2(45,45);
			  	bottomRightRoot = new Vector2(405,405);

			  	//Images
				chessTablePath = "src/main/resources/chessboard.png";
				//White pieces
				rookWhitePath = null;
				knightWhitePath = null;
				bishopWhitePath = null;
				queenWhitePath = null;
				kingWhitePath = null;
				pawnWhitePath = null;

				//Black pieces
				rookBlackPath = null;
				knightBlackPath = "src/main/resources/horse.png";
				bishopBlackPath = null;
				queenBlackPath = null;
				kingBlackPath = null;
		    break;
		  default:
		}
	}
}


//This is a new component witch takes a path string and imports an image

class ImageComponent extends Component {

	BufferedImage img;

    //Reading the image from disk
    public ImageComponent(String path) {
       try {
          img = ImageIO.read(new File(path));
       } catch (IOException e) {
	      System.out.print("ERROR READING IMAGE\n ");  
          e.printStackTrace();
       }
    }
    //get set of dimensions
    public Dimension getPreferredSize() {
       if (img == null) {
          return new Dimension(100,100);
       } else {
          return new Dimension(img.getWidth(), img.getHeight());
       }
    }
    public int getWidth() {
        if (img == null) {
           return 100;
        } else {
           return  img.getWidth();
        }
     }
    public int getHeight() {
        if (img == null) {
           return 100;
        } else {
           return  img.getHeight();
        }
    }
 }

class Vector2
{              
    // Members
    public int x;
    public int y;
       
    // Constructors
    public Vector2() {
        this.x = 0;
        this.y = 0;
    }    
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    } 
    // Compare two vectors
    public boolean equals(Vector2 other) {
        return (this.x == other.x && this.y == other.y);
    }
}
