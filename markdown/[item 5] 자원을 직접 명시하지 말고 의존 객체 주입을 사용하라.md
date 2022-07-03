## 많은 클래스가 하나 이상의 자원에 의존한다.
- 하나의 자원으로 모든 용도에 대응할 수 있기를 바라는 건 너무 순진한 생각이다.
  ```java
  public class SpellChecker {
        private static final Lexicon dictionary = Collections.emptyList;
        ...
  }
  ```
- 사용하는 자원에 따라 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.
  - 사용하는 클래스가 여러 자원 인스턴스를 지원해야 하며, 클라이언트가 원하는 자원을 사용해야 한다.
  - 위 조건을 만족하는 패턴이 인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식이다.
  ```java
  public class SpellChecker {
        private final Lexicon dictionary;
        
        public SpellChecker(Lexicon dictionary) {
            this.dictionary = Objects.requireNonNull(dictionary);
        } 
        ...
  }
  ```
- 의존 객체 주입이 유연성과 테스트 용이성을 개선해주긴 하지만, 의존성이 수천 개나 되는 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다.