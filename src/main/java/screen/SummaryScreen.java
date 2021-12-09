package screen;

import engine.Cooldown;
import engine.Core;

import java.awt.event.KeyEvent;

public class SummaryScreen extends Screen {
    /**
     * Milliseconds between changes in user selection.
     * 사용자 선택 변경 사이의 밀리초입니다.
     */
    private static final int SELECTION_TIME = 200;

    /**
     * Time between changes in user selection.
     * 사용자 선택 변경 사이의 시간입니다.
     */
    private Cooldown selectionCooldown;

    public SummaryScreen(final int width, final int height, final int fps) {
        super(width, height, fps);

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
     * Updates the elements on screen and checks for events.
     * 화면의 요소를 업데이트하고 이벤트를 확인합니다.
     */
    protected final void update() {
        super.update();
        draw();
        if (this.selectionCooldown.checkFinished() && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE) ){
                this.returnCode = 1;
                this.isRunning = false;
            }
        }
    }


    /**
     * Draws the elements associated with the screen.
     * 화면과 관련된 요소를 그립니다.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawTitle(this,"Game Summary",10);
        drawManager.drawSummary(this);


        drawManager.completeDrawing(this);
    }
}
