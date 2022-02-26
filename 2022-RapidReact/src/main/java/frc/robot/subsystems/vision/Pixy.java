package frc.robot.subsystems.vision;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.LEDFeedback;
import io.github.pseudoresonance.pixy2api.Pixy2;
import io.github.pseudoresonance.pixy2api.Pixy2CCC;
import io.github.pseudoresonance.pixy2api.Pixy2CCC.Block;
import io.github.pseudoresonance.pixy2api.links.SPILink;

public class Pixy extends SubsystemBase {

    private static final Logger LOG = LoggerFactory.getLogger(LEDFeedback.class);
    
    private Pixy2 pixy;
    
    public Pixy() {
        pixy = Pixy2.createInstance(new SPILink());
        pixy.init();
    }

    private static int getArea(Block block) {
        return block.getWidth() * block.getHeight();
    }

    private static Block getBiggestBlock(Pixy2 pixy) {
        ArrayList<Block> blocks = pixy.getCCC().getBlockCache();
        Block biggestBlock = null;

        for (Block block : blocks) {
            if (biggestBlock != null) {
                if (getArea(block) > getArea(biggestBlock)) {
                    biggestBlock = block;
                }
            }
            else {
                biggestBlock = block;
            }
        }

        return biggestBlock;
	}

    public Ball getBall() {
        Block block = getBiggestBlock(pixy);
        return new Ball(block.getX(), block.getY(), block.getWidth(), block.getHeight());
    }

    @Override
    public void periodic() {
        int blockCount = pixy.getCCC().getBlocks(false, Pixy2CCC.CCC_SIG1 | Pixy2CCC.CCC_SIG2, 2);
        
        if (blockCount != -2) {
            ArrayList<Block> block = pixy.getCCC().getBlockCache();
            
            if (block.size() == 0) {
                LOG.info("No blocks detected");
            }
            else {
                LOG.info("First block pos: (" + Integer.toString(block.get(0).getX()) + ", " + Integer.toString(block.get(0).getY()) + "), size: (" + Integer.toString(block.get(0).getWidth()) + ", " + Integer.toString(block.get(0).getHeight()) + ")");
            }
        }
    }
}
