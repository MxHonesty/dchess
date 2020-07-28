package bot.chess.discord.imageprocessing;



public class test {
	
    public char[][] localTable;
	
    public test() {
    	
    	localTable = new char[8][8];
    	
 	     localTable[0][0] = 'r';
 	     localTable[0][1] = 'n';
 	     localTable[0][2] = 'b';
 	     localTable[0][3] = 'q';
 	     localTable[0][4] = 'k';
 	     localTable[0][5] = 'b';
 	     localTable[0][6] = 'n';
 	     localTable[0][7] = 'r';

 	     for( int f1 = 0 ; f1 < 8 ;f1++) {
 	    	  localTable[1][f1] = 'p';
 	     }

	     localTable[7][0] = 'R';
 	     localTable[7][1] = 'N';
 	     localTable[7][2] = 'B';
 	     localTable[7][3] = 'Q';
 	     localTable[7][4] = 'K';
 	     localTable[7][5] = 'B';
 	     localTable[7][6] = 'N';
 	     localTable[7][7] = 'R';

 	     for( int f1 = 0 ; f1 < 8 ;f1++) {
 	    	  localTable[6][f1] = 'P';
 	     }
 	     
 	    for( int f1 = 0 ; f1 < 8 ;f1++) {
 	    	  for( int f2 = 2 ; f2 < 6 ;f2++) {
 	     	     localTable[f2][f1] = ' ';

 		     }
	     }
 	    
         Image image = new Image();
         image.updateTable(localTable);
        // image.renderTabel();
         image.displayImage(image.finalImage);
    }
    
   
	public static void main(String args[])  //static method  
	{  
		test Test = new test();

	}  
}
