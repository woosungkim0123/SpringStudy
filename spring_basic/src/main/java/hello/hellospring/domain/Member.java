package hello.hellospring.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entity => jpa가 관리하는 엔티티, pk 매핑(Id)
// db가 알아서 id를 만들어주는 것 => identity 전략이라 부름
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 만약 db에 칼럼이름이 username이면 @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
