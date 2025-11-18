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

    @Test
    void 유효한_블록을_추가하면_정상작동한다() {
        BlockChain blockChain = new BlockChain(0);
        Block last = blockChain.getLastBlock();
        List<String> data = List.of("tx-data");
        String merkle = Block.calculateMerkleRoot(data);

        Block newBlock = new Block(
                last.getIndex() + 1,
                System.currentTimeMillis(),
                last.getHash(),
                merkle,
                0,
                data
        );

        assertTrue(blockChain.addBlock(newBlock));
        assertEquals(2, blockChain.getChainCopy().size());
    }

    @Test
    void 유효하지_않은_블록을_추가하면_블록이_추가되지_않는다() {
        BlockChain blockchain = new BlockChain(1);

        // 잘못된 previousHash 넣기
        Block invalidBlock = new Block(
                1, System.currentTimeMillis(), "wrong-hash",
                Block.sha256("dummy"), 0,
                List.of("tx")
        );

        assertFalse(blockchain.addBlock(invalidBlock));
        assertEquals(1, blockchain.getChainCopy().size()); // 제네시스만 존재해야 함
    }

}
