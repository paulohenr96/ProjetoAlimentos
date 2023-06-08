# Sistema de Controle de Alimentos e Consumo Calórico

Este sistema web permite cadastrar alimentos, criar refeições e dietas utilizando os alimentos cadastrados, além de registrar o consumo calórico diário. Também é possível visualizar o histórico de consumo diário com gráficos.

## Funcionalidades

- Cadastro de alimentos
- Criação de refeições e dietas com os alimentos cadastrados
- Registro de consumo calórico diário
- Visualização do histórico de consumo diário com gráficos

## Estrutura do Projeto

O projeto está organizado em vários pacotes, cada um com uma responsabilidade específica:

- `controller`: Contém os Servlets responsáveis por lidar com as requisições e controlar o fluxo da aplicação. Os Servlets existentes são:
  - ServletAlimento: Lida com operações relacionadas a alimentos.
  - ServletCadastro: Responsável pelo cadastro de usuários.
  - ServletDieta: Gerencia as operações de criação e gerenciamento de dietas.
  - ServletLogin: Controla o login e autenticação de usuários.
  - ServletSenha: Gerencia a recuperação e alteração de senhas.

- `bean`: Contém a classe "ContextoBean" que possui códigos compartilhados entre os Servlets. Todos os Servlets extendem essa classe para evitar repetição de código.

- `beanDTO`: Contém a classe responsável por mostrar o gráfico no front-end.

- `model`: Contém as classes que representam as entidades do sistema. As classes existentes são:
  - ModelAlimento: Representa um alimento cadastrado.
  - ModelAlimentoConsumido: Representa um alimento consumido em um determinado dia.
  - ModelAlimentoRefeicao: Representa um alimento que está cadastrado em alguma refeição.
  - ModelConsumidoDia: Representa o consumo calórico diário.
  - ModelDieta: Representa uma dieta criada pelo usuário.
  - ModelRefeicao: Representa uma refeição cadastrada pelo usuário.
  - ModelUsuario: Representa um usuário do sistema.

- `dao`: Contém as classes responsáveis pelo acesso aos dados. Cada entidade possui uma classe DAO correspondente. As classes DAO existentes são:
  - DAOAlimento: Responsável pelas operações relacionadas a alimentos.
  - DAOAlimentoConsumido: Responsável pelas operações relacionadas ao consumo de alimentos em um determinado dia.
  - DAOConsumido: Responsável pelas operações relacionadas ao consumo calórico diário.
  - DAODieta: Responsável pelas operações relacionadas a dietas.
  - DAOGeneric: Classe genérica que contém métodos mais gerais utilizados pelos outros DAOs.
  - DAORefeicao: Responsável pelas operações relacionadas a refeições.
  - DAOUsuario: Responsável pelas operações relacionadas a usuários.

- `filtro`: Contém o filtro FiltroLogin que verifica se o usuário está logado ou não.

- `util`: Contém classes utilitárias do sistema. As classes existentes são:
  - JPAUtil: Classe responsável por iniciar a conexão com o banco de dados utilizando JPA.
  - Mensagem: Classe com constantes utilizadas no código.
  - ReportUtil: Classe responsável por gerar relatórios com o JasperReport.

## Tecnologias Utilizadas

- Linguagens: Java, HTML, CSS, JavaScript, jQuery.
- Frameworks e bibliotecas: Servlets, JSP, Bootstrap.

## Configuração e Execução

Para executar o projeto, siga as seguintes etapas:

1. Faça o clone do repositório para sua máquina.
2. Importe o projeto em sua IDE de preferência.
3. Configure as dependências necessárias, como o servidor Tomcat e o banco de dados.
4. Execute o servidor Tomcat e implante o projeto.
5. Acesse a aplicação pelo navegador utilizando a URL fornecida pelo servidor.

## Contribuição

Contribuições para o projeto são sempre bem-vindas. Se você encontrar algum problema ou tiver alguma sugestão de melhoria, sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para obter mais informações.
