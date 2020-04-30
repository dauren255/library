package kz.iitu.library.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "This genre id")
    private Long id;
    @ApiModelProperty(notes = "This genre name")
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Genre(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
