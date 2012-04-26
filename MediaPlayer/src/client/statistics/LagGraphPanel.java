package client.statistics;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import java.awt.Color;

	/**
	 * @author jwb09119
	 * @date 25/04/2012
	 * 
	 *Represents graphing data, obtained by observing an
	 * updating data source (model)
	 *
	 */

public class LagGraphPanel extends JPanel implements Observer{
	private LagGraphViewmodel viewModel;
	private final Color BACKGROUND = Color.BLACK;
	private final Color[] LINES = {Color.RED, Color.ORANGE, Color.GREEN};
	
	private int[] sentData;
	private int[] recievedData;
	private int[] playedData;
	
	public LagGraphPanel(Dimension size, Observable model) {
		viewModel = new LagGraphViewmodel(model);
		viewModel.addObserver(this);
		
		sentData = new int[100];
		recievedData = new int[100];
		playedData = new int[100];
		
		Arrays.fill(sentData, 0);
		Arrays.fill(recievedData, 0);
		Arrays.fill(playedData, 0);
		
		this.setSize(size);
	}
	
	
	private void initialiseSwing(){
		this.setMinimumSize(this.getSize());
		this.setMaximumSize(this.getSize());
		this.setDoubleBuffered(true);
		this.setVisible(true);
	}
	
	
	@Override
	public void paint(Graphics g){
		// Fill background
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		// Draw graph line
		g.setColor(LINES[0]);
		int size = sentData.length;
		
		float widthScale = ((float) this.getWidth())/size;
		float heightScale = ((float) this.getHeight())/100;
		

		int[] lastRecieved = {((int) 0), ((int) (recievedData[0]*heightScale))};
		int[] lastPlayed = {((int) 0), ((int) (playedData[0]*heightScale))};
		
		// Draw sent line
		g.setColor(LINES[0]);
		int lastY = (int) (sentData[0]*heightScale);
		int lastX = 0;
		for (int i = 1; i < size; i++) {
			
			int newBarXStart = lastX;
			int newBarYStart = (int) (sentData[i]*heightScale);
			int newBarXEnd = (int) (lastX + widthScale);
			int newBarYEnd = newBarYStart;
			
			g.drawLine(lastX, lastY, newBarXStart, newBarYStart);
			g.drawLine(newBarXStart, newBarYStart, newBarXEnd, newBarYEnd);
			
			lastX = newBarXEnd;
			lastY = newBarYEnd;
			
		}
		
		// Draw received line
		g.setColor(LINES[1]);
		lastY = (int) (recievedData[0]*heightScale);
		lastX = 0;
		for (int i = 1; i < size; i++) {
			
			int newBarXStart = lastX;
			int newBarYStart = (int) (recievedData[i]*heightScale);
			int newBarXEnd = (int) (lastX + widthScale);
			int newBarYEnd = newBarYStart;
			
			g.drawLine(lastX, lastY, newBarXStart, newBarYStart);
			g.drawLine(newBarXStart, newBarYStart, newBarXEnd, newBarYEnd);
			
			lastX = newBarXEnd;
			lastY = newBarYEnd;
			
		}
		
		// Draw played line
		g.setColor(LINES[2]);
		lastY = (int) (playedData[0]*heightScale);
		lastX = 0;
		for (int i = 1; i < size; i++) {
			
			int newBarXStart = lastX;
			int newBarYStart = (int) (playedData[i]*heightScale);
			int newBarXEnd = (int) (lastX + widthScale);
			int newBarYEnd = newBarYStart;
			
			g.drawLine(lastX, lastY, newBarXStart, newBarYStart);
			g.drawLine(newBarXStart, newBarYStart, newBarXEnd, newBarYEnd);
			
			lastX = newBarXEnd;
			lastY = newBarYEnd;
			
		}
		
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		int[][] data = (int[][]) arg1;
		
		sentData = data[0];
		recievedData = data[1];
		playedData = data[2];
		
		this.repaint();
		
	}
}