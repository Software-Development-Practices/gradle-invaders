package engine;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.InputMismatchException;
import java.util.Random;

import javax.swing.JFrame;

import screen.GameScreen;
import screen.Screen;

/**
 * Implements a frame to show screens on. 화면을 표시할 프레임을 구현합니다.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
@SuppressWarnings("serial")
public class Frame extends JFrame{

	/** Frame width. */
	private final int width;
	/** Frame height. */
	private final int height;
	/** Screen currently shown. */
	private Screen currentScreen;

	/**
	 * Initializes the new frame.
	 * 
	 * @param width  Frame width.
	 * @param height Frame height.
	 */
	public Frame(final int width, final int height) {
		setSize(width, height);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setVisible(true);

		Insets insets = getInsets();
		this.width = width - insets.left - insets.right;
		this.height = height - insets.top + insets.bottom;
		setTitle("Invaders");

		addKeyListener(Core.getInputManager());
	}

	/**
	 * Sets current screen.
	 * 
	 * @param screen Screen to show.
	 * @return Return code of the finished screen.
	 */
	public final int setScreen(final Screen screen) {
		currentScreen = screen;
		currentScreen.initialize();
		return currentScreen.run();
	}

	/**
	 * Getter for frame width.
	 * 
	 * @return Frame width.
	 */
	public final int getWidth() {
		return this.width;
	}

	/**
	 * Getter for frame height.
	 * 
	 * @return Frame height.
	 */

	public final int getHeight() {
		return this.height;
	}

//
//	private Thread th; //진동하는 스레드
//	public void vibrating() throws InterruptedException {
//		th=new Thread(this); //진동하는 스레드 객체 생성
//		th.start();//진동시작
//	}

	public void run() {
		Random r = new Random();
		int x_0 = getX();
		int y_0 = getY();
		int count = 0;
		while (true) {
//			try {
//				Thread.sleep(0);
//			}
//
//			catch(InputMismatchException | InterruptedException e) {
//
//				return;
//			}
			if(count%4==0){
				setLocation(x_0-3, y_0-3);
			}
			else if(count%4==1){
				setLocation(x_0-3, y_0+3);
			}
			else if(count%4==2){
				setLocation(x_0+3, y_0-3);
			}
			else if(count%4==3){
				setLocation(x_0+3, y_0+3);

			}
			setLocation(x_0, y_0);
			count++;

			if (count > 100) {
				return;
			}
		}
	}

	public int getxpoint(){
		return this.getX();
	}

	public int getypoint(){
		return this.getY();
	}
}
