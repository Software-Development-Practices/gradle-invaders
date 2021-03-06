package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.EnemyShip;
import entity.EnemyShipFormation;
import screen.*;

/**
 * Implements core game logic. 핵심 게임 로직을 구현합니다.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public final class Core {

	/**
	 * 화면 크기 변경을 위한 변수입니다.
	 * (0: 기본 화면 / 1: 조금 더 큰 화면 / 2: 아주 큰 화면)
	 */
	private static int screenSizeMode = 0;

	/**
	 * 난이도 변경을 위한 변수입니다.
	 * (0: Easy / 1: Normal / 2: Hard)
	 */
	private static int difficulty = 0;


	/**
	 * 음악 볼륨 컨트롤을 위한 변수입니다.
	 * (0: 0% / 1: 50% / 2: 100%)
	 */
	public static int musicVolume = 2;


	/**d
	 * Width of current screen. 현재 화면의 너비입니다.
	 */
	private static int WIDTH = 448;
	/**
	 * Height of current screen. 현재 화면의 높이입니다.
	 */
	private static int HEIGHT = 520;
	/**
	 * Max fps of current screen. 현재 화면의 최대 fps.
	 */
	private static final int FPS = 60;

	/**
	 * Max lives. 최대 목숨 개수
	 */
	private static final int MAX_LIVES = 3;
	/**
	 * Levels between extra life. 레벨 사이의 추가 목숨
	 */
	private static final int EXTRA_LIFE_FRECUENCY = 3;
	/**
	 * Total number of levels. 총 레벨 수.
	 */
	//총 레벨수 추가
	private static final int NUM_LEVELS = 10;

	/**
	 * Frame to draw the screen on. 화면을 그릴 Frame 입니다.
	 * (resize를 위해 Frame 변수를 두 개 만들어 사용합니다.)
	 */
	private static Frame frame1;
	/**
	 * Frame to draw the screen on. 화면을 그릴 Frame 입니다.
	 * (resize를 위해 Frame 변수를 두 개 만들어 사용합니다.)
	 */
	private static Frame frame2;
	/**
	 * Screen currently shown. 현재 보여지는 화면.
	 */
	private static Screen currentScreen;
	/**
	 * Difficulty settings list. 난이도 설정 목록.
	 */
	private static List<GameSettings> gameSettings;
	private static int x_0;
	private static int y_0;
	/**
	 * Application logger. 애플리케이션 로거.
	 */
	private static final Logger LOGGER = Logger.getLogger(Core.class.getSimpleName());
	/**
	 * Logger handler for printing to disk. 디스크에 프린팅하기 위한 로거 핸들러
	 */
	private static Handler fileHandler;
	/**
	 * Logger handler for printing to console. 콘솔에 프린팅하기 위한 로거 핸들러
	 */
	private static ConsoleHandler consoleHandler;


	private static Sound backGroundMusic;


	/**
	 * Test implementation. 테스트 구현.
	 *
	 * @param args Program args, ignored. 프로그램 인수, 무시됨.
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.setUseParentHandlers(false);

			fileHandler = new FileHandler("log");
			fileHandler.setFormatter(new MinimalFormatter());

			consoleHandler = new ConsoleHandler();
			consoleHandler.setFormatter(new MinimalFormatter());

			LOGGER.addHandler(fileHandler);
			LOGGER.addHandler(consoleHandler);
			LOGGER.setLevel(Level.ALL);

		} catch (Exception e) {
			// TODO handle exception
			e.printStackTrace();
		}

		GameState gameState;
		int width = 448, height= 520;
		int modiSpeed, modiFreq;

		/**
		 * 처음에 frame을 만듦
		 */
		frame1 = new Frame(WIDTH, HEIGHT);
		DrawManager.getInstance().setFrame(frame1);
		width = frame1.getWidth();
		height = frame1.getHeight();

		int returnCode = 1;
		do {
			gameState = new GameState(1, 0, MAX_LIVES, 0, 0);

			/**
			 * SettingScreen에서 정한 화면 사이즈에 맞추어 WIDTH, HEIGHT가 변경됨.
			 */
			switch (screenSizeMode) {
				case 1:
					WIDTH = 550;
					HEIGHT = 638;
					DrawManager.setEntitySize(3,3,3,3);
					EnemyShip.setModiWidth(3);	// EnemyShip의 타격범위 설정
					EnemyShipFormation.setDistance(60);
					break;
				case 2:
					WIDTH = 710;
					HEIGHT = 824;
					DrawManager.setEntitySize(4,4,4,4);
					EnemyShip.setModiWidth(4);	// EnemyShip의 타격범위 설정
					EnemyShipFormation.setDistance(80);
					break;
				default:
					WIDTH = 448;
					HEIGHT = 520;
					DrawManager.setEntitySize(2,2,1,1);
					EnemyShip.setModiWidth(2);	// EnemyShip의 타격범위 설정
					EnemyShipFormation.setDistance(40);
					break;
			}

			/**
			 * 변경된 WIDTH, HEIGHT에 맞추어 화면을 새로띄움.
			 */
			frame2 = new Frame(WIDTH, HEIGHT);
			DrawManager.getInstance().setFrame(frame2);
			width = frame2.getWidth();
			height = frame2.getHeight();

			// 이전에 띄웠던 창인 frame1을 종료함.
			frame1.dispose();

			// 새로 띄운 창인 frame2를 frame1 변수로 이동시킴.
			frame1 = frame2;

			// 게임 난이도 초기화
			gameSettings = new ArrayList<GameSettings>();

			x_0 = frame1.getX();
			y_0 = frame1.getY();

			switch (difficulty) {
				case 1:
					modiSpeed = 5;
					modiFreq = 250;
					break;
				case 2:
					modiSpeed = 10;
					modiFreq = 400;
					break;
				default:
					modiSpeed = 0;
					modiFreq = 0;
					break;
			}

			/* 레벨들의 난이도 설정 */
			/** Difficulty settings for level 1. */
			GameSettings SETTINGS_LEVEL_1 = new GameSettings(5, 4, 60-modiSpeed, 2500-modiFreq, 0);
			/** Difficulty settings for level 2. */
			GameSettings SETTINGS_LEVEL_2 = new GameSettings(5, 5, 50-modiSpeed, 2500-modiFreq, 0);
			/** Difficulty settings for Boss level 1. */
			GameSettings Boss_LEVEL_1 = new GameSettings(5, 5, 50-modiSpeed, 2500-modiFreq,1);
			/** Difficulty settings for level 3. */
			GameSettings SETTINGS_LEVEL_3 = new GameSettings(6, 5, 40-modiSpeed, 1500-modiFreq, 0);
			/** Difficulty settings for level 4. */
			GameSettings SETTINGS_LEVEL_4 = new GameSettings(6, 6, 30-modiSpeed, 1500-modiFreq, 0);
			/** Difficulty settings for Boss level 2. */
			GameSettings Boss_LEVEL_2 = new GameSettings(5, 5, 50-modiSpeed, 2500-modiFreq,2);
			/** Difficulty settings for level 5. */
			GameSettings SETTINGS_LEVEL_5 = new GameSettings(7, 6, 20-modiSpeed, 1000-modiFreq, 0);
			/** Difficulty settings for level 6. */
			GameSettings SETTINGS_LEVEL_6 = new GameSettings(7, 7, 10-modiSpeed, 1000-modiFreq, 0);
			/** Difficulty settings for Boss level 3. */
			GameSettings Boss_LEVEL_3 = new GameSettings(5, 5, 50-modiSpeed, 2500-modiFreq,3);
			/** Difficulty settings for level 7. */
			GameSettings SETTINGS_LEVEL_7 = new GameSettings(8, 7, 2-modiSpeed, 500-modiFreq, 0);

			gameSettings.add(SETTINGS_LEVEL_1);
			gameSettings.add(SETTINGS_LEVEL_2);
			gameSettings.add(Boss_LEVEL_3);
			gameSettings.add(SETTINGS_LEVEL_3);
			gameSettings.add(SETTINGS_LEVEL_4);
			gameSettings.add(Boss_LEVEL_2);
			gameSettings.add(SETTINGS_LEVEL_5);
			gameSettings.add(SETTINGS_LEVEL_6);
			gameSettings.add(Boss_LEVEL_3);
			gameSettings.add(SETTINGS_LEVEL_7);

			switch (returnCode) {
			case 1:
				// Main menu.
				backGroundMusic = new Sound("./src/main/resources/music/mainBgm.wav");
				backGroundMusic.playSoundLoop(musicVolume);
				currentScreen = new TitleScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " title screen at " + FPS + " fps.");
				returnCode = frame1.setScreen(currentScreen);
				LOGGER.info("Closing title screen.");
				backGroundMusic.pause();
				break;
			case 2:
				// Game & score.
				backGroundMusic = new Sound("./src/main/resources/music/gameScreenBgm.wav");
				backGroundMusic.playSoundLoop(musicVolume);
				do {
					// One extra live every few levels.
					boolean bonusLife = gameState.getLevel() % EXTRA_LIFE_FRECUENCY == 0
							&& gameState.getLivesRemaining() < MAX_LIVES;

					currentScreen = new GameScreen(gameState, gameSettings.get(gameState.getLevel() - 1), bonusLife,
							width, height, FPS);
					LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " game screen at " + FPS + " fps.");
					frame1.setScreen(currentScreen);
					LOGGER.info("Closing game screen.");

					gameState = ((GameScreen) currentScreen).getGameState();

					gameState = new GameState(gameState.getLevel() + 1, gameState.getScore(),
							gameState.getLivesRemaining(), gameState.getBulletsShot(), gameState.getShipsDestroyed());

				} while (gameState.getLivesRemaining() > 0 && gameState.getLevel() <= NUM_LEVELS);

				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " score screen at " + FPS + " fps, with a score of "
						+ gameState.getScore() + ", " + gameState.getLivesRemaining() + " lives remaining, "
						+ gameState.getBulletsShot() + " bullets shot and " + gameState.getShipsDestroyed()
						+ " ships destroyed.");
				currentScreen = new ScoreScreen(width, height, FPS, gameState);
				returnCode = frame1.setScreen(currentScreen);
				LOGGER.info("Closing score screen.");
				backGroundMusic.pause();
				break;
			case 3:
				// High scores.
				backGroundMusic = new Sound("./src/main/resources/music/scoreScreenBgm.wav");
				backGroundMusic.playSoundLoop(musicVolume);
				currentScreen = new HighScoreScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " high score screen at " + FPS + " fps.");
				returnCode = frame1.setScreen(currentScreen);
				LOGGER.info("Closing high score screen.");
				backGroundMusic.pause();
				break;
			case 4:
				currentScreen = new SummaryScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " setting screen at " + FPS + " fps.");
				returnCode = frame1.setScreen(currentScreen);
				break;
			case 5:
				backGroundMusic = new Sound("./src/main/resources/music/settingScreenBgm.wav");
				backGroundMusic.playSoundLoop(musicVolume);
				currentScreen = new SettingScreen(width, height, FPS);
				LOGGER.info("Starting " + WIDTH + "x" + HEIGHT + " setting screen at " + FPS + " fps.");

				returnCode = frame1.setScreen(currentScreen);
				backGroundMusic.pause();
				break;

			default:
				break;
			}

		} while (returnCode != 0);

		fileHandler.flush();
		fileHandler.close();
		System.exit(0);
	}

	/**
	 * Constructor, not called. 호출되지 않은 생성자.
	 */
	private Core() {

	}
	public static void vib(int count){

		if(count%4==0){
			frame1.setLocation(x_0-3, y_0-3);
		}
		else if(count%4==1){
			frame1.setLocation(x_0-3, y_0+3);
		}
		else if(count%4==2){
			frame1.setLocation(x_0+3, y_0-3);
		}
		else if(count%4==3){
			frame1.setLocation(x_0+3, y_0+3);
		}
		frame1.setLocation(x_0, y_0);
	}

	/**
	 * Controls access to the logger. logger에 대한 액세스를 제어합니다.
	 *
	 * @return Application logger.
	 */
	public static Logger getLogger() {
		return LOGGER;
	}

	/**
	 * Controls access to the drawing manager. drawing manager에 대한 액세스를 제어합니다.
	 *
	 * @return Application draw manager.
	 */
	public static DrawManager getDrawManager() {
		return DrawManager.getInstance();
	}

	/**
	 * Controls access to the input manager. input manager에 대한 액세스를 제어합니다.
	 *
	 * @return Application input manager.
	 */
	public static InputManager getInputManager() {
		return InputManager.getInstance();
	}

	/**
	 * Controls access to the file manager. input manager에 대한 액세스를 제어합니다.
	 *
	 * @return Application file manager.
	 */
	public static FileManager getFileManager() {
		return FileManager.getInstance();
	}

	/**
	 * Controls creation of new cooldowns. 새로운 재사용 대기시간 생성을 제어합니다.
	 *
	 * @param milliseconds Duration of the cooldown.
	 * @return A new cooldown.
	 */
	public static Cooldown getCooldown(final int milliseconds) {
		return new Cooldown(milliseconds);
	}

	/**
	 * Controls creation of new cooldowns with variance. variance가 있는 새로운 재사용 대기시간
	 * 생성을 제어합니다.
	 *
	 * @param milliseconds Duration of the cooldown. 재사용 대기시간.
	 * @param variance     Variation in the cooldown duration. 재사용 대기시간의 variance.
	 * @return A new cooldown with variance.
	 */
	public static Cooldown getVariableCooldown(final int milliseconds, final int variance) {
		return new Cooldown(milliseconds, variance);
	}

	/**
	 * screen 사이즈 모드 int 값에 대한 setter 함수입니다.
	 */
	public static void setScreenSizeMode(int mode) {
		screenSizeMode = mode;
	}


	/**
	 * PARK
	 * music volume int 값에 대한 setter 함수입니다.
	 */
	public static void setMusicVolume(int mode) {
		musicVolume = mode;
	}



	/**
	 * difficulty 모드 int 값에 대한 setter 함수입니다.
	 */
	public static void setDifficulty(int mode) {
		difficulty = mode;
	}
	/**
	 *
	 * @return setting 된 difficulty를 반환하는 getter 함수입니다.
	 */
	public static int getDifficulty() {
		return difficulty;
	}
}