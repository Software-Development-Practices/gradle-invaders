package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import screen.Screen;
import entity.Entity;
import entity.Ship;

/**
 * Manages screen drawing. 화면 그리기를 관리합니다.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public final class DrawManager {

    /**
     * Singleton instance of the class. 클래스의 싱글톤 인스턴스입니다.
     */
    private static DrawManager instance;
    /**
     * Current frame.
     */
    private static Frame frame;
    /**
     * FileManager instance.
     */
    private static FileManager fileManager;
    /**
     * Application logger.
     */
    private static Logger logger;
    /**
     * Graphics context.
     */
    private static Graphics graphics;
    /**
     * Buffer Graphics.
     */
    private static Graphics backBufferGraphics;
    /**
     * Buffer image.
     */
    private static BufferedImage backBuffer;
    /**
     * Normal sized font.
     */
    private static Font fontRegular;
    /**
     * Normal sized font properties.
     */
    private static FontMetrics fontRegularMetrics;
    /**
     * Big sized font.
     */
    private static Font fontBig;
    /**
     * Big sized font properties.
     */
    private static FontMetrics fontBigMetrics;

    /**
     * Regular와 Big 사이의 적당한 크기의 폰트입니다.
     */
    private static Font fontMiddle;

    /**
     * Small sized font.
     */
    private static Font fontSmall;

    /**
     * Sprite types mapped to their images. 이미지에 매핑된 스프라이트 유형입니다.
     */
    private static Map<SpriteType, boolean[][]> spriteMap;

    /**
     * Sprite types.
     */
    public static enum SpriteType {
        /**
         * Player ship.
         */
        Ship,
        /**
         * Destroyed player ship.
         */
        ShipDestroyed,
        /**
         * Player bullet.
         */
        Bullet,
        /**
         * Enemy bullet.
         */
        EnemyBullet,
        /**
         * First enemy ship - first form.
         */
        EnemyShipA1,
        /**
         * First enemy ship - second form.
         */
        EnemyShipA2,
        /**
         * Second enemy ship - first form.
         */
        EnemyShipB1,
        /**
         * Second enemy ship - second form.
         */
        EnemyShipB2,
        /**
         * Third enemy ship - first form.
         */
        EnemyShipC1,
        /**
         * Third enemy ship - second form.
         */
        EnemyShipC2,
        /**
         * Bonus ship.
         */
        EnemyShipSpecial,
        /**
         * Destroyed enemy ship.
         */
        Explosion,
        /**
         * boss1 first form.
         */
        BossA1,
        /**
         * boss1 second form.
         */
        BossA2,
        /**
         * boss2 first form.
         */
        BossB1,
        /**
         * boss2 second form.
         */
        BossB2,
        /**
         * boss3 first form.
         */
        BossC1,
        /**
         * boss3 second form.
         */
        BossC2,
        /**
         * Destroyed Boss.
         */
        BossExplosion,

        /**
         * Destroyed First enemy ship - first form.
         */
        DestroyedEnemyShipA1,
        /**
         * Destroyed First enemy ship - second form.
         */
        DestroyedEnemyShipA2,
        /**
         * Destroyed Second enemy ship - first form.
         */
        DestroyedEnemyShipB1,
        /**
         * Destroyed Second enemy ship - second form.
         */
        DestroyedEnemyShipB2,
        /**
         * Destroyed Third enemy ship - first form.
         */
        DestroyedEnemyShipC1,
        /**
         * Destroyed Third enemy ship - second form.
         */
        DestroyedEnemyShipC2,


        DestroyedEnemyShipSpecial
    }

    /**
     * resize시 entity 크기도 키우기 위해 사용되는 변수들입니다.
     */
    private static int x = 2, y = 2, width = 1, height = 1;

    /**
     * resize시 entity 크기도 키우기 위해 사용되는 변수들의 setter함수입니다.
     *
     * @param x_para
     * @param y_para
     * @param width_para
     * @param height_para
     */
    public static void setEntitySize(int x_para, int y_para, int width_para, int height_para) {
        x = x_para;
        y = y_para;
        width = width_para;
        height = height_para;
    }
    /**
     * ^^
     * @return x-axis for bullet
     */
    public static int getXAxis(){
        return x;
    }

    /**
     * Private constructor. private 생성자
     */
    private DrawManager() {
        fileManager = Core.getFileManager();
        logger = Core.getLogger();
        logger.info("Started loading resources.");


        try {
            spriteMap = new LinkedHashMap<SpriteType, boolean[][]>();

            spriteMap.put(SpriteType.Ship, new boolean[13][8]);
            spriteMap.put(SpriteType.ShipDestroyed, new boolean[13][8]);
            spriteMap.put(SpriteType.Bullet, new boolean[3][5]);
            spriteMap.put(SpriteType.EnemyBullet, new boolean[3][5]);
            spriteMap.put(SpriteType.EnemyShipA1, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipA2, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipB1, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipB2, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipC1, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipC2, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipA1, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipA2, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipB1, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipB2, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipC1, new boolean[12][8]);
            spriteMap.put(SpriteType.DestroyedEnemyShipC2, new boolean[12][8]);
            spriteMap.put(SpriteType.EnemyShipSpecial, new boolean[16][7]);
            spriteMap.put(SpriteType.Explosion, new boolean[13][7]);
            spriteMap.put(SpriteType.BossA1, new boolean[12][8]);
            spriteMap.put(SpriteType.BossA2, new boolean[12][8]);
            spriteMap.put(SpriteType.BossB1, new boolean[12][8]);
            spriteMap.put(SpriteType.BossB2, new boolean[12][8]);
            spriteMap.put(SpriteType.BossC1, new boolean[12][8]);
            spriteMap.put(SpriteType.BossC2, new boolean[12][8]);
            spriteMap.put(SpriteType.BossExplosion, new boolean[13][7]);
            spriteMap.put(SpriteType.DestroyedEnemyShipSpecial, new boolean[16][7]);


            fileManager.loadSprite(spriteMap);
            logger.info("Finished loading the sprites.");

            // Font loading.
            fontSmall = fileManager.loadFont(12f);
            fontRegular = fileManager.loadFont(14f);
            fontMiddle = fileManager.loadFont(19f);
            fontBig = fileManager.loadFont(24f);
            logger.info("Finished loading the fonts.");

        } catch (IOException e) {
            logger.warning("Loading failed.");
        } catch (FontFormatException e) {
            logger.warning("Font formating failed.");
        }
    }

    /**
     * Returns shared instance of DrawManager. DrawManager의 공유된 인스턴스를 반환합니다.
     *
     * @return Shared instance of DrawManager.
     */
    protected static DrawManager getInstance() {
        if (instance == null)
            instance = new DrawManager();
        return instance;
    }

    /**
     * Sets the frame to draw the image on. 이미지를 그릴 프레임을 설정합니다.
     *
     * @param currentFrame Frame to draw on.
     */
    public void setFrame(final Frame currentFrame) {
        frame = currentFrame;
    }

    /**
     * First part of the drawing process. Initializes buffers, draws the background
     * and prepares the images. 드로잉 프로세스의 첫 번째 부분입니다. 버퍼를 초기화하고 배경을 그리고 이미지를 준비합니다.
     *
     * @param screen Screen to draw in. 그릴 수 있는 화면입니다.
     */
    public void initDrawing(final Screen screen) {
        backBuffer = new BufferedImage(screen.getWidth(), screen.getHeight(), BufferedImage.TYPE_INT_RGB);

        graphics = frame.getGraphics();
        backBufferGraphics = backBuffer.getGraphics();

        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(0, 0, screen.getWidth(), screen.getHeight());

        fontRegularMetrics = backBufferGraphics.getFontMetrics(fontRegular);
        fontBigMetrics = backBufferGraphics.getFontMetrics(fontBig);

        // drawBorders(screen);
        // drawGrid(screen);
    }

    /**
     * Draws the completed drawing on screen. 완성된 그림을 화면에 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     */
    public void completeDrawing(final Screen screen) {
        graphics.drawImage(backBuffer, frame.getInsets().left, frame.getInsets().top, frame);
    }

    /**
     * Draws an entity, using the appropriate image. 적절한 이미지를 사용하여 entity를 그립니다.
     *
     * @param entity    Entity to be drawn. 그려질 entity 입니다.
     * @param positionX Coordinates for the left side of the image. 이미지의 왼쪽에 대한
     *                  좌표입니다.
     * @param positionY Coordinates for the upper side of the image. 이미지의 위쪽에 대한
     *                  좌표입니다.
     */
    public void drawEntity(final Entity entity, final int positionX, final int positionY) {
        boolean[][] image = spriteMap.get(entity.getSpriteType());

        backBufferGraphics.setColor(entity.getColor());
        //보스 1,2,3, bossExplosion
        switch (entity.getSpriteType()) {
            case BossA1:
            case BossA2:
            case BossB1:
            case BossB2:
            case BossC1:
            case BossC2:
            case BossExplosion:
                for (int i = 0; i < image.length; i++)
                    for (int j = 0; j < image[i].length; j++)
                        if (image[i][j])
                            backBufferGraphics.fillRect(positionX + i * 10 * x, positionY
                                    + j * 10 * y, 10 * width, 10 * height);
                break;
            case DestroyedEnemyShipA1:
            case DestroyedEnemyShipA2:
            case DestroyedEnemyShipB1:
            case DestroyedEnemyShipB2:
            case DestroyedEnemyShipC1:
            case DestroyedEnemyShipC2:
            case DestroyedEnemyShipSpecial:
                for (int i = 0; i < image.length; i++) {
                    for (int j = 0; j < image[i].length; j++) {
                        if (image[i][j]) {
                            if (x == 2) {
                                backBufferGraphics.drawRect(positionX + i * 3 - 5, positionY + j * 3 - 5, 1, 1);
                            } else {
                                logger.info(width + " sd");
                                logger.info(height + "heigh");
                                backBufferGraphics.fillRect(positionX + i * x * 2 - width * 5, positionY + j * y * 2 - height * 5, width, height);
                            }
                        }
                    }
                }
                break;
            default:
                for (int i = 0; i < image.length; i++) {
                    for (int j = 0; j < image[i].length; j++) {
                        if (image[i][j]) {
                            // 작은 창에서 fillReat를 쓰면 Entity가 깨져서 작은창에서만 drawRect를 쓸 수 있게해줌.
                            if (x == 2) {
                                backBufferGraphics.drawRect(positionX + i * 2, positionY + j * 2, 1, 1);
                            } else {
                                backBufferGraphics.fillRect(positionX + i * x, positionY + j * y, width, height);
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * For debugging purposes, draws the canvas borders. 디버깅을 위해 캔버스 테두리를 그립니다.
     *
     * @param screen Screen to draw in. 그릴 수 있는 화면입니다.
     */
    @SuppressWarnings("unused")
    private void drawBorders(final Screen screen) {
        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.drawLine(0, 0, screen.getWidth() - 1, 0);
        backBufferGraphics.drawLine(0, 0, 0, screen.getHeight() - 1);
        backBufferGraphics.drawLine(screen.getWidth() - 1, 0, screen.getWidth() - 1, screen.getHeight() - 1);
        backBufferGraphics.drawLine(0, screen.getHeight() - 1, screen.getWidth() - 1, screen.getHeight() - 1);
    }

    /**
     * For debugging purposes, draws a grid over the canvas. 디버깅을 위해 캔버스 위에 그리드를
     * 그립니다.
     *
     * @param screen Screen to draw in. 그릴 수 있는 화면입니다.
     */
    @SuppressWarnings("unused")
    private void drawGrid(final Screen screen) {
        backBufferGraphics.setColor(Color.DARK_GRAY);
        for (int i = 0; i < screen.getHeight() - 1; i += 2)
            backBufferGraphics.drawLine(0, i, screen.getWidth() - 1, i);
        for (int j = 0; j < screen.getWidth() - 1; j += 2)
            backBufferGraphics.drawLine(j, 0, j, screen.getHeight() - 1);
    }

    /**
     * Draws current score on screen. 화면에 현재 점수를 그립니다.
     *
     * @param screen Screen to draw on. 그릴 수 있는 화면입니다.
     * @param score  Current score. 현재 점수.
     */
    public void drawScore(final Screen screen, final int score) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        String scoreString = String.format("%04d", score);
        backBufferGraphics.drawString(scoreString, screen.getWidth() - 60, 25);
    }

    /**
     * Draws number of remaining lives on screen. 화면에 남은 목숨 수를 그립니다.
     *
     * @param screen Screen to draw on. 그릴 수 있는 화면입니다.
     * @param lives  Current lives. 현재 목숨.
     */
    public void drawLives(final Screen screen, final int lives, GameSettings gameSettings) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.setColor(Color.WHITE);
        backBufferGraphics.drawString(Integer.toString(lives), 20, 25);
        Ship dummyShip = new Ship(0, 0, gameSettings);
        for (int i = 0; i < lives; i++)
            //            drawEntity(dummyShip, 40 + 35 * i, 10); //min (default)
//            drawEntity(dummyShip, 40 + 45 * i, 10); // medium
            drawEntity(dummyShip, 40 + 60 * i, 7); //max
    }

    /**
     * Draws a thick line from side to side of the screen. 화면의 좌우로 굵은 선을 그립니다.
     *
     * @param screen    Screen to draw on. 그릴 수 있는 화면입니다.
     * @param positionY Y coordinate of the line. 선의 Y 좌표입니다.
     */
    public void drawHorizontalLine(final Screen screen, final int positionY) {
        backBufferGraphics.setColor(Color.GREEN);
        backBufferGraphics.drawLine(0, positionY, screen.getWidth(), positionY);
        backBufferGraphics.drawLine(0, positionY + 1, screen.getWidth(), positionY + 1);
    }

    /**
     * Draws game title. 게임 제목을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 수 있는 화면입니다.
     */
    public void drawTitle(final Screen screen, String title, int yWeight) {
        String titleString = title;
        String instructionsString = "select with w+s / arrows, confirm with space";
        if (title.equals("Invaders")) {
            backBufferGraphics.setColor(Color.GRAY);
            drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 2);
        }
        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredBigString(screen, titleString, screen.getHeight() / yWeight);
    }

    /**
     * Draws main menu. 메인 메뉴를 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param option Option selected. 선택된 옵션.
     */
    public void drawMenu(final Screen screen, final int option) {
        String playString = "Play";
        String highScoresString = "High scores";
        String gameSummaryString = "Game Summary";
        String settingString = "Setting";
        String exitString = "exit";

        if (option == 2)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, playString, screen.getHeight() / 3 * 2 - fontRegularMetrics.getHeight() * 2);
        if (option == 3)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, highScoresString,
                screen.getHeight() / 3 * 2);
        if (option == 4)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, gameSummaryString, screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 2);
        if (option == 5)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, settingString, screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 4);
        if (option == 0)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, exitString, screen.getHeight() / 3 * 2 + fontRegularMetrics.getHeight() * 6);
    }

    /**
     * Draws game results. 게임 결과를 그립니다.
     *
     * @param screen         Screen to draw on. 그릴 화면입니다.
     * @param score          Score obtained. 획득한 점수입니다.
     * @param livesRemaining Lives remaining when finished. 끝났을 때 남아있는 목숨 수.
     * @param shipsDestroyed Total ships destroyed. 파괴된 총 ships.
     * @param accuracy       Total accuracy. 전체 총 정확도.
     * @param isNewRecord    If the score is a new high score. 점수가 새로운 최고 점수인 경우.
     */
    public void drawResults(final Screen screen, final int score, final int livesRemaining, final int shipsDestroyed,
                            final float accuracy, final boolean isNewRecord) {
        String scoreString = String.format("score %04d", score);
        String livesRemainingString = "lives remaining " + livesRemaining;
        String shipsDestroyedString = "enemies destroyed " + shipsDestroyed;
        String accuracyString = String.format("accuracy %.2f%%", accuracy * 100);

        int height = isNewRecord ? 4 : 2;

        backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, scoreString, screen.getHeight() / height);
        drawCenteredRegularString(screen, livesRemainingString,
                screen.getHeight() / height + fontRegularMetrics.getHeight() * 2);
        drawCenteredRegularString(screen, shipsDestroyedString,
                screen.getHeight() / height + fontRegularMetrics.getHeight() * 4);
        drawCenteredRegularString(screen, accuracyString,
                screen.getHeight() / height + fontRegularMetrics.getHeight() * 6);
    }

    /**
     * Draws interactive characters for name input. 이름 입력을 위한 interactive
     * characters를 그립니다.
     *
     * @param screen           Screen to draw on. 그릴 화면입니다.
     * @param name             Current name selected. 현재 선택된 이름.
     * @param nameCharSelected Current character selected for modification. 수정을 위해
     *                         선택된 현재 character입니다.
     */
    public void drawNameInput(final Screen screen, final char[] name, final int nameCharSelected) {
        String newRecordString = "New Record!";
        String introduceNameString = "Introduce name:";

        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredRegularString(screen, newRecordString,
                screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 10);
        backBufferGraphics.setColor(Color.WHITE);
        drawCenteredRegularString(screen, introduceNameString,
                screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 12);

        // 3 letters name.
        int positionX = screen.getWidth() / 2
                - (fontRegularMetrics.getWidths()[name[0]] + fontRegularMetrics.getWidths()[name[1]]
                + fontRegularMetrics.getWidths()[name[2]] + fontRegularMetrics.getWidths()[' ']) / 2;

        for (int i = 0; i < 3; i++) {
            if (i == nameCharSelected)
                backBufferGraphics.setColor(Color.GREEN);
            else
                backBufferGraphics.setColor(Color.WHITE);

            positionX += fontRegularMetrics.getWidths()[name[i]] / 2;
            positionX = i == 0 ? positionX
                    : positionX
                    + (fontRegularMetrics.getWidths()[name[i - 1]] + fontRegularMetrics.getWidths()[' ']) / 2;

            backBufferGraphics.drawString(Character.toString(name[i]), positionX,
                    screen.getHeight() / 4 + fontRegularMetrics.getHeight() * 14);
        }
    }

    /**
     * Draws basic content of game over screen. 게임의 기본 콘텐츠를 화면 위에 그립니다.
     *
     * @param screen       Screen to draw on. 그릴 화면입니다.
     * @param acceptsInput If the screen accepts input. 화면이 입력을 수락하는 경우.
     * @param isNewRecord  If the score is a new high score. 점수가 새로운 최고 점수인 경우.
     */
    public void drawGameOver(final Screen screen, final boolean acceptsInput, final boolean isNewRecord) {
        String gameOverString = "Game Over";
        String continueOrExitString = "Press Space to play again, Escape to exit";

        int height = isNewRecord ? 4 : 2;

        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredBigString(screen, gameOverString, screen.getHeight() / height - fontBigMetrics.getHeight() * 2);

        if (acceptsInput)
            backBufferGraphics.setColor(Color.GREEN);
        else
            backBufferGraphics.setColor(Color.GRAY);
        drawCenteredRegularString(screen, continueOrExitString,
                screen.getHeight() / 2 + fontRegularMetrics.getHeight() * 10);
    }

    /**
     * Draws high score screen title and instructions. 고득점 화면 제목과 지침을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     */
    public void drawHighScoreMenu(final Screen screen) {
        String highScoreString = "High Scores";
        String instructionsString = "Press Space to return";

        backBufferGraphics.setColor(Color.GREEN);
        drawCenteredBigString(screen, highScoreString, screen.getHeight() / 8);

        backBufferGraphics.setColor(Color.GRAY);
        drawCenteredRegularString(screen, instructionsString, screen.getHeight() / 5);
    }

    /**
     * Draws high scores. 높은 점수를 그립니다.
     *
     * @param screen     Screen to draw on. 그릴 화면입니다.
     * @param highScores List of high scores. 높은 점수 목록입니다.
     */
    public void drawHighScores(final Screen screen, final List<Score> highScores) {
        backBufferGraphics.setColor(Color.WHITE);
        int i = 0;
        String scoreString = "";

        for (Score score : highScores) {
            scoreString = String.format("%s        %04d", score.getName(), score.getScore());
            drawCenteredRegularString(screen, scoreString,
                    screen.getHeight() / 4 + fontRegularMetrics.getHeight() * (i + 1) * 2);
            i++;
        }
    }

    /**
     * Draws a centered string on regular font. regular font로 centered string을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param string String to draw. 그릴 문자열입니다.
     * @param height Height of the drawing. 그림의 높이입니다.
     */
    public void drawCenteredRegularString(final Screen screen, final String string, final int height) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontRegularMetrics.stringWidth(string) / 2,
                height);
    }

    /**
     * Draws a string on regular font. regular font로 centered string을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param string String to draw. 그릴 문자열입니다.
     * @param width  Weight of the drawing. 그림의 넓이입니다.
     * @param height Height of the drawing. 그림의 높이입니다.
     */
    public void drawRegularString(final Screen screen, final String string, final int width, final int height) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawString(string, width, height);
    }

    /**
     * regular font보단 조금 크고 Big font보단 조금 작은 크기의 string을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param string String to draw. 그릴 문자열입니다.
     * @param width  Weight of the drawing. 그림의 넓이입니다.
     * @param height Height of the drawing. 그림의 높이입니다.
     */
    public void drawMiddleString(final Screen screen, final String string, final int width, final int height) {
        backBufferGraphics.setFont(fontMiddle);
        backBufferGraphics.drawString(string, width, height);
    }

    /**
     * Draws a string on regular font.
     * small font로 string을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param string String to draw. 그릴 문자열입니다.
     * @param width  Weight of the drawing. 그림의 넓이입니다.
     * @param height Height of the drawing. 그림의 높이입니다.
     */
    public void drawSmallString(final Screen screen, final String string, final int width, final int height) {
        backBufferGraphics.setFont(fontSmall);
        backBufferGraphics.drawString(string, width, height);
    }


    /**
     * Draws a rect on regular size.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     */
    public void drawRegularRect(final Screen screen) {
        backBufferGraphics.setFont(fontRegular);
        backBufferGraphics.drawRect(screen.getWidth() / 2 - 80, screen.getHeight() - fontBigMetrics.getHeight() * 2, 160, 50);

    }

    /**
     * Draws a centered string on big font. big font로 centered string을 그립니다.
     *
     * @param screen Screen to draw on. 그릴 화면입니다.
     * @param string String to draw. 그릴 문자열입니다.
     * @param height Height of the drawing. 그림의 높이입니다.
     */
    public void drawCenteredBigString(final Screen screen, final String string, final int height) {
        backBufferGraphics.setFont(fontBig);
        backBufferGraphics.drawString(string, screen.getWidth() / 2 - fontBigMetrics.stringWidth(string) / 2, height);
    }

    /**
     * Countdown to game start. 게임 시작 카운트다운.
     *
     * @param screen    Screen to draw on. 그릴 화면입니다.
     * @param level     Game difficulty level. 게임 난이도.
     * @param number    Countdown number. 카운트다운 번호입니다.
     * @param bonusLife Checks if a bonus life is received. 보너스 목숨을 받았는지 확인합니다.
     */
    public void drawCountDown(final Screen screen, final int level, final int number, final boolean bonusLife) {
        int rectWidth = screen.getWidth();
        int rectHeight = screen.getHeight() / 6;
        backBufferGraphics.setColor(Color.BLACK);
        backBufferGraphics.fillRect(0, screen.getHeight() / 2 - rectHeight / 2, rectWidth, rectHeight);
        backBufferGraphics.setColor(Color.GREEN);
        //1 2 보스1(3) 3 4 보스2(6) 6 7 보스3(9)
        //코드 짧게 바꿈
        if (number >= 4) {
            if (!bonusLife) {
                if (level % 3 == 0) {
                    drawCenteredBigString(screen, "BONUS STAGE " + level / 3,
                            screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
                } else {
                    drawCenteredBigString(screen, "Level " + (level - level / 3),
                            screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
                }
            } else {
                if (level % 3 == 0) {
                    drawCenteredBigString(screen, "BONUS STAGE " + level / 3 + "- Bonus life!",
                            screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
                } else {
                    drawCenteredBigString(screen, "Level " + (level - level / 3) + "- Bonus life!",
                            screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
                }
            }
        } else if (number != 0) {
            drawCenteredBigString(screen, Integer.toString(number),
                    screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
        } else {
            drawCenteredBigString(screen, "GO!", screen.getHeight() / 2 + fontBigMetrics.getHeight() / 3);
        }
    }


    public void drawSaveButton(final Screen screen, int activated) {
        String saveString = "save & quit";
        if (activated == 3) backBufferGraphics.setColor(Color.RED);
        else backBufferGraphics.setColor(Color.DARK_GRAY);
        drawRegularRect(screen);
        drawCenteredRegularString(screen, saveString, screen.getHeight() - fontBigMetrics.getHeight());

    }


    public void drawMenuTitle(final Screen screen, int activated) {
        String resizeString = "resize";
        String soundString = "sound";
        String difficultyString = "difficulty";
        String[] titles = {resizeString, soundString, difficultyString};
        for (int i = 0; i < titles.length; i++) {
            if (activated == i) backBufferGraphics.setColor(Color.PINK);
            else backBufferGraphics.setColor(Color.WHITE);
            drawCenteredRegularString(screen, titles[i], screen.getHeight() / 3 + fontBigMetrics.getHeight() * i * 3);
        }
    }

    public void drawSelectMenu(final Screen screen, int resizeOption, int soundOption, int difficultyOption) {
        String minString = "min";
        String midString = "mid";
        String maxString = "max";
        String zeroString = "0%";
        String fiftyString = "50%";
        String hundreadString = "100%";
        String easyString = "easy";
        String hellString = "hell";
        String[] resizeOptions = {minString, midString, maxString};
        String[] soundOptions = {zeroString, fiftyString, hundreadString};
        String[] difficultyOptions = {easyString, midString, hellString};


        for (int i = 0; i < 3; i++) {
            if (resizeOption == i) backBufferGraphics.setColor(Color.CYAN);
            else backBufferGraphics.setColor(Color.DARK_GRAY);
            drawRegularString(screen, resizeOptions[i],
                    screen.getWidth() / 2 - fontRegularMetrics.getWidths()[0] - fontRegularMetrics.getHeight() * 5 + fontRegularMetrics.getHeight() * i * 4,
                    screen.getHeight() / 3 + fontBigMetrics.getHeight());

            if (soundOption == i) backBufferGraphics.setColor(Color.CYAN);
            else backBufferGraphics.setColor(Color.DARK_GRAY);
            drawRegularString(screen, soundOptions[i],
                    screen.getWidth() / 2 - fontRegularMetrics.getWidths()[0] - fontRegularMetrics.getHeight() * 5 + fontRegularMetrics.getHeight() * i * 4,
                    screen.getHeight() / 3 + fontBigMetrics.getHeight() * 4);

            if (difficultyOption == i) backBufferGraphics.setColor(Color.CYAN);
            else backBufferGraphics.setColor(Color.DARK_GRAY);
            drawRegularString(screen, difficultyOptions[i],
                    screen.getWidth() / 2 - fontRegularMetrics.getWidths()[0] - fontRegularMetrics.getHeight() * 5 + fontRegularMetrics.getHeight() * i * 4,
                    screen.getHeight() / 3 + fontBigMetrics.getHeight() * 7);
        }

    }

    public void drawSummary(final Screen screen) {
        String title1 = "Game Story";
        String[] gameStory = {"One peaceful day, invaders from the IC 1101 galaxy", "began to invade Earth. You must hurry", "to board the spaceship and save the Earth.", "Will you join the great battle?"};
        String title2 = "TIP";
        String[] gameTip = {"Every 3rd stage, you enter the boss stage,", "and you get a life when you clear it."};
        String title3 = "Control";
        String[] gameControl = {"UP, DOWN, RIGHT, LEFT", "Space bar : bullet firing"};
        String quitString = "Quit";

        backBufferGraphics.setColor(Color.CYAN);
        drawMiddleString(screen, title1, screen.getWidth() / 25, screen.getHeight() / 5);
        for (int i = 0; i < 4; i++) {
            backBufferGraphics.setColor(Color.WHITE);
            drawSmallString(screen, gameStory[i], screen.getWidth() / 25, screen.getHeight() / 5 + 20 * (i+1));
        }

        backBufferGraphics.setColor(Color.CYAN);
        drawMiddleString(screen, title2, screen.getWidth() / 25, screen.getHeight() / 2);
        for (int i = 0; i < 2; i++) {
            backBufferGraphics.setColor(Color.WHITE);
            drawSmallString(screen, gameTip[i], screen.getWidth() / 25, screen.getHeight() / 2 + 20 * (i+1));
        }

        backBufferGraphics.setColor(Color.CYAN);
        drawMiddleString(screen, title3, screen.getWidth() / 25, screen.getHeight() - 150);
        for (int i = 0; i < 2; i++) {
            backBufferGraphics.setColor(Color.WHITE);
            drawSmallString(screen, gameControl[i], screen.getWidth() / 25, screen.getHeight() - 150 + 20 * (i+1));
        }

        backBufferGraphics.setColor(Color.RED);
        drawRegularRect(screen);
        drawCenteredRegularString(screen, quitString, screen.getHeight() - fontBigMetrics.getHeight());
    }
}
