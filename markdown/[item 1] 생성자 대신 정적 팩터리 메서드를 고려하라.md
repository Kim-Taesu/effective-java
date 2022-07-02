## 생성자 대신 정적 팩터리 메서드 사용

- 클래스는 생성자와 별도로 정적 팩터리 메서드를 제공할 수 있다.

```java
public static Boolean valueOf(boolean b) {
    return (b ? TRUE : FALSE);
}
```

### 정적 팩터리 메서드의 장점

1. **이름을 가질 수 있다.**
2. 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.
    - 불변 클래스는 인스턴스를 미리 만들어 놓거나 새로 생성한 인스턴스를 캐싱하여 재활용하는 식으로 불필요한 객체를 아예 생성하지 않는다.
    - 플라이웨이트 패턴도 이와 비슷한 기법이다.
    - 반복되는 요청에 같은 객체를 반환하는 식으로 정적 팩터리 방식의 클래스는 언제 어느 인스턴스를 살아 있게 할지를 철저히 통제할 수 있다.
        - 이런 클래스를 인스턴스 통제 클래스라 한다.
3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
    - 반환할 객체의 클래스를 자유롭게 선택할 수 있게 하는 엄청난 유연성을 선물한다.
    - API를 만들 때 이 유연성을 응용하면 구현 클래스를 공개하지 않아도 그 객체를 반환할 수 있어 API를 작게 유지할 수 있다.
        - 인터페이스 기반 프레임워크를 만드는 핵심 기술이다.
4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
    - 반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관없다.
5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

### 정적 팩터리 메서드의 단점

1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
2. 정적 패겉리 메서드는 프로그래머가 찾기 힘들다.

### 정적 팩터리 메서드 네이밍 방식

- from : 매겨변수 하나를 받아서 해당 타입의 인스턴르를 반환하는 형변환 메서드

    ```java
    Date d = Date.from(instant);
    ```

- of : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드

    ```java
    Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);
    ```

- valueOf : from과 of의 더 자세한 버전

    ```java
    BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);
    ```

- instance 혹은 getInstance: 매개변수를 받는다면 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.

    ```java
    StackWlaker luke = StackWlaker.getInstance(options);
    ```

- create 혹은 newInstance : instance 혹은 getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.

    ```java
    Object newArray = Array.newInstance(classObject, arrayLen);
    ```

- getType : getInstnace와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.

    ```java
    FileStore fs = Files.getFileStore(path)
    ```

- newType : newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.

    ```java
    BufferedReader br = Files.newBufferedReader(path);
    ```

- type : getType과 newType의 간결한 버전

    ```java
    List<Complaint> litany = Collections.list(legacyLitany);
    ```