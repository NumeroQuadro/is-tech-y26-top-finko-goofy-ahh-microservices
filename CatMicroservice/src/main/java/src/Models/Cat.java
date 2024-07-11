package src.Models;

import WrappedModels.CatColor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cats_main_info")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id", nullable = false)
    private Integer id;
    @Column(name = "cat_name", unique = true)
    private String name;
    @Column(name = "cat_birthday", nullable = false)
    private LocalDate birthDate;
    @Column(name = "cat_breed", nullable = false)
    private String breed;
    @Column(name = "cat_color" ,nullable = false)
    @Enumerated(EnumType.STRING)
    private CatColor color;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cats_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<Cat> friends = new HashSet<>();

    public void addFriend(Cat cat) {
        friends.add(cat);
        cat.getFriends().add(this);
    }
}
