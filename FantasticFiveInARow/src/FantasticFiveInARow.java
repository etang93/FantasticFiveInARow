import java.io.File;

import javax.activation.MimetypesFileTypeMap;

public class FantasticFiveInARow {

	public static void main(String[] args){
		RenjuSplashScreen sc = new RenjuSplashScreen(5000);
		sc.showSplash();
		
		String filepath = "/src/img/Renju.jpg";
	    File f = new File(filepath);
	    String mimetype= new MimetypesFileTypeMap().getContentType(f);

	    if(mimetype.startsWith("image/"))
	        System.out.println("It's an image");
	    else 
	        System.out.println("It's NOT an image");
	    
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run(){
				new Controller();
			}
		});
	}
	
}
