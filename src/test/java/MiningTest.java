import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.domain.Block;
import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MiningTest {
    @Test
    @DisplayName("채굴을 하면 체인에 블록이 등록된다")
    void 채굴을_하면_체인에_블록이_등록된다() {
        BlockChain chain = new BlockChain(2);
        NetWork netWork = new NetWork();

        Node miner = new Node("Miner", "Miner", chain, netWork);
        netWork.register(miner);

        Transaction tx = new Transaction("Miner", "Bob", 1.0, "sig");
        miner.receiveTransaction(tx);

        Block newBlock = miner.mineBlock(10, 2);

        assertTrue(chain.isChainValid());
        assertEquals(2, chain.getChainCopy().size());
    }
}
