package boss.mochu666;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class BossCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (label.equalsIgnoreCase("boss")) {
                // 移除所有NPC
                removeExistingNPCs();

                // 在玩家附近生成NPC
                generateBossNPC(player);

                return true;
            }
        }

        return false;
    }

    private void removeExistingNPCs() {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
        npcRegistry.deregisterAll();
    }

    private void generateBossNPC(Player player) {
        NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();

        // 在玩家附近找到钻石块
        for (int x = player.getLocation().getBlockX() - 50; x <= player.getLocation().getBlockX() + 50; x++) {
            for (int y = player.getLocation().getBlockY() - 50; y <= player.getLocation().getBlockY() + 50; y++) {
                for (int z = player.getLocation().getBlockZ() - 50; z <= player.getLocation().getBlockZ() + 50; z++) {
                    if (player.getWorld().getBlockAt(x, y, z).getType() == Material.DIAMOND_BLOCK) {
                        // 在钻石块上生成NPC
                        NPC npc = npcRegistry.createNPC(EntityType.PLAYER, "NUOWEIZS");
                        npc.spawn(player.getLocation().add(0, 1, 0));

                        // 设置NPC的名字和模型
                        npc.setName(ChatColor.RED + "" + ChatColor.BOLD + "挪威战神");
                        npc.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_METADATA, "35ba19bc-eb85-4fa0-aebd-314808684b54");
                        npc.data().set(NPC.PLAYER_SKIN_TEXTURE_PROPERTIES_SIGN_METADATA, "35ba19bceb854fa0aebd314808684b54");

                        // 设置NPC可以被攻击
                        npc.setProtected(false);

                        return;
                    }
                }
            }
        }
    }
}
