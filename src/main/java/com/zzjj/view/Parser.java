package com.zzjj.view;

public class Parser {

    public static int blockChainDifficultyParser(String input) {
        inputHasText(input);
        int difficulty = checkInt(input);

        if (difficulty > 10) {
            throw new IllegalArgumentException("[ERROR] 난이도는 1~10 중에서 골라주세요");
        }
        if (difficulty < 1) {
            throw new IllegalArgumentException("[ERROR] 난이도는 양수 1~10 중에서 입력해주세요");
        }
        return difficulty;
    }

    public static int nodeNumParser(String input) {
        inputHasText(input);
        int nodeNum = checkInt(input);

        if (nodeNum > 5) {
            throw new IllegalArgumentException("[ERROR] 노드 수는 5이하로 입력해주세요");
        }
        if (nodeNum < 0) {
            throw new IllegalArgumentException("[ERROR] 노드 수는 양수로 입력해주세요.");
        }
        if (nodeNum == 0) {
            throw new IllegalArgumentException("[ERROR] 노드 수는 0이상 입력해주세요");
        }
        return nodeNum;
    }

    public static String nodeNameParser(String input) {
        inputHasText(input);

        if(input.length() > 10) {
            throw new IllegalArgumentException("[ERROR] 노드 이름은 10자 미만으로 해주세요");
        }

        if (input.contains(" ")) {
            throw new IllegalArgumentException("[ERROR] 노드 이름에 공백을 포함할 수 없습니다");
        }
        return input;
    }


    private static void inputHasText(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 빈 값을 입력할 수 없습니다.");
        }
    }

    private static int checkInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 입력은 숫자여야 합니다.");
        }
    }
}
