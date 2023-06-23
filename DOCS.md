## Documentação 
O projeto "Lista de Contatos" é uma aplicação Java que permite gerenciar uma lista de contatos. Ele oferece funcionalidades como adicionar, remover, editar, pesquisar e visualizar detalhes de contatos.

## Estrutura do Projeto

```
├── pom.xml
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           ├── App.java
│   │           └── projeto
│   │               ├── dao
│   │               │   ├── ContatoDAO.java
│   │               │   └── DAOException.java
│   │               ├── model
│   │               │   ├── ComboColor.java
│   │               │   └── Contato.java
│   │               ├── service
│   │               │   └── ContatoService.java
│   │               └── ui
│   │                   ├── TelaCadastroContato.java
│   │                   └── TelaPrincipal.java
│   └── test
│       └── java
└── target
    ├── archive-tmp
    ├── classes
    │   ├── App.class
    │   └── com
    │       └── projeto
    │           ├── dao
    │           │   ├── ContatoDAO.class
    │           │   └── DAOException.class
    │           ├── model
    │           │   ├── ComboColor.class
    │           │   └── Contato.class
    │           ├── service
    │           │   └── ContatoService.class
    │           └── ui
    │               ├── TelaCadastroContato$1.class
    │               ├── TelaCadastroContato.class
    │               ├── TelaPrincipal$1.class
    │               ├── TelaPrincipal$2.class
    │               ├── TelaPrincipal$3.class
    │               ├── TelaPrincipal$4.class
    │               ├── TelaPrincipal$5.class
    │               └── TelaPrincipal.class
    ├── generated-sources
    │   └── annotations
    ├── ListaDeContatos-1.0-SNAPSHOT.jar
    ├── ListaDeContatos-1.0-SNAPSHOT-jar-with-dependencies.jar
    ├── maven-archiver
    │   └── pom.properties
    └── maven-status
        └── maven-compiler-plugin
            ├── compile
            │   └── default-compile
            │       ├── createdFiles.lst
            │       └── inputFiles.lst
            └── testCompile
                └── default-testCompile
                    └── inputFiles.lst

30 directories, 29 files
```

## Classes e suas Funções

### App.java
- Classe principal do projeto que contém o método `main` para iniciar a aplicação.

### dao.ContatoDAO.java
- Responsável por realizar operações de acesso ao banco de dados para a entidade Contato.
- Fornece métodos para inserir, atualizar, excluir e recuperar contatos do banco de dados.

### dao.DAOException.java
- Classe de exceção personalizada para lidar com erros específicos do DAO (Data Access Object).

### model.Contato.java
- Representa a entidade Contato com seus atributos, como id, nome, telefone,

 email, favorite e color.
- Possui métodos getter e setter para cada atributo.

### model.ComboColor.java
- Representa uma opção de cor para a seleção de cores dos contatos.

### service.ContatoService.java
- Camada de serviço responsável por fornecer métodos para manipulação de contatos.
- Utiliza o ContatoDAO para acessar o banco de dados e executar operações CRUD.

### ui.TelaPrincipal.java
- Interface gráfica principal da aplicação.
- Exibe a lista de contatos em uma tabela, permite adicionar, editar, excluir e pesquisar contatos.
- Implementa os componentes e eventos da interface de usuário.

### ui.TelaCadastroContato.java
- Interface gráfica para adicionar ou editar um contato.
- Exibe um formulário para inserir ou modificar os dados do contato, incluindo nome, telefone, email, cor favorita e marcador de favorito.

## Conclusão
Essa documentação fornece uma visão geral das classes e suas responsabilidades no projeto "Lista de Contatos". Cada classe desempenha um papel específico na funcionalidade global do sistema. Utilizando essa documentação, você terá uma compreensão clara da estrutura e organização do projeto.
