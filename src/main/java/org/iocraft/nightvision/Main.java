package org.iocraft.nightvision;
import org.iocraft.nightvision.ColourUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class Main extends JavaPlugin {
    private final long DAY_IN_MILLIS = TimeUnit.DAYS.toMillis(1);
	String Enabled = this.getConfig().getString("message-enabled");
	String Disabled = this.getConfig().getString("message-disabled");    
    String Prefix = this.getConfig().getString("message-prefix");
    public static String format(String format){
		return ChatColor.translateAlternateColorCodes('&', format);
    }	
	@Override
    public void onEnable() {
        getLogger().info("IO-NightVision has been enabled!");
        saveDefaultConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("nv") && p.hasPermission("io.nightvision.use")) {
            if (args.length == 0) {
				if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
					p.removePotionEffect(PotionEffectType.NIGHT_VISION);
					p.sendMessage(format(String.valueOf(this.Prefix) + String.valueOf(this.Disabled)));
				}
				else {
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
					p.sendMessage(format(String.valueOf(this.Prefix) + String.valueOf(this.Enabled)));
				}
				return true;
			} else {
				return false;
			}
        } else if (cmd.getName().equalsIgnoreCase("nv")) {
            if (args.length != 1) {
                return false;
            } else if (args[0].equalsIgnoreCase("reload") && p.hasPermission("io.nightvision.reload")) {
                this.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
                return true;
            } else if (args[0].equalsIgnoreCase("help") && p.hasPermission("io.nightvision.help")) {
                sender.sendMessage("HEADER");
                sender.sendMessage("/nv help: " + ChatColor.GOLD + "Shows commands in the plugin.");
                sender.sendMessage("/nv: " + ChatColor.GOLD + "Toggle night vision on/off.");
                sender.sendMessage("/nv reload: " + ChatColor.GOLD + "Reload the configuration.");
                sender.sendMessage("FOOTER");
            }
            return true;
        } else {
            p.sendMessage("You do not have permission to use that command.");
        }
        return false;
    }
    @Override
    public void onDisable() {
        getLogger().info("IO-NightVision has been disabled!");
    }
}