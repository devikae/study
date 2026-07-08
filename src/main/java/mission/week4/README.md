# lottoMaker v4
> 싱글/멀티 스레드로 대량 생성 속도 비교

## 1. 요구사항
- 단일 스레드 방식으로 로또 대량 생성 
- 멀티 스레드 방식으로 로또 대량 생성
- 두 방식의 실행시간 비교 
- 공유 컬렉션 사용할 때 발생할 수 있는 문제 재현

## 2. 측정 기준 
- 기준 데이터: `1_000_000건`
- 시간 단위:  ms
- 측정 범위: 100만 건의 List 완성 까지

            $results += [pscustomobject]@{ Run = $i; Name = $currentName; Duration = [double]$Matches[1] }
        }
  }
  }
  $results | Sort-Object Name, Run | Format-Table -AutoSize
## 3. 성능 측정 결과

- 기준 데이터: `1_000_000`건
- 스레드 수: `4`
- 시간 단위: ms
- 측정 횟수: 10회
- `multi thread not control concurrency`는 동시성 제어를 하지 않아 실제 개수가 기대 개수와 다를 수 있다.

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
      <th>6회차</th>
      <th>7회차</th>
      <th>8회차</th>
      <th>9회차</th>
      <th>10회차</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td rowspan="4">1,000,000건</td>
      <td>single thread</td>
      <td align="right">392.4203ms</td>
      <td align="right">396.4377ms</td>
      <td align="right">376.2981ms</td>
      <td align="right">378.4370ms</td>
      <td align="right">385.5728ms</td>
      <td align="right">383.0196ms</td>
      <td align="right">389.4836ms</td>
      <td align="right">387.4281ms</td>
      <td align="right">380.7896ms</td>
      <td align="right">375.2735ms</td>
    </tr>
    <tr style="background-color: #fff1f1;">
      <td><strong>multi thread not control concurrency (fail)</strong></td>
      <td align="right">299.0326ms</td>
      <td align="right">300.8579ms</td>
      <td align="right">303.7871ms</td>
      <td align="right">317.3733ms</td>
      <td align="right">302.4526ms</td>
      <td align="right">302.6623ms</td>
      <td align="right">308.7585ms</td>
      <td align="right">291.2546ms</td>
      <td align="right">290.6518ms</td>
      <td align="right">288.0525ms</td>
    </tr>
    <tr>
      <td>synchronized block control concurrency</td>
      <td align="right">311.6016ms</td>
      <td align="right">312.9210ms</td>
      <td align="right">311.9132ms</td>
      <td align="right">324.3715ms</td>
      <td align="right">323.1009ms</td>
      <td align="right">329.2250ms</td>
      <td align="right">334.9000ms</td>
      <td align="right">321.3925ms</td>
      <td align="right">314.9874ms</td>
      <td align="right">311.1928ms</td>
    </tr>
    <tr>
      <td>multi thread avoid shared state</td>
      <td align="right">270.3777ms</td>
      <td align="right">289.3173ms</td>
      <td align="right">273.7653ms</td>
      <td align="right">301.0211ms</td>
      <td align="right">299.4021ms</td>
      <td align="right">299.3927ms</td>
      <td align="right">309.6105ms</td>
      <td align="right">281.8933ms</td>
      <td align="right">269.7986ms</td>
      <td align="right">275.9758ms</td>
    </tr>
  </tbody>
</table>

