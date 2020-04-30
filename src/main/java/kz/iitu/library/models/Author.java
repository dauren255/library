package kz.iitu.library.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@NoArgsConstructor
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "This author id")
    private Long id;
    @ApiModelProperty(notes = "This author name")
    private String name;
    public Author(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Book> books;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}
