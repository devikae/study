## 📋 PR 제목
week3: 로또 통계 분석 및 성능 비교 기능 추가

## 📝 설명
week3에서는 로또 번호의 출현 빈도를 통계 내고, for/stream 방식의 카운팅 성능을 비교하는 기능을 추가했습니다.

### 주요 변경사항

#### 1. **FrequencyCounter** (새로운 구조)
- 책임 분리: 카운팅 로직만 담당
- `initializeStatistics()`: 1~45 번호를 0으로 초기화
- `countByFor()`: for 루프를 사용한 카운팅
- `countByStream()`: Stream API를 사용한 카운팅
- 입력 검증 로직 추가

#### 2. **PerformanceComparator** (새로운 객체)
- for/stream 카운팅의 실행 시간 측정
- 측정 결과 저장 및 조회
- 성능 비교를 위한 전용 객체

#### 3. **TopValueFinder** (새로운 객체)
- 카운팅 결과 정렬 및 Top N 추출
- 출현 빈도 내림차순 정렬
- 출현 빈도가 같을 경우 번호 오름차순 정렬

#### 4. **Main.java (week3)**
- 대규모 데이터(1,000,000건) 기반 성능 비교
- for/stream 결과 검증
- 성능 측정 결과 출력
- 상위 5개 출현 번호 출력

#### 5. **개선된 week1/week2 코드**
- `NumberGenerator.makeTickets()`: 대량 티켓 생성 메서드 추가
- `NumberValue.getInstance()`: 에러 메시지 개선
- `InputParser`: 파싱 책임 재정의 및 테스트 개선
- `MatchResult`: 등수 판정을 Enum으로 리팩토링
- `LottoMatcher`: 로또 비교 로직 분리 (새로운 객체)
- `ConsoleInputView/ConsoleOutputView`: View 계층 분리

### 성능 비교 결과 (1,000,000건 기준)

| 방식 | 평균 실행시간 |
|------|--------------|
| for | ~139ms |
| stream | ~116ms |

**결론**: Stream API가 약 17% 더 빠름

### 테스트 추가
- `FrequencyCounterTest`: 초기화, for/stream 카운팅, 검증
- `PerformanceComparatorTest`: 측정 정확성, 결과 일관성
- `TopValueFinderTest`: 정렬, 필터링, 경계 조건
- `InputParserTest`: 파싱 로직 개선
- `LottoMatcherTest`: 매칭 로직 검증
- `MatchResultTest`: Rank enum 테스트

## ✅ 체크리스트
- [x] 코드 리뷰 완료
- [x] 모든 테스트 통과
- [x] 새로운 테스트 추가
- [x] 책임 분리 및 구조 개선
- [x] 문서(README.md) 작성

## 🔗 관련 이슈
없음

## 📸 스크린샷 (선택)
성능 측정 결과는 Main 실행 시 콘솔에 출력됩니다.
