package input;

import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.view.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlockChainDifficultyTest {
    @Test
    @DisplayName("정상 입력 (1~10) 테스트")
    void validInput() {
        assertEquals(1, Parser.blockChainDifficultyParser("1"));
        assertEquals(5, Parser.blockChainDifficultyParser("5"));
        assertEquals(10, Parser.blockChainDifficultyParser("10"));
    }

    @Test
    @DisplayName("빈 문자열 입력 시 에러")
    void emptyInputError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.blockChainDifficultyParser("  "));
    }

    @Test
    @DisplayName("숫자가 아닐 때 에러")
    void nonNumberError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.blockChainDifficultyParser("abc"));
    }

    @Test
    @DisplayName("10보다 크면 에러")
    void overTenError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.blockChainDifficultyParser("11"));
    }

    @Test
    @DisplayName("1보다 작으면 에러")
    void underOneError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.blockChainDifficultyParser("0"));
    }
}
