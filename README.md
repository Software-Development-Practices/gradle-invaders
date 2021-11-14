# Space Invaders

## Convention

### Commit Convention

- task 하나 : 하나의 브랜치 = 1 : 1 로 작업을 한다.
- task 의 하위 이슈(하위 작업)가 생길 경우 브랜치는 생성하지 않는다. (미정)
- 커밋 메세지 첫 줄 컨벤션은 다음과 같다.
  - [헤더] [SETB-{issue number}] 커밋 메세지
  - 헤더 종류는 다음과 같다.
    - add: 기능 구현
    - fix: 코드/버그 수정
    - hofix: hotfix 브랜치에서 생성되는 모든 커밋 헤더
    - docs: 문서 작업
    - PR: PR 생성할 때 붙여주는 헤더

### Branch Convention

- 브랜치 네이밍 컨벤션
  - SETB-{issue number}/{branch name}-{work feature}

## Todo

### general -- new

1. Add dying effect
   - virbration effect, etc
2. Add SFX & BGM
   - dynamic sound effect, etc
3. Display HP Color
   - identifying enemy's hp state, bullet count, etc
4. Add Pause function
   - pausing game by pressing ESC key, etc
5. Add Setting function
   - screen resizing, game speed selecting, etc
6. Add various monsters
   - diversifying monster patterns, boss monsters, etc
7. Add Game Summary
   - game rule, manual, etc

### old

1.  **Add player dying effect**

        Add a vibration effect when a player dies
        to add a sense of excitement.

2.  **Add Sound Effects & Background Music**

        Add sound effects and background music.
        Sound effects are dynamic so that volume depends on
        distance between player and sound effect point.

3.  **Display Enemy HP Color**

        color identifies how many bullets an enemy has to be hit to die
        and how many bullets are out of the enemy.

4.  **Pause fuction**
    (일시정지 기능)

        If you press the ESC key while playing the game,
        you can pause. If you want, you can exit the game.

5.  **Setting function.**
    (설정 기능)

        It enables the overall description of the game,
        selecting screen size, and game speed.

6.  **Monster diversity**
    (몬스터 다양성)

        Diversifying simple existing monster patterns gives tension to
        the game, It maintains the user's interest by providing the goal
        of a game called Boss Monster in the middle.

7.  **Game Summary**
    (게임설명)

        Explain manual, game rule before game start.
