// package contes.atoslocadora.models;

// public class ClienteDTO {
    
//     @Id
//     @GeneratedValue
//     private Long id;

//     @Column(name = "cpf", nullable = false)
//     private Long cpf;

//     @Column(name = "nome", nullable = false)
//     private String nome;

//     @Column(name = "cnh", nullable = false)
//     private String cnh;

//     @Column(name = "endereco", nullable = false)
//     private String endereco;

//     @Column(nullable = false)
//     @Type(type = "org.hibernate.type.NumericBooleanType")
//     private boolean ativo = true;

//     @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, orphanRemoval = true)
//     private List<Contrato> contratos = new ArrayList<>();
// }
