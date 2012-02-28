package game.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class Texture2D
{
    private BufferedImage bufferedImage;
    private final String contentLocation;
    
    /**
     * Complete Constructor
     * Easy way to store and load images for your program.
     * @param path Path to where the image is stored.
     */
    public Texture2D(String path)
    {
        this.contentLocation = path;
        this.loadImage(contentLocation);
    }
    
    /**
     * Easy way to store and load images for your program.
     * @param bufferedImage BufferedImage you want to turn into a Texture2D.
     */
    public Texture2D(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
        this.contentLocation = null;
    }
    
    /**
     * Takes a path and loads the image regardless of the OS in use.
     * @param path Path to the image you want to load.
     * @return Returns a boolean to note the success of the operation.
     */
    private boolean loadImage(String path)
    {
        boolean loaded = false;
        URL systemIndependentPath = getClass().getResource(path);
        try
        {
            this.bufferedImage = ImageIO.read(systemIndependentPath);
            loaded = true;
        }
        catch(Exception ex)
        {
            this.bufferedImage = null;
            System.out.println("Could not load image: " + path);
        }
        return loaded;
    }
    
    /**
     * @return Returns the BufferedImage held in this Texture2D. 
     */
    public BufferedImage getImage()
    {
        return this.bufferedImage;
    }
    
    /**
     * Sets the BufferedImage in the Texture2D
     * @param bufferedImage BufferedImage to be stored in this Texture2D.
     */
    public void setImage(BufferedImage bufferedImage)
    {
        this.bufferedImage = bufferedImage;
    }
    
    /**
     * Resizes a Texture2D.
     * @param tex Texture2D you want to resize.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return Returns the scaled Texture2D.
     */
    public static Texture2D resize(Texture2D tex, int width, int height)
    {
        int type = tex.getImage().getType() == 0? BufferedImage.TYPE_INT_ARGB : tex.getImage().getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
	Graphics2D g = resizedImage.createGraphics();
	g.setComposite(AlphaComposite.Src);
	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	g.setRenderingHint(RenderingHints.KEY_RENDERING,
	RenderingHints.VALUE_RENDER_QUALITY);
	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(tex.getImage(), 0, 0, width, height, null);
	g.dispose();
        return new Texture2D(resizedImage);
    }
    
    /**
     * Resizes a BufferedImage
     * @param bi A BufferedImage to be resized.
     * @param width The width of the resized image.
     * @param height The height of the resized image.
     * @return Returns the scaled BufferedImage as a Texture2D.
     */
    public static Texture2D resize(BufferedImage bi, int width, int height)
    {
        return Texture2D.resize(new Texture2D(bi), width, height);
    }
}