추가로 실패 케이스인 `multi thread not control concurrency (fail)`를 제외하고, 작은 작업량에서도 5회씩 측정했다.

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
      <td rowspan="3">1,000건</td>
      <td>single thread</td>
      <td align="right">6.2414ms</td>
      <td align="right">1.1893ms</td>
      <td align="right">1.1595ms</td>
      <td align="right">1.0099ms</td>
      <td align="right">0.8241ms</td>
    </tr>
    <tr>
      <td>synchronized block control concurrency</td>
      <td align="right">2.6806ms</td>
      <td align="right">1.4551ms</td>
      <td align="right">1.3391ms</td>
      <td align="right">1.2959ms</td>
      <td align="right">1.2531ms</td>
    </tr>
    <tr>
      <td>multi thread avoid shared state</td>
      <td align="right">2.1625ms</td>
      <td align="right">1.4374ms</td>
      <td align="right">1.3184ms</td>
      <td align="right">1.7483ms</td>
      <td align="right">1.1235ms</td>
    </tr>
    <tr>
      <td rowspan="3">10,000건</td>
      <td>single thread</td>
      <td align="right">6.5009ms</td>
      <td align="right">3.6679ms</td>
      <td align="right">3.8639ms</td>
      <td align="right">3.3411ms</td>
      <td align="right">3.2905ms</td>
    </tr>
    <tr>
      <td>synchronized block control concurrency</td>
      <td align="right">9.1799ms</td>
      <td align="right">3.1603ms</td>
      <td align="right">3.0936ms</td>
      <td align="right">3.0966ms</td>
      <td align="right">2.9358ms</td>
    </tr>
    <tr>
      <td>multi thread avoid shared state</td>
      <td align="right">3.1351ms</td>
      <td align="right">2.9069ms</td>
      <td align="right">5.1778ms</td>
      <td align="right">2.9135ms</td>
      <td align="right">2.7865ms</td>
    </tr>
  </tbody>
</table>

## 4. 객체 구조 

### TaskFactory 
로또 티켓을 생성하는 `작업`을 만들어주는 객체.

스레드를 직접 실행하지 않는다. 
실행 가능한 작업을 만들고, Runner에 넘길 수 있도록 만든 객체. 
- 실행 결과를 반환하지 않고 외부에서 전달받은 List에 티켓을 추가하는 메서드
- 공유 컬렉션 회피를 위해 List를 결과로 반환하는 메서드
- synchronized 블럭을 적용한 메서드

### ThreadRunner
작업을 어떤 방식으로 실행할지 정의하는 인터페이스다.

`ThreadRunner`는 `List<Runnable>`을 받아 실행한다.  
구현체에 따라 같은 작업 목록도 싱글스레드로 실행될 수도 있고, 멀티스레드로 실행될 수도 있다.

또한 실행할 작업 목록이 `null`이거나 비어 있는 경우를 검증하기 위해 공통 검증 메서드를 제공

### SingleThreadRunner
전달 받은 작업을 하나의 스레드에서 실행할 수 있도록 만든 구현체

### MultiThreadRunner
`List<Runnable>`을 전달받으면 작업 개수만큼 스레드 풀을 만들고, 각 작업을 동시에 실행한다.  
`Future.get()`을 사용해 모든 작업이 끝날 때까지 기다린다.

### PerformanceMeasurer
작업의 실행 시간을 측정하는 객체

작업이 싱글 스레드인지, 멀티 스레드인지, 어떤 컬렉션을 사용하는지 알지 못한다.

### ResultValidator
실험 결과가 기대한 개수와 일치하는지 검증하는 객체
`실제 생성된 티켓 개수 == 기대한 티켓 개수`

### PerformanceResult
측정 결과를 출력하기 위한 객체

## 5. 설계 구조 
- 작업을 만든다.
- 그 작업을 러너를 이용해 실행한다. 
- 작업의 소요 시간을 측정한다.
- 작업의 성공을 검증한다.


### 단일 스레드 방식

단일 스레드 방식은 `SingleThreadRunner`로 구현했다.  
`TaskFactory`가 `1,000,000`개의 로또 티켓을 생성하는 `Runnable` 작업을 만들고, `SingleThreadRunner`는 이 작업을 하나의 스레드에서 실행한다.

작업 실행에는 `Executors.newSingleThreadExecutor()`를 사용했다.  
작업을 제출한 뒤 `Future.get()`을 호출해 티켓 생성이 모두 끝날 때까지 기다린다.

단일 스레드에서는 하나의 스레드만 `ArrayList`에 접근하므로 동시성 문제가 발생하지 않는다.  
따라서 이 방식은 멀티스레드 실험의 성능과 정확성을 비교하기 위한 기준으로 사용했다.

### 멀티 스레드 방식 
멀티 스레드 방식은 `MultiThreadRunner`로 구현했다.  
`TaskFactory`가 전체 로또 티켓 생성 개수를 `스레드 수`만큼 나눈 `Runnable` 작업들을 만들고, `MultiThreadRunner`는 이 작업들을 여러 스레드에서 동시에 실행한다.

