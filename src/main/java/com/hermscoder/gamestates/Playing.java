package com.hermscoder.gamestates;

import com.hermscoder.entities.EnemyManager;
import com.hermscoder.levels.LevelRender;
import com.hermscoder.entities.Player;
import com.hermscoder.levels.LevelManager;
import com.hermscoder.main.Game;
import com.hermscoder.ui.PauseOverlay;
import com.hermscoder.utils.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static com.hermscoder.main.Game.SCALE;

public class Playing extends State implements StateMethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private LevelRender levelRender;
    private boolean paused = false;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this, levelManager);
        player = new Player(200, 200,
                (Sprite.PlayerSpriteAtlas.getTileWidth(SCALE)),
                (Sprite.PlayerSpriteAtlas.getTileHeight(SCALE)));
        player.loadLvlData(levelManager.getCurrentLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);

        levelRender = new LevelRender(this, levelManager);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if(!paused) {
            levelRender.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData());
        } else {
            pauseOverlay.update();
        }
    }


    @Override
    public void draw(Graphics g) {
        levelRender.draw(g);
        player.render(g, levelRender.getxLevelOffset());
        enemyManager.draw(g, levelRender.getxLevelOffset());
        if(paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(paused)
            pauseOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
    }

    public void unpauseGame() {
        paused = false;
    }

    public void mouseDragged(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseDragged(e);
    }

    public boolean isPaused() {
        return paused;
    }
}
