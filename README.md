# 🧱 우테코 프리코스 오픈미션

> **자바로 구현한 블록체인 무결성 검증 시스템**

이 프로젝트는 블록체인의 핵심 개념인 **무결성(integrity)** 과 **연결성(linkage)** 을 학습하기 위해
순수 Java로 설계된 미니 블록체인 구현입니다.  
각 블록은 이전 블록의 해시를 포함하며, 중간 블록의 데이터가 조작되면 체인 전체의 유효성 검증을 통해
위조된 블록을 자동으로 감지할 수 있습니다.

---

## 🚀 주요 기능

- **Block / Blockchain 객체 설계**
    - 블록: `index`, `timestamp`, `data`, `previousHash`, `hash`
    - 체인: `List<Block>` 로 연결된 단일 체인 구조

- **무결성 검증**
    - `Blockchain.isValid()` 메서드로 체인 전체 유효성 검사
    - `findInvalidBlocks()` 메서드로 조작된 블록의 인덱스 탐지

- **위조 감지 테스트**
    - 중간 블록의 `data`를 변경했을 때 체인 검증 실패 확인
    - 정상 체인에서는 `true`, 조작된 체인에서는 `false` 반환

- **확장 아이디어**
    - Merkle Tree 기반 데이터 요약
    - 디지털 서명(ECDSA)
    - 여러 노드 간 체인 동기화 시뮬레이션

---

## 🧩 프로젝트 구조
```plaintext
src/
├── main/java/com/yourname/minichain/
│    ├── domain/
│    │     ├── Block.java
│    │     └── Blockchain.java
│    ├── util/
│    │     └── HashUtil.java
│    └── Main.java
└── test/java/com/yourname/minichain/
└── BlockchainTest.java
```
