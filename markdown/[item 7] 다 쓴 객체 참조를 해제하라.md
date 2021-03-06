## 다 쓴 객체 참조를 해제하라
- Java처럼 가바지 컬렉터를 갖춘 언어로 넘어오면 메모리 관리에 더 이상 신경쓰지 않아도 된다고 오해할 수 있는데, 절대 사실이 아니다.

```java
public class Stack {
	private Object[] elements;
	private int size = 0;
	private static final int CAPACITY = 16;

	public Stack(Object[] elements) {
		this.elements = new Object[CAPACITY];
	}

	public void push(Object object) {
		ensureCapacity();
		elements[size++] = object;
	}

	public Object pop() {
		if (size == 0)
			throw new EmptyStackException();
		return elements[--size];
	}

	private void ensureCapacity() {
		if (elements.length == size) {
			elements = Arrays.copyOf(elements, 2 * size + 1);
		}
	}
}
```
- 위 코드는 특별한 문제가 없어보이지만 프로그램을 오래 사용하다보면 점차 가비지 컬렉션 활동과 메모리 사용량이 늘어나 결국 성능이 저하된다.
- 위 코드에서는 스택이 커졌다가 줄어들었을 때 스택에서 꺼내진 객체들을 가비지 컬렉터가 회수핮 ㅣㅇ낳는다.
  - 이 스택이 그 객체들의 다 쓴 참조를 여전히 가지고 있기 때문이다.
  - 다 쓴 참조란 앞으로 다시 쓰지 않을 참조를 뜻한다.

### 가비지 컬렉션 언어에서는 메모리 누수를 찾기가 아주 까다롭다.
- 객체 참조 하나를 살려두면 가비지 컬렉터는 그 객체뿐 아니라 그 객체가 참조하는 모든 객체를 회수해가지 못한다.
- 그래서 단 몇 개의 객체가 매우 많은 객체를 회수되지 못하게 할 수 있고 잠재적으로 성능에 악영향을 줄 수 있다.
- 해법은 해당 참조를 다 썼을 때 null 처리 하면 된다.
```java
public Object pop(){
	if (size == 0)
		throw new EmptyStackException();
	Object result = elements[--size];
	elements[size] = null; // 다 쓴 참조 해제
    return result;
}
```
- 다 쓴 참조를 null 처리하면 다른 이점도 따라온다.
- 만약 null 처리한 참조를 실수로 사용하려 하면 NPE를 던지며 종료된다.
- 하지만 객체 참조를 null 처리하는 일은 예외적인 경우여야 한다.
- 다 쓴 참조를 해제하는 가장 좋은 방법은 그 참조를 담은 변수를 유효 범위(scope) 밖으로 밀어내는 것이다.

### null 처리는 언제 해야 할까?
- 위 Stack 클래스가 메모리 누수에 취약한 이유는 자기 메모리를 직접 관리하기 때문이다.
  - elements 배열로 저장소 풀을 만들어 원소들을 관리한다.
  - 배열을 활성 영역에 속한 원소들이 사용되고 비활성 영역은 쓰이지 않는다.
- 문제는 가비지 컬렉터는 이 사실을 알 길이 없다는 데 있다.
  - 가비지 컬렉터가 보기에는 비활성 영역에서 참조하는 객체도 똑같이 유효한 객체다.
  - 그러므로 프로그래머는 비활성 영역이 되는 순간 null 처리해서 해당 객체를 더는 쓰지 않을 것임을 가비지 컬렉터에 알려야 한다.
- 일반적으로 자기 메모리를 직접 관리하는 클래스라면 프로그래머는 항시 메모리 누수에 주의해야 한다.

### 캐시 역시 메모리 누수를 일으키는 주범이다.
- 객체 참조를 캐시에 넣고 나서, 이 사실을 잊은 채 그 객체를 다 쓴 뒤로도 한참을 그냥 놔두는 일을 자주 접할 수 있다.
- 해법은 여러 가지다.
  - 운 좋게 캐시 외부에서 키를 참조하는 동안만 엔트리가 살아 있는 캐시가 필요한 상황이라면 WekHashMap을 사용해 캐시를 만들자
     - 단 WeakHashMap은 이러한 상황에서만 유용하다.
  - 캐시를 만들 때 모통은 캐시 엔트리의 유효 기간을 정확히 정의하기 어렵기 때문에 시간이 지날수록 엔트리의 가치를 떨어뜨리는 방식을 흔히 사용한다.
    - 이런 방식에서는 쓰지 않는 엔트리를 이따금 청소해줘야 한다. (ScheduledThreadPoolExecutor)
  - 백그라운드 스레드를 활용하거나 캐시에 새 엔트리를 추가할 때 부수 작업으로 수행하는 방법이 있다.
    - LinkedHashMap은 removeEldestEntry 메서드를 써서 후자의 방식으로 처리한다.

## 메모리 누수으 ㅣ세 번째 주범은 바로 listener 혹은 callback 이다.
- 클라이언트가 콜백을 등록만 하고 명확히 해지하지 않는다면, 뭔가 조치해쥦 않는 한 콜백은 계속 쌓여갈 것이다.
- 이럴 때 콜백을 약한 참조로 저장하면 가비지 컬렉터가 즉시 수거해간다.
  - 예를 들어 WeakHashMap에 키로 저장하면 된다.