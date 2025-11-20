import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @Test
    @DisplayName("유효한 트랜잭션을 받으면 Mempool에 저장한다")
    void 유효한_트랜잭션을_받으면_Mempool에_저장한다() {
        BlockChain chain = new BlockChain(1);
        NetWork netWork = new NetWork();
        Node alice = new Node("Alice", "Bob", chain, netWork);

        Transaction tx = new Transaction("Alice", "Bob", 1.0, "fake");

        alice.receiveTransaction(tx);
        assertEquals(1, alice.getMempoolSize());
    }

    @Test
    @DisplayName("유효하지 않은 트랜잭션은 Mempool에 저장되지 않는다")
    void 유효하지_않은_트랜잭션은_Mempool에_저장되지_않는다() {
        BlockChain chain = new BlockChain(1);
        NetWork netWork = new NetWork();
        Node alice = new Node("Alice", "Alice", chain, netWork);

        //signature가 없는 트랜잭션
        Transaction tx = new Transaction("Alice", "Bob", 1.0, "");

        alice.receiveTransaction(tx);
        assertEquals(0, alice.getMempoolSize());
    }
}
