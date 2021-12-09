package screen;

import engine.Cooldown;
import engine.Core;

import java.awt.event.KeyEvent;

public class SettingScreen extends Screen {
    /**
     * Milliseconds between changes in user selection. 사용자 선택 변경 사이의 밀리초입니다.
     */
    private static final int SELECTION_TIME = 200;
    private static int resizeOption = 0;
    private static int soundOption = 2;
    private static int difficultyOption = 0;

    /**
     * 현재 활성화된 선택 요소가 무엇인지를 판단하는 것입니다.
     * 0 : resize
     * 1 : sound
     * 2 : difficulty
     * 3 : save and exit
     */
    private static int activated;

    /**
     * Time between changes in user selection. 사용자 선택 변경 사이의 시간입니다.
     */
    private Cooldown selectionCooldown;

    public SettingScreen(final int width, final int height, final int fps) {
        super(width, height, fps);
        activated = 0;

        // Defaults to resize screen

        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }



    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events. 화면의 요소를 업데이트하고 이벤트를
     * 확인합니다.
     */
    protected final void update() {
        super.update();
        draw();
        if (this.selectionCooldown.checkFinished() && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP) || inputManager.isKeyDown(KeyEvent.VK_W)) {
                previousMenuItem();
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN) || inputManager.isKeyDown(KeyEvent.VK_S)) {
                nextMenuItem();
                this.selectionCooldown.reset();
            }

            if(inputManager.isKeyDown(KeyEvent.VK_LEFT) || inputManager.isKeyDown(KeyEvent.VK_A)){
                previousOptionItem();
                this.selectionCooldown.reset();
            }
            if(inputManager.isKeyDown(KeyEvent.VK_RIGHT) || inputManager.isKeyDown(KeyEvent.VK_D)){
                nextOptionItem();
                this.selectionCooldown.reset();
            }

            if (activated == 3 &&inputManager.isKeyDown(KeyEvent.VK_SPACE) ){
                this.returnCode = 1;
                Core.setScreenSizeMode(resizeOption);
                Core.setDifficulty(difficultyOption);
                Core.setMusicVolume(soundOption);
                this.isRunning = false;
            }

        }
    }

    /**
     * Shifts the focus to the next menu item. 포커스를 다음 메뉴 항목으로 이동합니다.
     */
    private void nextOptionItem() {
        switch (activated) {
            case 0:
                if (resizeOption == 2)
                    resizeOption = 0;
                else
                    resizeOption++;
                break;
            case 1:
                if (soundOption == 2)
                    soundOption = 0;
                else
                    soundOption++;
                break;
            case 2:
                if (difficultyOption == 2)
                    difficultyOption = 0;
                else
                    difficultyOption++;
                break;
            default:
                break;
        }

    }

    /**
     * Shifts the focus to the previous menu item. 포커스를 이전 메뉴 항목으로 이동합니다.
     */
    private void previousOptionItem() {
        switch (activated) {
            case 0:
                if (resizeOption == 0)
                    resizeOption = 2;
                else
                    resizeOption--;
                break;
            case 1:
                if (soundOption == 0)
                    soundOption = 2;
                else
                    soundOption--;
                break;
            case 2:
                if (difficultyOption == 0)
                    difficultyOption = 2;
                else
                    difficultyOption--;
                break;
            default:
                break;
        }
    }


    /**
     * Shifts the focus to the next menu item. 포커스를 다음 메뉴 항목으로 이동합니다.
     */
    private void nextMenuItem() {
        if (activated == 3)
            activated = 0;
        else
            activated++;
    }

    /**
     * Shifts the focus to the previous menu item. 포커스를 이전 메뉴 항목으로 이동합니다.
     */
    private void previousMenuItem() {
        if (activated == 0)
            activated = 3;
        else
            activated--;
    }


    /**
     * Draws the elements associated with the screen. 화면과 관련된 요소를 그립니다.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawTitle(this,"Setting",4);
        drawManager.drawMenuTitle(this,activated);
        drawManager.drawSelectMenu(this,resizeOption,soundOption,difficultyOption);
        drawManager.drawSaveButton(this,activated);
        drawManager.completeDrawing(this);
    }
}
