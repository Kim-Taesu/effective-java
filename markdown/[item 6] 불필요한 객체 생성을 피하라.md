## 불필요핞 객체 생성을 피하라
- 생성자 대신 정적 팩터리 메서드를 제공하는 불변 클래스에서는 정적 팩터리 메서드를 사용해 불필요한 객체 생성을 피할 수 있다.
  - 생성자는 호출할 때마다 새로운 객체를 만들지만
  - 패겉리 메서드는 전혀 그렇지 않다.
- 생성 비용이 아주 비싼 객체는 캐싱을 하여 재사용하길 권한다.
- 아래 예시는 String.matches 메서드를 사용한다.
  ```java
  static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3}(I[XV]|V?I{0,3})$")
  }
  ```
  - String.matches 는 정규 표현식으로 문자열 형태를 확인하는 가장 쉬운 방법이지만, 성능이 중요한 상황에서 반복해 사용하기엔 적합하지 않다.
  - 이 메서드가 내부에서 만드는 정규표현식용 Pattern 인스턴스는, 한 번 쓰고 버려져서 곧바로 가비지 컬렉션 대상이 된다.
    - Pattern은 입력받은 정규표현식에 해당하는 유한 상태 머신을 만들기 때문에 인스턴스 생성 비용이 높다.
  - 성능을 개선하려면 필요한 정규표현식을 표현하는 Pattern 인스턴스를 클래스 초기화 과정에서 직접 생성해 캐싱해두고, 나중에 메서드 호출될 때 마다 이 인스턴스를 재사용 한다.
  ```java
  public class RomanNumerals {
        private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3}(I[XV]|V?I{0,3})$");
        static boolean isRomanNumeral(String s) {
            return ROMAN.matcher(s).matches();
        }
  }
  ```
  - 클래스가 초기화된 후 이 메서드를 한 번도 호출하지 않는다면 ROMAN 필드는 쓸데없이 초기화된 꼴이다.
    - 지연 초기화로 불필요한 초기화를 없앨 수는 있지만, 권하지는 않는다.
    - 지연 초기ㅗ하는 코드를 복잡하게 만드는데, 성능은 크게 개선되지 않을 때가 많기 때문이다.
- 오토방식은 프로그래머가 기본 타입과 박싱된 기본 타입을 섞어 쓸 때 자동으로 상호 변환해주는 기술이다.
  - 오토박싱은 기본 타입과 그에 대응하는 박싱된 기본 타입의 구분을 흐려주지만, 완전히 없애주는 것은 아니다.
  ```java
  private static long sum() {
    Long sum = 0L;
    for (long i = 0; i <= Integer.MAX_VALUE; i++)
        sum += i;
    return sum;
  }
  ```
  - sum 변수를 Long 으로 선언해서 불필요한 Long 인스턴스가 약 231만개나 만들어진 것이다.
    - sum의 타입을 long으로만 바꿔주면 성능이 향상된다.
  - 박싱된 기본 타입보다는 기본 타입을 사용하고, 의도치 않은 오토박싱이 숨어들지 않도록 주의하자
- 객체 생성 비용이 비싼 경우는 객체 Pool을 사용하자
  - ex) 데이터베이스 연결