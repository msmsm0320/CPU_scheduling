# CPU 스케줄링 시뮬레이터

이 프로젝트는 CPU 스케줄링 알고리즘을 구현한 Java 기반의 시뮬레이터입니다. **FCFS (First-Come-First-Serve)**와 **SJF (Shortest Job First)** 두 가지 스케줄링 알고리즘을 구현하여 프로세스 스케줄링을 시뮬레이션합니다.

## 주요 기능

- **First-Come-First-Serve (FCFS):**
  - 프로세스가 도착한 순서대로 실행됩니다.
  - 가장 단순하고 직관적인 스케줄링 방법입니다.

- **Shortest Job First (SJF):**
  - 프로세스의 CPU 버스트 시간이 짧은 순서대로 실행됩니다.
  - 평균 대기 시간을 최소화할 수 있는 스케줄링 방법입니다.

## 동작 방식

- **PCB (Process Control Block):**
  각 프로세스는 `PCB` 클래스 객체로 표현됩니다. PCB는 다음과 같은 정보를 포함합니다:
  - 도착 시간 (Arrival Time)
  - CPU 버스트 시간 (CPU Burst Time)
  - 총 CPU 실행 시간 (Total CPU Time)
  - 입출력 버스트 시간 (I/O Burst Time)

- **FCFS 알고리즘:**
  1. 프로세스들이 도착한 순서대로 큐에 추가됩니다.
  2. 큐의 순서대로 프로세스를 실행합니다.

- **SJF 알고리즘:**
  1. 도착한 프로세스들 중 CPU 버스트 시간이 가장 짧은 프로세스를 선택합니다.
  2. 선택된 프로세스를 실행한 후 다시 프로세스를 선택합니다.

## 사용 방법

1. **프로젝트 빌드 및 실행:**
   - Java 환경에서 프로젝트를 빌드합니다.
   - `main` 메서드를 실행하여 프로그램을 시작합니다.

2. **입력 데이터:**
   - 각 프로세스의 도착 시간, CPU 버스트 시간, 총 CPU 시간 등을 입력하거나 설정된 데이터 파일을 사용합니다.

3. **출력 결과:**
   - 각 알고리즘에 따라 프로세스 실행 순서와 평균 대기 시간, 평균 반환 시간 등을 출력합니다.

## 코드 구조

- **PCB 클래스:**
  - 프로세스의 속성 및 생성자를 정의합니다.

- **FCFS 및 SJF 구현:**
  - 각 알고리즘의 로직은 Java 컬렉션을 활용하여 구현되었습니다.
  - FCFS는 큐를 사용하고, SJF는 정렬된 리스트를 사용합니다.

## 예제 실행 화면

- FCFS 및 SJF 실행 결과:
  
  [B989046 전민서 운영체제 programming 과제 testcase 실행결과.pdf](https://github.com/user-attachments/files/17790840/B989046.programming.testcase.pdf)
