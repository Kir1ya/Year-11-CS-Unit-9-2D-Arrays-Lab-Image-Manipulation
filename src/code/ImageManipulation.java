package code;

import image.Pixel;
import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = new APImage("cyberpunk_2077.jpg");
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel pixel = image.getPixel(i, j);
                int average = getAverageColour(pixel);
                pixel.setRed(average);
                pixel.setGreen(average);
                pixel.setBlue(average);
            }
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();
        return (red + green + blue) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel pixel = image.getPixel(i, j);
                int average = getAverageColour(pixel);
                int search = average < 128 ? 0 : 255;
                int[] myArray = {search, search, search};
                pixel.setRed(myArray[0]);
                pixel.setGreen(myArray[1]);
                pixel.setBlue(myArray[2]);
            }
        }
        image.draw();
    }


    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
    public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel current = image.getPixel(i, j);
                Pixel left = current;
                Pixel down = current;
                if (i > 0) {
                    left = image.getPixel(i - 1, j);
                }
                if (j < height - 1) {
                    down = image.getPixel(i, j + 1);
                }
                int currentAverage = getAverageColour(current);
                int leftAverage = getAverageColour(left);
                int belowAverage = getAverageColour(down);
                int difference1 = Math.abs(currentAverage - leftAverage);
                int difference2 = Math.abs(currentAverage - belowAverage);
                if (difference1 > threshold || difference2 > threshold) {
                    current.setRed(0);
                    current.setGreen(0);
                    current.setBlue(0);
                } else {
                    current.setRed(255);
                    current.setGreen(255);
                    current.setBlue(255);
                }
            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width / 2; i++) {
                Pixel left = image.getPixel(i, j);
                Pixel right = image.getPixel(width - 1 - i, j);
                int tempRed = left.getRed();
                int tempGreen = left.getGreen();
                int tempBlue = left.getBlue();
                left.setRed(right.getRed());
                left.setGreen(right.getGreen());
                left.setBlue(right.getBlue());
                right.setRed(tempRed);
                right.setGreen(tempGreen);
                right.setBlue(tempBlue);
            }
        }
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage originalImage = new APImage(pathToFile);
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        APImage image = new APImage(originalHeight, originalWidth);

        for (int i = 0; i < originalWidth; i++) {
            for (int j = 0; j < originalHeight; j++) {
                Pixel originalPixel = originalImage.getPixel(i, j);
                int rotatedX = j;
                int rotatedY = originalWidth - 1 - i;
                int red = originalPixel.getRed();
                int green = originalPixel.getGreen();
                int blue = originalPixel.getBlue();
                Pixel rotatedPixel = new Pixel(red, green, blue);
                image.setPixel(rotatedX, rotatedY, rotatedPixel);
            }
        }
        image.draw();
    }

}