작업 실행에는 `Executors.newFixedThreadPool()`을 사용했다.  
작업들을 제출한 뒤 각 작업의 `Future.get()`을 호출해 모든 스레드의 티켓 생성이 끝날 때까지 기다린다.

동시성 제어를 하지 않은 멀티 스레드 방식에서는 여러 스레드가 같은 `ArrayList`에 동시에 접근한다.  
`ArrayList`는 멀티스레드 환경에서 안전하지 않기 때문에 기대 개수보다 실제 생성 개수가 적게 나왔다.

그래서 동시성 문제를 해결하기 위해 두 가지 방식을 추가로 실험했다.

첫 번째는 `synchronized` 블록을 사용한 방식이다.  
공유 `ArrayList`는 그대로 사용하되, `add`하는 구간만 `synchronized`로 감싸 한 번에 하나의 스레드만 접근하게 했다.  
정확한 개수를 보장할 수 있지만, 여러 스레드가 락을 기다리기 때문에 성능이 떨어질 수 있다.

두 번째는 공유 상태를 회피하는 방식이다.  
각 스레드가 자기 로컬 리스트에 티켓을 생성한 뒤, 작업이 끝나면 결과 리스트들을 하나로 병합했다.  
공유 리스트에 동시에 접근하지 않기 때문에 동시성 문제가 발생하지 않고, 락 경쟁도 줄일 수 있다.

### ExecutorService 사용 이유
`ExecutorService`는 스레드를 직접 생성하고 관리하는 부담을 줄이기 위해 사용했다.

직접 `Thread`를 만들면 작업 실행, 종료, 예외 처리, 완료 대기 등을 직접 관리해야 한다.  
`ExecutorService`를 사용하면 작업을 제출하고, 스레드 풀에서 실행하며, `Future.get()`으로 작업 완료까지 기다릴 수 있다.

이번 실험에서는 싱글스레드와 멀티스레드 실행 방식을 비교해야 했기 때문에, 스레드 실행과 완료 대기를 일관되게 처리하기 위해 `ExecutorService`를 사용했다.

## 9. 결론
`1,000,000건`을 생성할 때는 멀티스레드 방식이 단일 스레드보다 빠르게 측정되었다.  
하지만 이것이 멀티스레드가 항상 더 빠르다는 의미는 아니다.

동시성 제어를 하지 않은 멀티스레드 방식은 실행 시간은 짧았지만, 실제 생성 개수가 기대 개수와 달라 실패한 결과였고 
따라서 성능을 비교할 때는 실행 시간뿐 아니라 결과가 정확한지도 함께 확인해야 한다.

`synchronized` 방식은 공유 `ArrayList`에 접근하는 구간을 보호해 정확한 결과를 만들었다.  
하지만 `add`할 때마다 락을 사용하므로 스레드들이 대기하는 시간이 생긴다.  
작업에 비해 동기화 비용이 커지면 멀티스레드의 이점은 줄어들 수 있다.

공유 상태를 회피하는 방식은 각 스레드가 자기 리스트에 티켓을 생성한 뒤 마지막에 병합했다.  
공유 리스트에 동시에 접근하지 않기 때문에 락 경쟁을 줄일 수 있었고, 이번 실험에서는 가장 빠르게 측정되었다.

추가로 `1,000건`, `10,000건`처럼 작은 작업량도 측정했다.  
작업량이 작을 때는 스레드 풀 생성, 작업 제출, 완료 대기 같은 부가 비용의 영향이 상대적으로 커진다.  
그래서 일부 구간에서는 단일 스레드가 멀티스레드 방식보다 더 빠르게 측정되었다.

결국 멀티스레드의 성능은 단순히 스레드 수를 늘린다고 좋아지는 것이 아니다.  
작업을 나누기 쉬운지, 공유 상태가 얼마나 적은지, 동기화 비용이 얼마나 큰지에 따라 결과가 달라진다.업을 잘 나눌 수 있으며, 공유 상태로 인한 비용이 작을 때 효과가 커진다.
