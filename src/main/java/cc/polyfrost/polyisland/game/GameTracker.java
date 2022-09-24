package cc.polyfrost.polyisland.game;

import cc.polyfrost.polyisland.utils.ServerUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

import java.util.Objects;

public class GameTracker {
    public static final GameTracker INSTANCE = new GameTracker();
    private static final String TIME_IDENTIFIER = ":";
    private static final String MCCI_PREFIX = "MCCI: ";

    private final MinecraftClient client = MinecraftClient.getInstance();

    private GameState state = GameState.NONE;
    private LightState lightState = LightState.NONE;
    private Game currentGame;
    private int time;

    public Game getGame() {
        return this.currentGame;
    }

    public LightState getLightState() {
        return this.lightState;
    }

    public boolean isInGame() {
        return this.currentGame != null;
    }

    public void onWorldTick(ClientWorld world) {
        /* Game */

        final Game oldGame = this.currentGame;
        this.currentGame = null;

        final Scoreboard scoreboard = Objects.requireNonNull(client.player).getScoreboard();
        if (scoreboard != null) {
            final ScoreboardObjective sidebar = scoreboard.getObjectiveForSlot(1);
            if (sidebar != null) {
                final String sidebarName = sidebar.getDisplayName().getString();
                if (sidebarName.contains(MCCI_PREFIX)) {
                    final String rawId = sidebarName.substring(MCCI_PREFIX.length());
                    Game.fromScoreboard(rawId).ifPresent(game -> {
                        if (game != oldGame) {
                            this.onGameChange(game, oldGame);
                        }

                        this.currentGame = game;
                    });
                }
            }
        }

        /* Time */

        if (this.currentGame != null) {
            final int lastTime = this.time;
            this.time = -1;

            for (var bossBar : ServerUtils.getServerBossBars()) {
                final String name = bossBar.getName().getString();
                final BossBar.Color color = bossBar.getColor();
                if (name.contains("Green Light") && color == BossBar.Color.GREEN && state == GameState.ACTIVE) {
                    lightState = LightState.GREEN;
                } else if (name.contains("Red Light") && color == BossBar.Color.RED && state == GameState.ACTIVE) {
                    lightState = LightState.RED;
                } else {
                    lightState = LightState.NONE;
                }
                if (name.contains(TIME_IDENTIFIER)) {
                    final int index = name.indexOf(TIME_IDENTIFIER);
                    final String rawMins = name.substring(index - 2, index);
                    final String rawSecs = name.substring(index + 1, index + 3);
                    final int mins = Integer.parseInt(rawMins);
                    final int secs = Integer.parseInt(rawSecs);
                    final int time = (mins * 60) + secs;

                    if (time != lastTime) {
                        this.onTimeChange(time, lastTime);
                    }

                    this.time = time;
                }
            }
        } else {
            this.time = -1;
        }

        /* Ticking */

        if (this.currentGame == null) {
            this.state = GameState.NONE;
        } else {
            if (this.state == GameState.NONE) {
                this.state = GameState.WAITING_FOR_GAME;
            }

            if (this.client.currentScreen instanceof DeathScreen && this.state.ordinal() < GameState.POST_ROUND_SELF.ordinal()) {
                this.endGame(GameState.POST_ROUND_SELF);
            }
        }
    }

    public void onChatMessage(String message) {
        if (message.endsWith(" over!")) {
            this.endGame(message.contains("Round") ? GameState.POST_ROUND : GameState.POST_GAME);
        } else if (message.contains("you finished the round and came")) {
            this.endGame(GameState.POST_ROUND_SELF);
        } else if (message.contains("you didn't finish the round!")) {
            this.endGame(GameState.POST_ROUND_SELF);
        } else if (message.contains("you won Round")) {
            this.endGame(GameState.POST_ROUND_SELF);
        } else if (message.contains("you lost Round")) {
            this.endGame(GameState.POST_ROUND_SELF);
        }
    }

    public void onGameChange(Game game, Game oldGame) {
        if (game != oldGame && game != null && oldGame != null) {
            this.state = GameState.WAITING_FOR_GAME;
        }
    }

    public void onTimeChange(int time, int lastTime) {
        if (this.state == GameState.POST_ROUND) {
            if (time == 15) {
                this.state = GameState.WAITING_FOR_GAME;
            }
        }

        // state decay change
        if (lastTime == 1) {
            if (this.state == GameState.WAITING_FOR_GAME) {
                this.state = GameState.ACTIVE;
            } else if (this.state == GameState.POST_ROUND) {
                this.state = GameState.WAITING_FOR_GAME;
            } else if (this.state == GameState.POST_GAME) {
                this.state = GameState.NONE;
            }
        }
    }


    protected void endGame(GameState state) {
        this.state = state;
    }
}