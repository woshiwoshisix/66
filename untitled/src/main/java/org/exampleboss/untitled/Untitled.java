package org.exampleboss.untitled;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BossCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final NPCRegistry npcRegistry;

    public BossCommand(JavaPlugin plugin, NPCRegistry npcRegistry) {
        this.plugin = plugin;
        this.npcRegistry = npcRegistry;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("boss")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "该命令只能由玩家执行！");
                return true;
            }

            Player player = (Player) sender;
            int radius = 100;

            // 移除所有NPC
            for (NPC npc : npcRegistry) {
                npc.destroy();
            }

            // 在玩家周围查找钻石块
            for (int x = player.getLocation().getBlockX() - radius; x <= player.getLocation().getBlockX() + radius; x++) {
                for (int y = player.getLocation().getBlockY() - radius; y <= player.getLocation().getBlockY() + radius; y++) {
                    for (int z = player.getLocation().getBlockZ() - radius; z <= player.getLocation().getBlockZ() + radius; z++) {
                        if (player.getWorld().getBlockAt(x, y, z).getType() == Material.DIAMOND_BLOCK) {
                            // 在钻石块上生成NPC
                            NPC npc = npcRegistry.createNPC(EntityType.PLAYER, "挪威战神");
                            npc.spawn(player.getLocation().add(0, 1, 0));
                            npc.setName(ChatColor.RED + "" + ChatColor.BOLD + "挪威战神");
                            npc.setProtected(true);

                            player.sendMessage(ChatColor.GREEN + "已生成Boss NPC！");

                            // 在玩家头顶生成黑曜石平台
                            Location platformLocation = player.getLocation().add(0, 3, 0);
                            generateObsidianPlatform(platformLocation);

                            // 召唤烈焰弹并扩散爆炸
                            summonFlameBalls(platformLocation);

                            // 延迟销毁黑曜石平台
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    destroyObsidianPlatform(platformLocation);
                                }
                            }.runTaskLater(plugin, 20 * 10); // 延迟10秒后销毁黑曜石平台

                            return true;
                        }
                    }
                }
            }

            player.sendMessage(ChatColor.RED + "附近没有钻石块！");
            return true;
        }

        return false;
    }

    // 在指定位置生成3*3的黑曜石平台
    private void generateObsidianPlatform(Location location) {
        World world = location.getWorld();
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        // 生成3*3的黑曜石平台
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                Block block = world.getBlockAt(centerX + xOffset, centerY, centerZ + zOffset);
                block.setType(Material.OBSIDIAN);
            }
        }
    }

    // 销毁指定位置的3*3黑曜石平台
    private void destroyObsidianPlatform(Location location) {
        World world = location.getWorld();
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        // 销毁3*3的黑曜石平台
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                Block block = world.getBlockAt(centerX + xOffset, centerY, centerZ + zOffset);
                block.setType(Material.AIR);
            }
        }
    }

    // 在玩家周围范围内召唤100个烈焰弹并扩散爆炸
    private void summonFlameBalls(Location location) {
        World world = location.getWorld();
        int centerX = location.getBlockX();
        int centerY = location.getBlockY();
        int centerZ = location.getBlockZ();

        Random random = new Random();
        List<Location> locations = new ArrayList<>();

        // 在玩家周围范围内随机选择100个位置
        for (int i = 0; i < 100; i++) {
            int xOffset = random.nextInt(21) - 10; // -10 到 10 的随机偏移量
            int zOffset = random.nextInt(21) - 10; // -10 到 10 的随机偏移量
            Location targetLocation = new Location(world, centerX + xOffset + 0.5, centerY + 5, centerZ + zOffset + 0.5);
            locations.add(targetLocation);
        }

        // 在每个位置召唤烈焰弹并扩散爆炸
        for (Location targetLocation : locations) {
            Fireball fireball = world.spawn(targetLocation, Fireball.class);
            fireball.setDirection(location.toVector().subtract(targetLocation.toVector()).normalize());

            // 设置烈焰弹的威力和是否摧毁方块
            fireball.setYield(2.0F);
            fireball.setIsIncendiary(false);

            // 5秒后烈焰弹爆炸
            new BukkitRunnable() {
                @Override
                public void run() {
                    fireball.detonate();
                }
            }.runTaskLater(plugin, 20 * 5); // 5秒后爆炸
        }
    }
}
