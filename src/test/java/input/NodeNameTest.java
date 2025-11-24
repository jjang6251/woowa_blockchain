package input;

import static org.junit.jupiter.api.Assertions.*;

import com.zzjj.view.Parser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NodeNameTest {
    @Test
    @DisplayName("정상 이름 입력")
    void validName() {
        assertEquals("NodeA", Parser.nodeNameParser("NodeA"));
    }

    @Test
    @DisplayName("빈 입력 에러")
    void emptyInputError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNameParser("   "));
    }

    @Test
    @DisplayName("10자 초과 시 에러")
    void tooLongError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNameParser("ABCDEFGHIJK")); // 11자
    }

    @Test
    @DisplayName("공백 포함 시 에러")
    void containsSpaceError() {
        assertThrows(IllegalArgumentException.class,
                () -> Parser.nodeNameParser("Hello World"));
    }
}
