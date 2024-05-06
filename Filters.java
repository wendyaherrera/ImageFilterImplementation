import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Filters {

  public static void main(String[] args)throws IOException {

    // Read in the image file.
    File f = new File("dog.png");
    BufferedImage img = ImageIO.read(f);

    // Applies a filter implementation on img 
    //applyGrayscale(img);
    //applyNorak(img);
    //applyMirror(img);

    //applies the border filter to the image
    int borderThickness = 15; //chooses the border thickness 
    int[] borderColor = {235,64,52}; //chooses the border color, in this case a red
    //applyBorder(img,borderThickness,borderColor); //gives the image, thickness and color to the border filter method

    //applies the custom filter, which creates a drawing-like version of the image
    //rgb color arrays for custom filter, with names that match the colors
    int[] silver = {192,192,192}; 
    int[] black = {0,0,0}; 
    int[] gray = {128,128,128}; 
    int[] wheat = {245,222,179};
    int[] tan = {210,180,140};
    applyCustom(img,silver,black,gray,wheat,tan);

    // Writes the result to a new image file.
    f = new File("dog_filtered.png");
    ImageIO.write(img, "png", f);
  }

  public static void applyGrayscale(BufferedImage img) { //Grayscale filter method

    int width = img.getWidth(); //width variable
    int height = img.getHeight(); //height variable
    int average = 0; //to store the average

    for(int i = 0; i < height; i ++){ //traverses through the image array height
      for(int j = 0; j < width; j++){ //traverses through the image array width

        int[] pixel = Utilities.getRGBArray(i, j, img); //gets the RGB array in the current array position of the image
        average = (pixel[0]+pixel[1]+pixel[2])/ 3; //uses the RGB array indexes to calculate the average
        Utilities.setRGB(new int []{average, average, average}, i, j, img); //updates the rgb values to be the average
      }
    }
  }

  public static void applyNorak(BufferedImage img) { //Norak filter method

    int width = img.getWidth(); //width variable
    int height = img.getHeight(); //height variable
    int average = 0; //to store the average

    for(int i = 0; i < height; i ++){ //traverses through the image array height
      for(int j = 0; j < width; j++){ //traverses through the image array width

        int[] pixel = Utilities.getRGBArray(i, j, img); //gets the RGB array in the current array position of the image
        average = (pixel[0]+pixel[1]+pixel[2])/ 3; //uses the RGB array indexes to calculate the average

        if(average > 153){ //checks if the pixel is a bright pixel

          Utilities.setRGB(new int[]{average, average, average}, i, j, img); //updates the rgb values to be the average
        }
      }
    }
  }

  public static void applyBorder(BufferedImage img, int borderThickness, int[] borderColor) { //Border filter method
    int width = img.getWidth(); //width variable
    int height = img.getHeight(); //height variable

    for ( int i = 0; i < height; i ++){ //traverses through the image array height
      for (int j = 0; j < width ; j ++){ //traverses through the image array width 

        int[] pixel = Utilities.getRGBArray(i, j, img); //gets the RGB array in the current array position of the image

        //checks if the rows or columns array position is before the border limit
        // this will create the borders at the top (using i) and left side of the image (using j)
        if(i < borderThickness || j < borderThickness ){ //arrays start at index 0, so i & j must be less than the provided thickness value 

          Utilities.setRGB(borderColor,i, j, img); //makes the pixel array rgb values the values of the assigned border color rgb
        }
        // checks if the rows or colums array position is within the desired border area, before reaching the height and width limit
        //this will create the borders at the right(using j) and bottom side (using i) of the image
        if(i < height && i > height - 1 - borderThickness || j < width && j > width - 1 - borderThickness){ 

          Utilities.setRGB(borderColor,i, j, img); //makes the RGB values the assigned border color array
        }
      }
    }
  }

  public static void applyMirror(BufferedImage img) { //Mirror filter method
    int width = img.getWidth(); // width variable
    int height = img.getHeight(); // height variable
    int reverse = width - 1; //to traverse the array in reverse

    for ( int i = 0; i < height; i ++){ //traverses through the image array height
      reverse = width - 1; //to always get the index position of the last column of the row
      for (int j = 0 ; j < width/2 ; j ++){ //traverses halfway through the image columns/width (because a reflection is created with the halfs of an image)

        int[] pixel = Utilities.getRGBArray(i, j, img); //gets the RBG values array in the current array position of the image
        int[] temp = pixel; //stores the array in a temporary array variable 
        int[] mirror = Utilities.getRGBArray(i,reverse,img); //gets the RGB values array from the last column of the row
        Utilities.setRGB(temp,i,reverse,img); //switches the mirror array to have the temp array values
        Utilities.setRGB(mirror,i,j,img); //switches the pixel/temp array to have the mirror array values
        reverse --; //to traverse the columns backwards to get to needed mirror array
      }
    }
  }


  //Creates a filter that makes the image look like a drawing
  public static void applyCustom(BufferedImage img, int[] silver, int[] black, int[]gray, int[] wheat, int[] tan) { //to 
    int width = img.getWidth(); //width variable
    int height = img.getHeight(); //height variable
    int average = 0;

    for(int i = 0; i < height; i ++){ //traverses through the image array height
      for(int j = 0; j < width; j++){ //traverses through the image array width

        int[] pixel = Utilities.getRGBArray(i, j, img); //gets the RBG values array in the current array position of the image
        average = (pixel[0]+pixel[1]+pixel[2])/ 3; //calculates the average of the array

        //Checks if the array is in the area of the top right quadrant of the image or the bottom left quadrant of the image
        if(i >= height/2 && j <= width/2 || i <= height/2 && j >= width/2){

          if(average < 70 ){ //checks if color is dark color similar to black, these have averages of less than 70 

            Utilities.setRGB(black,i,j,img); //makes the pixel a solid black color

          } else if (average > 120 && average < 190){ //checks if color is within colors that are not too light or too dark

              Utilities.setRGB(tan,i,j,img);  //makes the pixel a tan brownish color

          }else{

            Utilities.setRGB(wheat,i,j,img); //makes all other pixels a wheat(similar to beige and brown) color
          }

        }else{ //applies to the other two quadrants of the image

          if(average < 70 ){ //checks if color is dark color similar to black, these have averages of less than 70 

            Utilities.setRGB(black,i,j,img); //makes the pixel a solid black color

          }else if (average > 120 && average < 190){ //checks if color is within colors that are not too light or too dark

            Utilities.setRGB(silver,i,j,img);  //makes the pixel a solid silver color

          }else{

            Utilities.setRGB(gray,i,j,img); //makes all other pixels a solid gray color
          }
        }

      }

    }

  }

}
