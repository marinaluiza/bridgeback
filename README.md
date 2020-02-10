Backend para carregar dados de repositórios da API pública do gitHub.
Foi utilizado Maven para constrolar o ciclo de construção, Spring Boot para criar a aplicação usando basicamente RequestMapper e 
Jackson Json para ler os dados.
É preciso ter Maven e JVM instalados.
Para rodar localmente basta clonar o projeto, rodar o script "package" do Maven ($ mvn clean dependency:copy-dependencies package) e depois rodar a classe Main.java ($ java Main).

