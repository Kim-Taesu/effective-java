## finalizer와 cleaner 사용을 피하라
- 자바는 두 가지 객체 소멸자를 제공한다.
  - 그중 finalizer는 예측할 수 없고, 상황에 따라 위험할 수 있어 일반적으로 불필요하다.
    - 오동작, 낮은 성능, 이식성 문제의 원인이 되기도 한다.
  - cleaner는 finalizer보다는 덜 위험하지만, 여전히 예측할 수 없고, 느리고, 일반적으로 불필요하다.
- finalizer와 cleaner는 즉시 수행된다는 보장이 없다.
  - 즉 finalizer와 cleaner에 맡기면 중대한 오류를 일으킬 수 있다.
  - 시스템이 동시에 열 수 있는 파일 개수에 한계가 있기 때문이다.
  - 시스템이 finalizer나 cleaner 실행을 게을리해서 파일을 계속 열어 둔다면 새로운 파일을 열지 못해 프로그램이 실패할 수 있다.
- finalizer나 cleaner를 얼마나 신속히 수행할지는 전적으로 가비지 컬렉터 알고리즘에 달렸으며, 이는 가비지 컬렉터 구현마다 천차만별이다.
- 클래스에 finalizer를 달아두면 그 인스턴스의 자원 회수가 제멋대로 지연될 수 있다.
  - finalizer 스레드가 다른 애플리케이션 스레드보다 우선순위가 낮아서 실행될 기회를 제대로 얻지 못해 에러가 발생할 수 있다.
- 자바 언어 명세는 finalizer나 cleaner의 수행 시점뿐 아니라 수행 여부조차 보장하지 않기 때문에, 상태를 영구적으로 수정하는 작업에서는 절대 finalizer나 cleaner에 의존해서는 안된다.
- finalizer와 cleaner는 심각한 성능 문제도 동반한다.
  - AutoCloseable 객체보다 50배 느리다.
- finalizer를 사용한 클래스는 finalizer 공격에 노출되어 심각한 보안 문제를 일으킬 수도 있다.
  - final이 아닌 클래스를 finalizer 공격으로부터 방어하려면 아무 일도 하지 않는 finalize 메서드를 만들고 final로 선언하자

### finalizer나 cleaner를 대신해줄 묘안
- AutoCloaseable 을 구현해주고, 클라이언트에서 인스턴스를 다 쓰고 나면 close 메서드를 호출하면 된다.
  - 일반적으로 예외가 발생해도 제대로 종료되도록 try-with-resources를 사용해야 한다.

### finalizer와 cleaner의 사용처
1. 자원의 소유자가 close 메서드를 호출하지 않는 것에 대한 안전망 역할
   - ex) FileInputStream, FileOutputStream, ThreadPoolExecutor
2. 네이티브 피어와 연결된 객체
   - 네이티브 피어란 일반 자바 객체가 네이티브 메서드를 통해 기능을 위임한 네이티브 객체를 말한다.
   - 네이티브 피어는 자바 객체가 아니니 가비지 컬렉터를 그 존재를 알지 못한다.