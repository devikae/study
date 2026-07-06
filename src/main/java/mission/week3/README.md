# lottoMaker v3
> 로또 번호 출현 빈도 통계와 for/stream 카운팅 성능 비교

---

## 1. 요구사항

- 로또 티켓을 대량 생성한다.
- 각 번호(1~45)의 출현 빈도를 계산한다.
- for 방식과 stream 방식으로 같은 카운팅을 수행한다.
- 두 방식의 카운팅 결과가 완전히 같은지 먼저 검증한다.
- 결과가 같을 때만 실행 시간을 비교한다.
- 출현 빈도가 높은 상위 번호를 정렬해서 출력한다.

---

## 2. 측정 기준

- 기준 데이터: `1_000_000`건
- 시간 단위: ms
- 측정 범위: 카운팅 로직만 측정
- 측정 제외: 티켓 생성, 통계 Map 초기화, 정렬, 출현빈도 Top5 추출
- 정확성 검증: for 카운팅 결과 Map과 stream 카운팅 결과 Map의 `equals()` 비교

---

## 3. 성능 측정 결과

<table>
  <thead>
    <tr>
      <th>기준</th>
      <th>방식</th>
      <th>1회차</th>
      <th>2회차</th>
      <th>3회차</th>
      <th>4회차</th>
      <th>5회차</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td rowspan="2">1,000,000건</td>
      <td>for</td>
      <td align="right">142.1385ms</td>
      <td align="right">141.0896ms</td>
      <td align="right">133.5981ms</td>
      <td align="right">140.6998ms</td>
      <td align="right">138.7386ms</td>
    </tr>
    <tr>
      <td>stream</td>
      <td align="right">114.4504ms</td>
      <td align="right">98.9745ms</td>
      <td align="right">125.1978ms</td>
      <td align="right">124.3341ms</td>
      <td align="right">120.3178ms</td>
    </tr>
  </tbody>
</table>

---

## 4. Top5 결과

| 순위 | 1회차 | 2회차 | 3회차 | 4회차 | 5회차 |
|---:|---|---|---|---|---|
| 1 | 12번: 134,208회 | 8번: 133,821회 | 19번: 134,103회 | 20번: 133,938회 | 21번: 133,960회 |
| 2 | 35번: 133,971회 | 16번: 133,793회 | 10번: 134,013회 | 5번: 133,918회 | 38번: 133,890회 |
| 3 | 18번: 133,951회 | 21번: 133,793회 | 39번: 133,876회 | 25번: 133,903회 | 5번: 133,842회 |
| 4 | 20번: 133,949회 | 42번: 133,785회 | 22번: 133,811회 | 31번: 133,899회 | 13번: 133,799회 |
| 5 | 8번: 133,922회 | 15번: 133,778회 | 11번: 133,808회 | 13번: 133,891회 | 12번: 133,789회 |

---

## 5. 수정 전 객체 구조

1일차 커밋: `8254ad6 Add week3 lotto statistics comparison`

### FrequencyCounter

초기 구조에서는 `FrequencyCounter`가 다음 책임을 한 번에 가지고 있었다.

- 통계 Map 생성
- 1~45 번호 초기화
- for 또는 stream으로 카운팅
- Map을 List로 변환
- 출현 빈도 기준 정렬
- 수정 불가능한 List 반환

이 구조에서는 `computeStatsFor()`나 `computeStatsStreamSameLogic()` 전체를 측정하게 되었다. 그래서 실제 측정값에는 카운팅뿐 아니라 초기화, 리스트 변환, 정렬 시간이 함께 포함되었다.

### PerformanceComparator

초기 구조에서는 `PerformanceComparator.getDuration()`이 `FrequencyCounter`의 전체 계산 메서드를 호출했다.

```java
startTime = System.nanoTime();
ticketList = stats.computeStatsFor(tickets);
endTime = System.nanoTime();
```

이 방식은 "완성된 통계 결과를 만드는 전체 시간"을 재는 데는 쓸 수 있지만, "for와 stream의 순수 카운팅 성능"을 비교하기에는 부정확했다.

