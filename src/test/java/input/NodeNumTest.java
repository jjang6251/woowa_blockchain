package input;

import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.view.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NodeNumTest {
    @Test
    @DisplayName("정상 입력 (1~5)")
    void validInput() {
        assertEquals(1, Parser.nodeNumParser("1"));
        assertEquals(5, Parser.nodeNumParser("5"));
    }

    @Test
    @DisplayName("빈 입력 에러")
    void emptyInputError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNumParser(""));
    }

    @Test
    @DisplayName("숫자 아님 에러")
    void nonNumberError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNumParser("a"));
    }

    @Test
    @DisplayName("5 초과 입력 시 에러")
    void overFiveError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNumParser("6"));
    }

    @Test
    @DisplayName("음수 입력 시 에러")
    void negativeError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNumParser("-1"));
    }

    @Test
    @DisplayName("0 입력 시 에러")
    void zeroError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNumParser("0"));
    }
}
