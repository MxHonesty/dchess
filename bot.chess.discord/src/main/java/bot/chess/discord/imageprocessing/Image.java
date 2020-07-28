package bot.chess.discord.imageprocessing;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;


public class Image {
	
	BufferedImage finalImage;
	Assets assets;
    public char[][] table;
    
    
    //Contructors
	public Image(int skinNumber, int pieceScale){
		
		assets = new Assets(skinNumber, pieceScale);
	    table = new char[8][8];
	}
	public Image(){
		assets = new Assets(1, 100);
	    table = new char[8][8];
	}
	
	public BufferedImage renderTabel() {
		
	    int drawPozitionX,drawPozitionY;
	    
		finalImage = new BufferedImage(assets.chessTable.getHeight(), assets.chessTable.getWidth(), BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = finalImage.createGraphics();
	    g.drawImage(assets.chessTable.img, 0, 0, null);
	   
	   
	    for(int f1 = 0; f1 < 8 ; f1++) {
	        for(int f2 = 0; f2 < 8 ; f2++) {
	        	ImageComponent componentToDraw = findAsset(table[f2][f1]);
	        	
	        	if (componentToDraw != null) {
		        	BufferedImage imageToDraw = componentToDraw.img;
		        	
		        	drawPozitionX =  assets.squareSize * f1 + assets.topLeftRoot.x + (assets.squareSize -imageToDraw.getWidth())/2 ;
		        	drawPozitionY =  assets.squareSize * f2 + assets.topLeftRoot.y + (assets.squareSize -imageToDraw.getHeight())/2 ; 
		        	
		    	    g.drawImage(imageToDraw , drawPozitionX, drawPozitionY , null);
	        	}
	        	//else {System.out.print(f2 + " " + f1 + '\n') ; }
		    }
	    }
	    g.dispose();
	    return finalImage;
	}
	
	public void updateTable( char[][] updatedTable) {
	    for(int f1 = 0; f1 < 8 ; f1++) {
	        for(int f2 = 0; f2 < 8 ; f2++) {
	        	table[f1][f2] = updatedTable[f1][f2];
		    }
	    }
	}
	
	private ImageComponent findAsset(char piece) {
		switch(piece) {
		//White pieces
		  case ' ':	
			  return null;
		  case 'r':	
			  return assets.rookBlack;
		  case 'n':	
			  return assets.knightBlack;
		  case 'b':	
			  return assets.bishopBlack;
		  case 'q':	
			  return assets.queenBlack;
		  case 'k':	
			  return assets.kingBlack;
		  case 'p':	
			  return assets.pawnBlack;
		//Black  pieces
		  case 'R':	
			  return assets.rookWhite;
		  case 'N':	
			  return assets.knightWhite;
		  case 'B':	
			  return assets.bishopWhite;
		  case 'Q':	
			  return assets.queenWhite;
		  case 'K':	
			  return assets.kingWhite;
		  case 'P':	
			  return assets.pawnWhite;
		  default:
			  return assets.error;
		}
	}
	
	public void displayImage(BufferedImage img) {
		JLabel picLabel = new JLabel(new ImageIcon(img));
		JPanel jPanel = new JPanel();
		jPanel.add(picLabel);
		JFrame f = new JFrame();
		f.setSize(new Dimension(img.getWidth(), img.getHeight() +50) );
		f.add(jPanel);
		f.setVisible(true);
	}
	
	public void salvare(BufferedImage img, String id) {
		File outputfile = new File("src/main/resources/img" + id + ".png");
	    try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Assets {
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
	
	//Other
	ImageComponent error;

	public Assets(int skinNumber, int pieceScale) {
		
		Skin skin = new Skin(skinNumber);
		
		topLeftRoot = skin.topLeftRoot;
		bottomRightRoot = skin.bottomRightRoot;
		
		tableSize = skin.bottomRightRoot.x - skin.topLeftRoot.x; 
		squareSize = tableSize/8;
		
		int scale =  Math.round(squareSize * pieceScale /100);

		//Assets
		chessTable = new ImageComponent(skin.chessTablePath);
		//White
		rookWhite = scalePiece(new ImageComponent(skin.rookWhitePath), scale );
		knightWhite = scalePiece(new ImageComponent(skin.knightWhitePath), scale );
		bishopWhite = scalePiece(new ImageComponent(skin.bishopWhitePath), scale );
		queenWhite = scalePiece(new ImageComponent(skin.queenWhitePath), scale );
		kingWhite = scalePiece(new ImageComponent(skin.kingWhitePath), scale );
		pawnWhite = scalePiece(new ImageComponent(skin.pawnWhitePath), scale );
		
		//Black
		rookBlack = scalePiece(new ImageComponent(skin.rookBlackPath), scale );
		knightBlack = scalePiece(new ImageComponent(skin.knightBlackPath), scale );
		bishopBlack = scalePiece(new ImageComponent(skin.bishopBlackPath), scale );
		queenBlack = scalePiece(new ImageComponent(skin.queenBlackPath), scale );
		kingBlack = scalePiece(new ImageComponent(skin.kingBlackPath), scale );
		pawnBlack = scalePiece(new ImageComponent(skin.pawnBlackPath), scale );
		
		error = scalePiece(new ImageComponent(skin.errorPath), scale );
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

	String errorPath;
	//Here is contained every path to every image used
	public Skin(int selectedSkin) {

		switch(selectedSkin) {
		  case 1:
			  	topLeftRoot = new Vector2(45,45);
			  	bottomRightRoot = new Vector2(405,405);

			  	//Images
				chessTablePath = "src/main/resources/skin1/chessboard.png";
				//White pieces
				rookWhitePath = "src/main/resources/skin1/whiteRook.png";
				knightWhitePath = "src/main/resources/skin1/whiteKnight.png";
				bishopWhitePath = "src/main/resources/skin1/whiteBishop.png";
				queenWhitePath = "src/main/resources/skin1/whiteQueen.png";
				kingWhitePath = "src/main/resources/skin1/whiteKing.png";
				pawnWhitePath = "src/main/resources/skin1/whitePawn.png";

				//Black pieces
				rookBlackPath = "src/main/resources/skin1/blackRook.png";
				knightBlackPath = "src/main/resources/skin1/blackKnight.png";
				bishopBlackPath = "src/main/resources/skin1/blackBishop.png";
				queenBlackPath = "src/main/resources/skin1/blackQueen.png";
				kingBlackPath = "src/main/resources/skin1/blackKing.png";
				pawnBlackPath = "src/main/resources/skin1/blackPawn.png";
		    break;
		  default:
		}
		
		errorPath = "src/main/resources/error.png";
	}
}

//This is a new component witch takes a path string and imports an image

class ImageComponent extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BufferedImage img;

    //Reading the image from disk
    public ImageComponent(String path) {
       try {
          img = ImageIO.read(new File(path));
       } catch (IOException e) {
	      System.out.print("ERROR READING IMAGE " + path);  
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
}
