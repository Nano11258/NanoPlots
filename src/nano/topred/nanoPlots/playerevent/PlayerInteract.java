package nano.topred.nanoPlots.playerevent;

import nano.topred.nanoPlots.plots.Plot;
import nano.topred.nanoPlots.plots.Plots;
import nano.topred.nanoPlots.PlotsData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (event.getClickedBlock()==null)
        {
            return;
        }
        Plot plot = Plots.plotContainingLocation(event.getClickedBlock().getLocation());
        if (plot ==null){
            return;
        }
        if (! plot.canBuild(plot, PlotsData.getPlotPlayer(player)))
        {
            player.sendMessage("You cannot build here");
            event.setCancelled(true);
        }

    }
}