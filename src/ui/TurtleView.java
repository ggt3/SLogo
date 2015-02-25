package ui;

import java.util.Observable;
import java.util.Observer;

import model.Turtle;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class TurtleView implements Observer {
	private ImageView myImageView;
	private Canvas myCanvas;
	private Color myColor;
	private double myHeading; // in degrees, 0 is north
	private double myWidthOffset;
	private double myHeightOffset;

	public TurtleView(Image imageIn, Canvas canvasIn, Color colorIn,
			double xIn, double yIn) {
		myImageView = new ImageView();
		myCanvas = canvasIn;
		myImageView.setImage(imageIn);
		myImageView.setX(xIn);
		myImageView.setY(yIn);
		myColor = colorIn;
		
		myWidthOffset = myCanvas.getWidth()/2;
		myHeightOffset = myCanvas.getHeight()/2;

		myHeading = 50;
		myImageView.setPreserveRatio(true);
		myImageView.setSmooth(true);
	}

	public ImageView getImageView() {
		return myImageView;
	}

	public void draw() {
//		myCanvas.getGraphicsContext2D().rotate(myHeading);
		myCanvas.getGraphicsContext2D().drawImage(myImageView.getImage(),
				getCenterX(), getCenterY(), myCanvas.getHeight() / 10,
				myCanvas.getWidth() / 10);
	}

	private void drawLine(double x1, double y1, double x2, double y2) {
		myCanvas.getGraphicsContext2D().setFill(myColor);
		myCanvas.getGraphicsContext2D().setLineWidth(5);
		myCanvas.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
	}

	@Override
	public void update(Observable o, Object arg) {
		 Turtle tModel = (Turtle) o;
		 double newX = tModel.getX();
		 double newY = tModel.getY();
		 double newHeading = tModel.getHeading();
		
		 if (myHeading != newHeading) {
		 myHeading = newHeading;
		 myCanvas.getGraphicsContext2D().save();
		 myCanvas.getGraphicsContext2D().rotate(myHeading);
		 }
		 myHeading = newHeading;
		
		 if (newX != getCenterX() || newY != getCenterY()) {
		 drawLine(getCenterX(), getCenterY(), newX, newY);
		 // draw();
		 myImageView.setX(newX);
		 myImageView.setY(newY);
		 }
	}

	private double getCenterX() {
		return (myImageView.getX() - (myCanvas.getWidth() / 20)) - myWidthOffset;
	}

	private double getCenterY() {
		return (myImageView.getY() - (myCanvas.getHeight() / 20)) - myHeightOffset;
	}

}
