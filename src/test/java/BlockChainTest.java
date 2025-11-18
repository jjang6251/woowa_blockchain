import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.domain.Block;
import com.zzjj.domain.BlockChain;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BlockChainTest {
    @Test
    void 제네시스_블록이_존재한다() {
        BlockChain blockChain = new BlockChain(1);
        assertEquals(1, blockChain.getChainCopy().size());
    }
}
