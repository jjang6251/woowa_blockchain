import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.domain.BlockChain;
import com.zzjj.domain.NetWork;
import com.zzjj.domain.Node;
import com.zzjj.domain.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NetworkTest {
    @Test
    @DisplayName("트랜잭션이 노드에게 잘 전파가 된다")
    void 트랜잭션이_노드에게_잘_전파가_된다() {
        BlockChain chain = new BlockChain(1);
        NetWork netWork = new NetWork();
        Node alice = new Node("Alice", "Alice", chain, netWork);
        Node bob = new Node("Bob", "Bob", chain, netWork);

        netWork.register(alice);
        netWork.register(bob);

        Transaction tx = new Transaction("Alice", "Bob", 1.0, "sig");

        netWork.broadCastTransasction(tx, alice);

        assertEquals(1, alice.getMempoolSize());
        assertEquals(1, bob.getMempoolSize());
    }
}
