package nano.topred.nanoPlots.commands;

import nano.topred.nanoPlots.plots.Plot;
import nano.topred.nanoPlots.plots.Plots;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlotInfo implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("You must be a player");
            return false;
        }
        Player player = (Player) sender;
        Plot plot = Plots.plotContainingPlayer(player);
        if (plot != null)
        {
            plot.plotPrintInfo(player);

        } else
        {
            sender.sendMessage("You are not in a plot");
        }

        return true;
    }
}