---

## 6. 변경한 코드 구조

### FrequencyCounter

현재 `FrequencyCounter`는 통계 초기화와 카운팅만 담당

- `initializeStatistics()`: 1~45 번호를 key로 가지고 값이 0인 Map 생성
- `countByFor(...)`: for 방식으로 카운팅
- `countByStream(...)`: stream 방식으로 카운팅
- `validate(...)`: 카운팅에 필요한 입력 상태 검증

정렬 책임은 제거 -> for/stream 카운팅만 따로 측정 가능한 상태로 변화

### PerformanceComparator

현재 `PerformanceComparator`는 측정 객체 역할을 한다.

- 측정 방식 선택: `FOR`, `STREAM`
- 측정 전 통계 Map 준비
- 카운팅 실행 시간 기록
- 카운팅된 Map 보관
- `getDuration()`으로 시간 조회
- `getStats()`로 카운팅 결과 조회

이 구조에서는 for와 stream을 각각 한 번씩만 실행하고, 그 실행 결과 Map을 그대로 비교할 수 있다.

### TopValueFinder

`TopValueFinder`는 정렬과 상위 N개 추출만 담당한다.

- 카운팅된 Map을 List로 변환
- 출현 빈도 내림차순 정렬
- 출현 빈도가 같으면 번호 오름차순 정렬
- limit만큼 잘라서 반환

카운팅과 측정이 끝난 뒤에 실행되므로, Top5 계산은 성능 측정값에 섞이지 않는다.

---

## 7. 책임 분리 변화

| 객체 | 수정 전 책임               | 수정 후 책임                            |
|---|-----------------------|----------------------------------------|
| `FrequencyCounter` | 초기화, 카운팅, 변환, 정렬, 반환  | 초기화, 카운팅, 입력 검증                        |
| `PerformanceComparator` | 전체 통계 계산 메서드 실행 시간 측정 | 카운팅 실행 시간 측정, duration과 countedMap 보관  |
| `TopValueFinder` | 없음                    | 정렬, Top N 추출                           |
| `Main` | 측정 호출과 출력             | 티켓 생성, for/stream 결과 비교, 성능 출력, Top5 출력 |

---

## 8. 테스트 기준

### FrequencyCounter

- 통계 Map이 1~45를 0으로 초기화하는지
- for 방식이 출현 빈도를 계산하는지
- stream 방식이 출현 빈도를 계산하는지
- for와 stream 결과 Map이 같은지
- 초기화되지 않은 Map이면 예외가 발생하는지
- 음수 카운트가 있으면 예외가 발생하는지

### PerformanceComparator

- `FOR` 방식으로 측정하면 실행 시간이 기록되는지
- `STREAM` 방식으로 측정하면 실행 시간이 기록되는지
- 측정 방식이 `null`이면 예외가 발생하는지
- 같은 티켓에 대해 for와 stream의 통계 결과가 같은지

### TopValueFinder

- 출현 빈도 높은 순으로 정렬되는지
- 출현 빈도가 같으면 번호가 작은 순으로 정렬되는지
- limit만큼만 반환하는지
- limit이 0이면 빈 리스트를 반환하는지
- limit이 음수이면 예외가 발생하는지
- null/empty statistics, null key, null value, 음수 value를 거부하는지

---

## 9. 정리
수정 전에는 "통계 결과를 만드는 기능"과 "성능을 측정하는 기능"이 섞여 있었다.
오늘 구조에서는 카운팅, 측정, 정렬을 각각 다른 객체로 분리했다.

가장 중요한 변화는 성능 비교 전에 for와 stream이 같은 결과 Map을 만드는지 먼저 확인하도록 한 점이다. 같은 일을 했다는 검증이 있어야 실행 시간 비교도 의미가 있다.

번호별 출현 빈도는 "번호 -> 횟수" 구조이므로 Map이 적합하다고 판단했다.
NumberValue를 key로 사용하면 이미 검증된 로또 번호 객체를 그대로 사용할 수 있기도 하다.