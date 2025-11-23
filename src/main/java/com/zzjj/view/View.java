package com.zzjj.view;

import java.util.Scanner;

public class View {
    public int blockChainDifficulty() {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("블록체인 채굴 난이도를 입력하세요. (범위 1~10)");
                String input = sc.nextLine();
                return Parser.blockChainDifficultyParser(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int nodeNum() {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("참여할 노드 수를 입력하세요: (범위 1~5)");
                String input = sc.nextLine();
                return Parser.nodeNumParser(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String nodeName() {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("참여할 노드 이름을 입력하세요: (길이 10자 이하, 공백 불가)");
                String input = sc.nextLine();
                return Parser.nodeNameParser(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
