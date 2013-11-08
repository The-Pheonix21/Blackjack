import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class cardImage {
	public String name;
	private Image image;
	public cardImage(String name) {
		this.name=name;
		this.image = cardImage.loadImage(name);
	}
	public void draw(Graphics g, Rectangle r) {
		g.drawImage(image, r.x, r.y, r.width, r.height, null);
	}
	private static Image loadImage(String name) {
		String path = null;
		Image image = null;

	
		try{
		path = "cards" + File.separator + name + ".png";	
		File file = new File(path);
		image = ImageIO.read(file);
		}catch   (IOException e) {
			System.out.println("Could not load image at path" + path);
			System.exit(1);

		
		}
	return image;}
}