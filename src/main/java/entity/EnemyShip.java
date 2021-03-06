package entity;

import java.awt.Color;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a enemy ship, to be destroyed by the player. 플레이어가 파괴할 적 함선을
 * 구현합니다.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class EnemyShip extends Entity {

	/** Point value of a type A enemy. */
	private static final int A_TYPE_POINTS = 10;
	/** Point value of a type B enemy. */
	private static final int B_TYPE_POINTS = 20;
	/** Point value of a type C enemy. */
	private static final int C_TYPE_POINTS = 30;
	/** Point value of a type Boss1. */
	//보스 포인트
	private static final int BossA_POINTS = 50;
	private static final int BossB_POINTS = 100;
	private static final int BossC_POINTS = 200;
	/** Point value of a bonus enemy. */
	private static final int BONUS_TYPE_POINTS = 100;

	/**
	 * Cooldown between sprite changes. 스프라이트 변경 사이의 쿨다운.
	 */
	private Cooldown animationCooldown;
	/**
	 * Checks if the ship has been hit by a bullet. 함선이 총알에 맞았는지 확인합니다.
	 */
	private boolean isDestroyed;
	/**
	 * Values of the ship, in points, when destroyed. 파괴된 선박의 가치(포인트)입니다.
	 */
	private int pointValue;
	/**
	 * resize시 EnemyShip의 크기도 커지는데 타격범위도 키우기 위해 사용되는 변수들입니다.
	 */
	private static int modiWidth = 2;

	/**
	 * 일반 몬스터 타입별 HP 설정
	 */
	private int HP = 1 ;

	/**
	 * modiWidth 변수의 setter함수입니다.
	 */
	public static void setModiWidth(int wid) {
		modiWidth = wid;
	}

	/**
	 * Constructor, establishes the ship's properties. 생성자, 함선의 속성을 설정합니다.
	 *
	 * @param positionX  Initial position of the ship in the X axis. X축에서 함선의 초기
	 *                   위치입니다.
	 * @param positionY  Initial position of the ship in the Y axis. Y축에서 함선의 초기
	 *                   위치입니다.
	 * @param spriteType Sprite type, image corresponding to the ship. 스프라이트 타입, 함선에
	 *                   대응하는 이미지.
	 * @param selectedColor 적 함선의 종류에 따라 색깔을 다르게 하기 위해 받는 색상 값입니다.
	 */

	public EnemyShip(final int positionX, final int positionY, final SpriteType spriteType, final Color selectedColor) {
		super(positionX, positionY, 12 * modiWidth, 8 * modiWidth, selectedColor);
		//보스 경우 너비랑 높이도 스프라이트 크기에 맞게 바꿔 줍니다.
		switch(spriteType) {
			case BossA1:
			case BossA2:
				width = 12 * 10 * modiWidth;
				height = 8 * 10 * modiWidth;
			case BossB1:
			case BossB2:
				width = 12 * 10 * modiWidth;
				height = 8 * 10 * modiWidth;
			case BossC1:
			case BossC2:
				width = 12 * 10 * modiWidth;
				height = 8 * 10 * modiWidth;
		}

		this.spriteType = spriteType;
		this.animationCooldown = Core.getCooldown(500);
		this.isDestroyed = false;

		//일반 몬스터 타입별 HP추가
		switch (this.spriteType) {

			case EnemyShipA1:
			case EnemyShipA2:
				this.pointValue = A_TYPE_POINTS;
				this.HP = 3 ;
				break;
			case EnemyShipB1:
			case EnemyShipB2:
				this.pointValue = B_TYPE_POINTS;
				this.HP = 2 ;
				break;
			case EnemyShipC1:
			case EnemyShipC2:
				this.pointValue = C_TYPE_POINTS;
				this.HP = 1 ;
				break;
			//보스 포인트 설정.
			case BossA1:
			case BossA2:
				this.pointValue = BossA_POINTS;
				break;
			case BossB1:
			case BossB2:
				this.pointValue = BossB_POINTS;
				break;
			case BossC1:
			case BossC2:
				this.pointValue = BossC_POINTS;
				break;
			default:
				this.pointValue = 0;
				break;
		}
	}

	/**
	 * Constructor, establishes the ship's properties for a special ship, with known
	 * starting properties. 생성자, 알려진 시작 속성을 사용하여 특수 함선에 대한 함선 속성을 설정합니다.
	 */
	public EnemyShip() {
		super(-32, 60, 16 * modiWidth, 7 * modiWidth, Color.RED);

		this.spriteType = SpriteType.EnemyShipSpecial;
		this.isDestroyed = false;
		this.pointValue = BONUS_TYPE_POINTS;
	}

	/**
	 * Getter for the score bonus if this ship is destroyed. 이 함선이 파괴되면 점수 보너스를 얻을 수
	 * 있습니다.
	 *
	 * @return Value of the ship.
	 */
	public final int getPointValue() {
		return this.pointValue;
	}

	/**
	 * Moves the ship the specified distance. 함선을 지정된 거리만큼 이동합니다.
	 *
	 * @param distanceX Distance to move in the X axis. X축에서 이동할 거리입니다.
	 * @param distanceY Distance to move in the Y axis. Y축에서 이동할 거리입니다.
	 */
	public final void move(final int distanceX, final int distanceY) {
		this.positionX += distanceX;
		this.positionY += distanceY;
	}

	/**
	 * Updates attributes, mainly used for animation purposes. 주로 애니메이션 목적으로 사용되는
	 * 속성을 업데이트합니다.
	 */
	public final void update() {
		if (this.animationCooldown.checkFinished()) {
			this.animationCooldown.reset();

			switch (this.spriteType) {
			case EnemyShipA1:
				this.spriteType = SpriteType.EnemyShipA2;
				break;
			case EnemyShipA2:
				this.spriteType = SpriteType.EnemyShipA1;
				break;
			case EnemyShipB1:
				this.spriteType = SpriteType.EnemyShipB2;
				break;
			case EnemyShipB2:
				this.spriteType = SpriteType.EnemyShipB1;
				break;
			case EnemyShipC1:
				this.spriteType = SpriteType.EnemyShipC2;
				break;
			case EnemyShipC2:
				this.spriteType = SpriteType.EnemyShipC1;
				break;
			//boss 움직임
			case BossA1:
				this.spriteType = SpriteType.BossA2;
				break;
			case BossA2:
				this.spriteType = SpriteType.BossA1;
				break;
			case BossB1:
				this.spriteType = SpriteType.BossB2;
				break;
			case BossB2:
				this.spriteType = SpriteType.BossB1;
				break;
			case BossC1:
				this.spriteType = SpriteType.BossC2;
				break;
			case BossC2:
				this.spriteType = SpriteType.BossC1;
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Destroys the ship, causing an explosion. 함선을 파괴하여 폭발을 일으킵니다.
	 */
	public final void destroy() {
		this.isDestroyed = true;
		if(this.width > 16*modiWidth){
			this.spriteType = SpriteType.BossExplosion;
		}else{
			switch (this.spriteType){
				case EnemyShipA1:
					this.spriteType = SpriteType.DestroyedEnemyShipA1;
					break;
				case EnemyShipA2:
					this.spriteType = SpriteType.DestroyedEnemyShipA2;
					break;
				case EnemyShipB1:
					this.spriteType = SpriteType.DestroyedEnemyShipB1;
					break;
				case EnemyShipB2:
					this.spriteType = SpriteType.DestroyedEnemyShipB2;
					break;
				case EnemyShipC1:
					this.spriteType = SpriteType.DestroyedEnemyShipC1;
					break;
				case EnemyShipC2:
					this.spriteType = SpriteType.DestroyedEnemyShipC2;
					break;
				case EnemyShipSpecial:
					this.spriteType = SpriteType.DestroyedEnemyShipSpecial;
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Checks if the ship has been destroyed. 함선이 파괴되었는지 확인합니다.
	 *
	 * @return True if the ship has been destroyed.
	 */
	public final boolean isDestroyed() {
		return this.isDestroyed;
	}

	/**
	 *
	 * @return 타입별 HP를 반환 합니다.
	 */
	public int getHP(){
		return this.HP ;
	}

	/**
	 *
	 * @param hp 남은 HP에 대한 HP setter 함수 입니다.
	 */
	public void setHP(int hp){
		this.HP = hp ;
	}
}
