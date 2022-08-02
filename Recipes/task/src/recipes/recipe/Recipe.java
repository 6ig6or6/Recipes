package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;
import recipes.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="recipes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    @NotBlank
    @Column
    private String name;
    @NotBlank
    @Column
    private String category;
    @Nullable
    @Column
    private LocalDateTime date;
    @NotBlank
    @Column
    private String description;
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "INGREDIENTS", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "ingredients")
    private List<String> ingredients = new ArrayList<>();
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    @CollectionTable(name = "DIRECTIONS", joinColumns = @JoinColumn(name = "ID"))
    @Column(name = "directions")
    private List<String> directions = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
