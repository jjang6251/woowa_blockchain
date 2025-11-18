import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zzjj.domain.Block;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlockTest {
    @Test
    @DisplayName("머클루트_생성")
    void 머클루트_생성() {
        List<String> dataList = List.of("tx1", "tx2", "tx3");
        String merkle = Block.calculateMerkleRoot(dataList);

        assertNotNull(merkle);
        assertEquals(64, merkle.length()); //SHA-256 길이
    }

    @Test
    @DisplayName("블록해쉬값_계산")
    void 블록해쉬값_계산() {
        int index = 1;
        long timestamp = System.currentTimeMillis();
        String prevHash = "abcd1234";
        String merkle = Block.sha256("dummy");
        long nonce = 0;

        String hash = Block.calculateHash(index, timestamp, prevHash, merkle, nonce);

        assertNotNull(hash);
        assertEquals(64, hash.length());
    }

    @Test
    @DisplayName("블록유효성테스트")
    void 블록유효성테스트() {
        // 제네시스 블록
        Block genesis = new Block(
                0, System.currentTimeMillis(), "0",
                Block.sha256("genesis"),
                0, List.of("genesis-data")
        );

        // 다음 블록
        Block block1 = new Block(
                1, System.currentTimeMillis(), genesis.getHash(),
                Block.sha256("block1-root"),
                0, List.of("tx1")
        );

        assertTrue(block1.isValid(genesis, 0));
    }
}
