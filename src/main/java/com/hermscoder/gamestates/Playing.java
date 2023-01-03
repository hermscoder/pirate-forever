package com.hermscoder.gamestates;

import com.hermscoder.entities.EnemyManager;
import com.hermscoder.entities.Player;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.levels.LevelRender;
import com.hermscoder.main.Game;
import com.hermscoder.objects.ObjectManager;
import com.hermscoder.ui.GameOverOverlay;
import com.hermscoder.ui.LevelCompletedOverlay;
import com.hermscoder.ui.PauseOverlay;
import com.hermscoder.utils.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import static com.hermscoder.main.Game.SCALE;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;

    private LevelRender levelRender;
    private boolean paused;
    private boolean gameOver;
    private boolean levelCompleted;
    private boolean playerDying;

    public Playing(Game game) {
        super(game);
        initClasses();
        loadStartLevel();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this, levelManager);
        objectManager = new ObjectManager(this);
        player = new Player(231, 200,
                (Sprite.PlayerSpriteAtlas.getTileWidth(SCALE)),
                (Sprite.PlayerSpriteAtlas.getTileHeight(SCALE)),
                this);
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);

        levelRender = new LevelRender(this, levelManager);
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if (paused) {
            pauseOverlay.update();
        } else if (levelCompleted) {
            levelCompletedOverlay.update();
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelRender.update();
            objectManager.update(levelManager.getCurrentLevel().getLvlData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(), player);
        }
    }


    @Override
    public void draw(Graphics g) {
        levelRender.draw(g);
        player.render(g, levelRender.getxLevelOffset());
        enemyManager.draw(g, levelRender.getxLevelOffset());
        objectManager.draw(g, levelRender.getxLevelOffset());

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (levelCompleted) {
            levelCompletedOverlay.draw(g);
        }
    }

    public void resetAll() {
        paused = false;
        gameOver = false;
        levelCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitBox) {
        objectManager.checkObjectTouched(hitBox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }

    public void checkWeaponTouched(Player player) {
        objectManager.checkWeaponTouched(player);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            } else if (e.getButton() == MouseEvent.BUTTON3)
                player.powerAttack();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mousePressed(e);
            else if (levelCompleted)
                levelCompletedOverlay.mousePressed(e);
        } else {
            gameOverOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseReleased(e);
            else if (levelCompleted)
                levelCompletedOverlay.mouseReleased(e);
        } else {
            gameOverOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused)
                pauseOverlay.mouseMoved(e);
            else if (levelCompleted)
                levelCompletedOverlay.mouseMoved(e);
        } else {
            gameOverOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                player.setRight(false);

                if(player.checkIfDoublePressed(e.getKeyCode(), 200)) {
                    player.dash();
                }
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                player.setLeft(false);
                if(player.checkIfDoublePressed(e.getKeyCode(), 200)) {
                    player.dash();
                }
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
            case KeyEvent.VK_L:
                player.setAttacking(true);
                break;
            case KeyEvent.VK_P:
                player.powerAttack();
                break;
        }
        player.changeLastKeyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
            player.changeLastKeyReleasedAndResetTimer(e.getKeyCode());
        }
    }

    public void unpauseGame() {
        paused = false;
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver)
            if (paused)
                pauseOverlay.mouseDragged(e);
    }

    public boolean isPaused() {
        return paused;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public LevelRender getLevelRender() {
        return levelRender;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
        if (levelCompleted)
            game.getAudioPlayer().levelCompleted();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean dying) {
        this.playerDying = dying;
    }
}
