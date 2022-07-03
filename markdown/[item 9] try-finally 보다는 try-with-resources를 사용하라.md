## try-finally 보다는 try-with-resources를 사용하라
- try-finally 문의 맹점
  - 예외는 try 블록과 finally 블록 모두에서 발생할 수 있는데, 여러 자원을 try-finally로 구현하는 경우 순서에 따라 특정 예외에 대한 정보는 남지 않을 수 있다.
    - 정보가 아예 없기 때문에 디버깅을 옵시 어렵게 한다.
- try-with-resources 를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야 한다.
  - 단순히 void를 반환하는 close 메서드 하나만 덩그러니 정의한 인터페이스이다.
  - 만약 닫아야 하는 자원을 뜻하는 클래스를 작성한다면 AutoCloseable을 반드시 구현하기 바란다.