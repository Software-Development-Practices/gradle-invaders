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

	/**
	 * ^^
	 * @return frame의 x위치 반환
	 */
	public int getxpoint(){
		return this.getX();
	}

	/**
	 *^^
	 * @return frame의 y위치 반환
	 */
	public int getypoint(){
		return this.getY();
	}
}
