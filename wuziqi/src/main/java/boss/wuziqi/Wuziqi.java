package boss.wuziqi;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Wuziqi extends JavaPlugin implements CommandExecutor {
    private static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.GREEN + "Wuziqi" + ChatColor.GRAY + "] ";

    private Map<Player, Player> players = new HashMap<>();
    private List<Player> waitingList = new ArrayList<>();
    private Chessboard chessboard;

    @Override
    public void onEnable() {
        getCommand("wuziqi").setExecutor(this);
        getLogger().info("Wuziqi plugin has been enabled.");
        chessboard = new Chessboard(); // 创建棋盘实例
    }

    @Override
    public void onDisable() {
        getLogger().info("Wuziqi plugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "This command can only be executed by players.");
            return true;
        }

        Player player = (Player) sender;

        if (label.equalsIgnoreCase("wuziqi")) {
            if (args.length > 0 && args[0].equalsIgnoreCase("join")) {
                if (players.containsKey(player)) {
                    player.sendMessage(PREFIX + ChatColor.RED + "You are already in a game.");
                    return true;
                }

                if (waitingList.contains(player)) {
                    player.sendMessage(PREFIX + ChatColor.RED + "You are already in the waiting list.");
                    return true;
                }

                waitingList.add(player);
                player.sendMessage(PREFIX + ChatColor.GREEN + "You have joined the game waiting list.");
                checkStartGame();

                return true;
            } else if (args.length > 0 && args[0].equalsIgnoreCase("color")) {
                if (players.containsKey(player)) {
                    player.sendMessage(PREFIX + ChatColor.RED + "You are already in a game.");
                    return true;
                }

                if (waitingList.contains(player)) {
                    player.sendMessage(PREFIX + ChatColor.RED + "You are already in the waiting list.");
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage(PREFIX + ChatColor.RED + "Usage: /wuziqi color <black|white>");
                    return true;
                }

                String color = args[1].toLowerCase();
                if (!color.equals("black") && !color.equals("white")) {
                    player.sendMessage(PREFIX + ChatColor.RED + "Invalid color. Available options: black, white.");
                    return true;
                }

                waitingList.add(player);
                player.sendMessage(PREFIX + ChatColor.GREEN + "You have joined the game waiting list as " + color + " player.");
                checkStartGame();

                return true;
            }
        }

        return false;
    }

    private void checkStartGame() {
        if (waitingList.size() >= 2) {
            Player player1 = waitingList.get(0);
            Player player2 = waitingList.get(1);

            startGame(player1, player2);
            waitingList.remove(player1);
            waitingList.remove(player2);
        }
    }

    private void startGame(Player player1, Player player2) {
        players.put(player1, player2);
        players.put(player2, player1);

        player1.sendMessage(PREFIX + ChatColor.GREEN + "The game has started. You are playing against " + player2.getName() + ".");
        player2.sendMessage(PREFIX + ChatColor.GREEN + "The game has started. You are playing against " + player1.getName() + ".");

        // 将玩家传送至指定的棋盘
        Location boardLocation = new Location(player1.getWorld(), 100, 100, 100); // 指定棋盘的坐标
        player1.teleport(boardLocation);
        player2.teleport(boardLocation);

        chessboard.reset(); // 重置棋盘状态

        // 在棋盘周围显示边框等棋盘信息
        chessboard.drawBoardBorder(player1);
        chessboard.drawBoardBorder(player2);

        // TODO: 添加其他棋盘显示逻辑，例如绘制棋盘格子、绘制已下棋子等

        // 游戏结束后的处理
        // TODO: 添加游戏结束逻辑，判断胜负、显示胜利信息等
    }
}
