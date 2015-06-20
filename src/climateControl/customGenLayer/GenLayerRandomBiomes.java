package climateControl.customGenLayer;

import climateControl.BiomeRandomizer;

import climateControl.utils.Zeno410Logger;
import java.util.logging.Logger;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerHills;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiver;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraft.world.gen.layer.GenLayerShore;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
/**
 *
 * @author MasterCaver modified by Zeno410
 */
public class GenLayerRandomBiomes extends GenLayer {


    public static Logger logger = new Zeno410Logger("RandomBiomes").logger();

    private BiomeRandomizer biomeRandomizer;

    public GenLayerRandomBiomes(long par1, GenLayer par3GenLayer){
        super(par1);
        this.parent = par3GenLayer;

        /*this.randomBiomeList = new BiomeGenBase[] {BiomeGenBase.desert, BiomeGenBase.desert, BiomeGenBase.savanna,
            BiomeGenBase.plains, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.forest,
            BiomeGenBase.roofedForest, BiomeGenBase.extremeHills, BiomeGenBase.extremeHills,
            BiomeGenBase.birchForest, BiomeGenBase.swampland, BiomeGenBase.swampland, BiomeGenBase.taiga,
            BiomeGenBase.icePlains, BiomeGenBase.coldTaiga, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F,
            BiomeGenBase.megaTaiga, BiomeGenBase.jungle, BiomeGenBase.jungle};*/


        biomeRandomizer = BiomeRandomizer.instance;
    }

    public int nextInt(int maximum) {
        return super.nextInt(maximum);
    }


    /**
     * Returns a list of integer values generated by this layer. These may be interpreted as temperatures, rainfall
     * amounts, or biomeList[] indices based on the particular GenLayer subclass.
     */
    public int[] getInts(int par1, int par2, int par3, int par4)
    {
        int[] var5 = this.parent.getInts(par1, par2, par3, par4);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; var7++)
        {
            for (int var8 = 0; var8 < par3; var8++)
            {
                this.initChunkSeed((long)(var8 + par1), (long)(var7 + par2));
                int var9 = var5[var8 + var7 * par3];
                int var10 = (var9 & 3840) >> 8;
                var9 &= -3841;

                // Random biome distribution
                if (isBiomeOceanic(var9)){
                    var6[var8 + var7 * par3] = var9;
                } else if (var9 == BiomeGenBase.mushroomIsland.biomeID){
                    var6[var8 + var7 * par3] = var9;
                }else if (var9 >= 1 && var9 <= 4) {
                    var6[var8 + var7 * par3] = biomeRandomizer.randomBiome(this).biomeID;
                }else{
                    var6[var8 + var7 * par3] = BiomeGenBase.mushroomIsland.biomeID;
                }
            }
        }

        return var6;
    }
}