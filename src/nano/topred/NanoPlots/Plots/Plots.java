//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package nano.topred.NanoPlots.Plots;


import nano.topred.NanoPlots.PlotPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Iterator;

public class Plots
{
    private static ArrayList<Plot> plots;
    private static int plotsCreated;

    public Plots(ArrayList<Plot> p)
    {
        plots = p;
        plotsCreated = 0;
    }


    public static Plot getPlotFromID(int id)
    {
        for (Plot p : plots)
        {
            if (p.getId() == id)
            {
                return p;
            }
        }
        return null; //Plot doesn't exist

    }

    public static boolean removePlotFromID(int id)
    {
        for (Plot p : plots)
        {
            if (p.getId() == id)
            {
                plots.remove(p);
                return true;
            }
        }
        return false;
    }

    public static int incrPlotNumber()
    {

        return plotsCreated++;
    }

    public static int decrPlotNumber()
    {
        return plotsCreated--;
    }

    public static void add(Plot plot)
    {
        plots.add(plot);
    }

    public static boolean isInPlots(Location l)
    {
        for (Plot p : plots)
        {
            if (p.isInPlot(l))
            {
                return true;
            }
        }
        return false;
    }

    public static Plot plotContainingPlayer(Player player)
    {
        Location playerLocation = player.getLocation();
        return plotContainingLocation(playerLocation);
    }

    public static Plot plotContainingLocation(Location location)
    {
        Iterator var1 = plots.iterator();

        Plot p;
        do
        {
            if (!var1.hasNext())
            {
                return null;
            }

            p = (Plot) var1.next();
        } while (!p.isInPlot(location));

        return p;
    }

    public static ArrayList<Plot> getPlots()
    {
        return plots;
    }

    public static void setPlots(ArrayList<Plot> plots)
    {
        Plots.plots = plots;
    }

    public Plot plotContainingPlayer(PlotPlayer player)
    {
        Location playerLocation = player.getPlayer().getLocation();
        return plotContainingLocation(playerLocation);
    }
}
