# DAY4 JAVA+SQL

1. 자바 프로그램에서 DB 저장 - 조회 기능 = JDBC

   JAVA DATABASE CONNECTIVITY

   java.sql 패키지의 api

   java.lang

   java.util

   java.io

   oracle jdbc driver / java.sql 패키지의 인터페이스를 상속받아 오라클 라이브러리 구현 제공 클래스 모임 / db제공회사가 배포하는 것들

### java와 db 연결

| 0.DB 독립적<br />1. DB연결 ( DB종류, ip, port, 계정, 암호)<br />2. sql 작성 - 전송<br />3. sql 실행 결과 이용<br />4. DB연결 해제 |
| ------------------------------------------------------------ |

* jdbc driver 호출

  Class.forName("Oracle.jdbc.driver.OracleDriver");

* java.sql.Connection 생성해서 db 연결

  Connection con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "hr", "hr");

* jdbc를 통해 sql 전송

  ddl은 가능하나 jdbc를 통해 dll을 사용하는건 권장하지 않는다. db 내부에서 해라

  dcl또한 가능하나 권장하지 않는다. system 계정은 db내부에서 해라

  dml ,select ,tcl 사용 할 거임
  
  String sql = "insert into emp"+
  					" select employee_id, first_name, department_id, salary, job_id, hire_date" +
  					" from employees where employee_id > 200";
  
* db연결 해제

  con.close();

### insert 만들기

```java
public static void main(String[] args) {
    try {
        //jdbc driver 호출
        Class.forName("oracle.jdbd.driver.OracleDriver");
        //db연결
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe",
                                                     "hr", "hr");
        //insert sql작성
        String sql = "insert into emp"+
					" select employee_id, first_name, department_id, salary, job_id, hire_date" +
					" from employees where employee_id > 200";
        //DB에게 명령문 만들기
        Statement st = con.createStatement();
        //sql 명령문에 저장, 전송(update)
        int row = st.executeUpdate(sql);
        System.out.println(row + " 개의 행 삽입되었습니다.");
        //db연결 해제
        con.close();
        
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLEXception e) {
        e.printStackTrace();
    } finally {
        try {
        	if(con.isClosed() == false) {
                con.close();
            }    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
```

### update 만들기

```java
public static void main(String[] args) {
    connection con = null;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "hr","hr");
        
        String sql = "update emp set title = '미정' where salary <= 30000";
        
        Statement st = con.createStatement;
        int row = st.executeUpdate(sql);
    }
}
```

### select 만들기

```java
public static void main(String[] args) {
    Connection con = null;
    try{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "hr","hr");
        
        String sql = "select id, name, title from emp";
        
        Statement st = con.createStatement;
        Result rs = st.executeQuery(sql);
        
        while(re.next()) {
            int id = rs.getInt(1);
            String name = rs.getString("name");
            String title = rs.getString("title");
            s.o.p(id + "\t" + name + "\t" + title + "\t");
        }
        con.close();
    }
}
```



### sql 전송

* insert / update / delete => db 실행결과 리턴 (레코드 수)
* Statement st = con.createStatement();
* int rows = st.executeUpdate(" dml(insert문|update|delet)");

 => dml 실행 변화 행 개수



select - jdbc sql

(dml - 실행 db 변화를 줌, 행의 수 변화함)

(ddl - 실행 db 변화를 줌, 행의 수가 변화를 할지 안할지는?)
create table a (b number(5)); => 행의 수 변화 x

create table a as select * from emp;  => 행의 수 변화 o





### ResultSet

(조회 - db에 변화를 안줌)

Statement st = con.createStatement();

조회 결과 테이블이 나옴 <= ResultSet rs = st.executeQuery("select")



java.sql.Connection 

java.sql.Statement = new Statement(); x  con.createStatement(); o

java.sql.ResultSet => select 결과 테이블 조회한거 저장



ResultSet의 결과는 어떻게 봐야할까

select id, name, salary from emp를 실행했다면 

rs.next(); ==> rs 의 첫번 째 레코드 이동 true 리턴

rs.next(); ==> rs 의 두번 째 레코드 이동 true 리턴

rs.next(); ==> rs 의 세번 째 레코드 이동 true 리턴

rs.next(); ==> rs 의 네번 째 레코드 이동 false 리턴

while(rs.next()) {

int id = rs.getint(1); => rs.gitInt("id");

String id

rs.getString(!); 스트링으로 해석

Sring name = rs.getString,,

duble salary - rs.getCouble(:salary;)



result 메소드

| 자바          | resultStet                                | 오라클                                                       |
| ------------- | ----------------------------------------- | ------------------------------------------------------------ |
| int           | getInt("컬럼명")                          | number(n)                                                    |
| double        | getDouble                                 | number(n, s)                                                 |
| String        | getString                                 | Varchar2                                                     |
| java.sql.Date | getDate("컬럼명")<br />getSring("indate") | date 'rr/mm/dd'<br /> 날짜데이터 select to_char (날짜데이터, 'mm,'m) |

```java
Statement st = con.createStatement();
ResultSet rs = st.executeQuery(sql);

while(rs.next()) {
    int id = rs.getInt(1);
    String name = rs.getString("name");
}
```



![image-20210817155135112](../md-images/image-20210817155135112.png)

emp 테이블 -> ArrayList<EmpVO>나 배열 타입으로

레코드

컬럼을 변수로



class EmpVO{  => 레코드 1개 표현하는 객체

int id; => 컬럼변수



클래스명에서 XXXVO => value object 값을 저장하고 추출하는 용도의 객체

​						emp테이블=>EmpVO // EmpDO = data object // EmpDTO = data transfer object

​						VO에서 rs에서 값을 저장하고있다가 list.add로 전송

​					XXXDAO => data access object 값에 접근하는 용도의 객체

​						db에 있는 값을 자바로 가져와야하니 jdbc를 짜야함

​						파일에 있는 값을 자바로 가져와야하니 파일 입력(java.io.FileReader) 를 짜야함