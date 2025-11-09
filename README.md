# 🧱 Java Mini Blockchain – 무결성 검증 & 머클 트리

> **자바로 구현한 미니 블록체인 무결성 검증 프로젝트**  
> – 1단계(기본 무결성 검증) + 2단계(머클 트리, 체인 비교, 파일 저장)까지 구현

---

## 1. 프로젝트 개요

이 프로젝트는 블록체인의 핵심 개념인 **무결성(integrity)** 과  
**연결성(linkage), 데이터 위조 탐지**를 학습하기 위해 만든 **순수 Java 기반 미니 블록체인**입니다.

각 블록은 이전 블록의 해시를 포함하고, 블록 안의 데이터는 **머클 트리(Merkle Tree)** 로 요약됩니다.  
중간 블록의 데이터가 조작되면, 체인 전체 유효성 검증을 통해 **어느 블록에서 무결성이 깨졌는지 탐지**할 수 있습니다.

> **미션 유형:** 고난도 문제 해커톤  
> **목표:** 익숙한 기술(Java)을 사용해, 실제 블록체인의 무결성 검증 구조를 직접 설계·구현해보기

---

## 2. 구현 범위

### ✅ 1단계 – 기본 블록체인 & 무결성 검증

**기능 요약**

- `Block` / `Blockchain` 도메인 모델 설계
  - `Block`
    - `index`, `timestamp`, `data`, `previousHash`, `hash`
    - `calculateHash()` 로 블록 내부 필드 기반 해시 계산
  - `Blockchain`
    - `List<Block> chain`
    - `addBlock(String data)` : 새 블록 추가
- **체인 무결성 검증**
  - `isValid()`
    - 현재 블록의 `hash` 와 `calculateHash()` 결과 비교
    - 현재 블록의 `previousHash` 와 이전 블록의 `hash` 비교
  - 둘 중 하나라도 어긋나면 **체인 무효(invalid)** 판정
- **조작된 블록 탐지**
  - `findInvalidBlocks()`
    - 무결성 검증 실패가 발생한 블록 인덱스 목록 반환

**1단계 목표**

- “블록체인에서는 왜 중간 블록을 고치면 안 되는지”를  
  **해시 기반 구조로 직접 눈으로 확인**하는 것

---

### ✅ 2단계 – 머클 트리, 체인 비교, 파일 저장

#### 2-1. Merkle Tree 기반 데이터 무결성

한 블록에 여러 개의 데이터를 담고, 이를 **머클 루트(merkleRoot)** 로 요약합니다.

- `Block`
  - `List<String> dataList` : 블록 안에 들어가는 데이터 목록
  - `String merkleRoot` : `dataList` 로부터 계산한 머클 루트
  - 블록 해시 = `index + timestamp + merkleRoot + previousHash` 를 해시한 값
- `MerkleUtil` (또는 `MerkleTree` 클래스)
  - `calculateMerkleRoot(List<String> dataList)` 메서드 구현
  - 데이터 하나라도 변경되면 `merkleRoot` → `hash` 까지 연쇄적으로 변경

> 👉 **효과:**  
> 단일 문자열이 아니라 **여러 데이터가 들어간 블록**에서도  
> 부분 데이터 위조가 전체 블록/체인의 무결성에 영향을 미치는 구조를 확인할 수 있음.

---

#### 2-2. 체인 비교 & 포크 해결 (Longest Chain Rule 간이 구현)

서로 다른 두 체인이 있을 때,  
**“어떤 체인을 채택할 것인가?”** 를 결정하는 간단한 규칙을 구현합니다.

- `isValidChain(List<Block> chain)`
  - 다른 체인도 1단계에서 구현한 무결성 검증 로직으로 검사
- `replaceChain(List<Block> newChain)`
  - 새 체인이 유효하고(`isValidChain == true`),  
    현재 체인보다 더 길면 → 새 체인으로 교체
  - 그렇지 않으면 교체하지 않음

> 👉 **효과:**  
> 실제 블록체인의 “가장 긴 유효 체인 선택” 규칙을  
> 간단히 시뮬레이션하여 **포크 상황에서의 무결성 유지** 감각을 익힐 수 있음.

---

#### 2-3. 파일 저장 / 로드 (Persistence)

체인을 파일로 저장하고 다시 불러와도  
무결성 검증이 통과하는지 확인합니다.

- `saveToFile(Path path)`
  - 현재 `chain` 을 파일로 직렬화(예: Java 직렬화 또는 JSON)
- `loadFromFile(Path path)` (static 메서드)
  - 파일에서 체인 데이터를 읽어와 `Blockchain` 객체 생성
- 로드 후 `isValid()` 호출로 무결성 유지 여부 확인

> 👉 **효과:**  
> 단순 메모리 상의 시뮬레이션을 넘어서  
> **“저장된 원장의 무결성”**까지 고려할 수 있음.

---

#### 2-4. 무결성 검증 + 로그 출력

- `validateAndLog()` (또는 유사 메서드)
  - `isValid()` / `findInvalidBlocks()` 를 호출하고
  - 콘솔 또는 파일에 검증 결과를 로그로 출력

예시:

```text
[OK] Blockchain is valid.
[TAMPER] Block 2 is invalid: hash mismatch
[TAMPER] Block 3 is invalid: previousHash mismatch