# EducaGames MVP

Portal web inicial para jogos educacionais gamificados, feito com Java, Spring Boot, Maven, Spring Security, JWT, JPA/Hibernate e H2.

## O que foi criado

- Cadastro, login e logout client-side com JWT.
- Senhas com hash BCrypt.
- Perfil do jogador com pontuacao total e nivel calculado.
- Catalogo de jogos ativo/inativo.
- Estrutura para jogos estaticos em `src/main/resources/static/games/{slug}`.
- SDK JavaScript reutilizavel em `src/main/resources/static/js/game-sdk.js`.
- Envio de pontuacao protegido em `POST /api/scores/submit`.
- Historico de partidas em `ScoreRecord`.
- Ranking geral ordenado por pontuacao total.
- Seeds iniciais com usuario demo e 2 jogos placeholder.

## Stack

- Java 17
- Spring Boot 3.3.5
- Maven
- Spring Security
- JWT com `jjwt`
- JPA/Hibernate
- H2 em memoria, configurado com modo PostgreSQL para facilitar migracao futura
- HTML, CSS e JavaScript estaticos servidos pelo Spring Boot

## Como rodar

Instale Maven e execute:

```bash
mvn spring-boot:run
```

Depois acesse:

- Aplicacao: `http://localhost:8080`
- Console H2: `http://localhost:8080/h2-console`

Dados do H2:

- JDBC URL: `jdbc:h2:mem:educagames`
- User: `sa`
- Password: vazio

Conta demo:

- Username: `demo`
- E-mail: `demo@educagames.local`
- Senha: `123456`

## Endpoints principais

Autenticacao:

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/logout`

Usuario:

- `GET /api/users/me`
- `PUT /api/users/me`

Jogos:

- `GET /api/games`
- `GET /api/games/{slug}`

Pontuacao:

- `POST /api/scores/submit`

Ranking:

- `GET /api/ranking`

As rotas de API, exceto autenticacao, exigem header:

```http
Authorization: Bearer SEU_TOKEN
```

## Estrutura de pastas

```text
src/main/java/br/com/mvp/educagames
  config
  controller
  dto
  entity
  exception
  repository
  security
  service

src/main/resources/static
  css
  img/covers
  js
    api.js
    game-sdk.js
  games
    quiz-historia
      index.html
      style.css
      script.js
    matematica-relampago
      index.html
      style.css
      script.js
```

## Como adicionar um novo jogo

1. Crie uma pasta para o jogo:

```text
src/main/resources/static/games/meu-jogo/index.html
src/main/resources/static/games/meu-jogo/style.css
src/main/resources/static/games/meu-jogo/script.js
```

2. Cadastre o jogo no banco. Para desenvolvimento, adicione um novo item em `DataSeeder`:

```java
gameRepository.save(new Game(
    "Meu Jogo",
    "meu-jogo",
    "Descricao curta do jogo.",
    "Categoria",
    Difficulty.EASY,
    "/img/covers/minha-capa.svg",
    "/games/meu-jogo/index.html",
    100,
    true
));
```

3. No `index.html` do jogo, importe o SDK:

```html
<script src="/js/game-sdk.js"></script>
<script src="./script.js"></script>
```

## Como um jogo envia pontuacao

Ao finalizar a partida, chame:

```javascript
await GameSDK.submitScore({
  gameSlug: "meu-jogo",
  score: 80
});
```

O backend valida:

- usuario autenticado por JWT;
- jogo existente e ativo;
- pontuacao entre `0` e a pontuacao maxima do jogo.

Depois salva `ScoreRecord`, soma no total do usuario e o ranking passa a refletir o novo total.

## Proximos passos recomendados

- Trocar o secret JWT de desenvolvimento por variavel de ambiente.
- Criar migrations com Flyway ou Liquibase antes de migrar para PostgreSQL.
- Adicionar tela administrativa simples para cadastrar jogos sem mexer no seed.
- Criar testes de integracao para autenticacao, pontuacao e ranking.
- Definir padrao de empacotamento dos jogos quando a equipe comecar a publicar jogos reais.
