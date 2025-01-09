package academy.devdojo.domain;


import lombok.*;

@With
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Include
    //  @Id
    //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //   @Column(nullable = false)
    private String firstName;
    // @Column(nullable = false)
    private String lastName;

   // @Column(nullable = false, unique = true)
    private String email;

}
