<h1>Projeto MaxClubCard</h1>

Rest Api com Java(17) e Spring para a campanha "Arquibancada Maxclubcard nas Olimpíadas" que consiste em cadastrar campanhas, cadastrar cliente na campanha e processar transações que poderão ser convertidas em números da sorte para que o cliente possa concorrer ao prêmio de um ingresso com acompanhando para assistir aos jogos olímpicos.

O aplicativo foi configurado para usar o docker para executar o MySql na porta 3306 e o RabbitMq na porta 5672.

<h2>O que foi construído</h2>
Foram construídos três microsserviços, que consistem em:
- Um serviço que é o server da aplicação, onde os demais serviços serão registrados;
- Um gateway que centraliza as requisições;
- Um serviço de Campanhas, que possibilita cadastrar campanha, cliente/cartão, e gerar sorteios;
- Um serviço de Transações para processamento de pagamentos.

<h2>Passo a passo para execução do projeto</h2>
- Acesse a pasta Arquibancada-Maxclubcard-nas-Olimpadas pelo prompt na linha de comando e execute o comando:  docker-compose up. Nesse momento irá criar o container com o mysql na porta 3306(user "root" e password "root") e rabbit na porta 5672(user "guest" e password "guest")

- Para executar os serviços pela linha de comando:
	- Acesse a pasta Arquibancada-Maxclubcard-nas-Olimpadas/server, abra outro terminal também e execute: mvn spring-boot:run
	- Repita isso para os serviços de Gateway, Transações e Campanhas.

A porta do servidor é 8082. A porta do gateway é a 8083. Os serviços "transacoes" e "campanhas" serão executados em portas dinamicas direcionadas pelo gateway.

Ao rodar os serviços, poderá acessar o endereço http://localhost:8082/ e verificar as instâncias do serviços executando no servidor.
Para acessar o endpoint dos serviços "transacoes" e "campanhas" é necessario apontar a rota para o gateway.
Transacoes: http://localhost:8083/transacoes-ms/
Campanhas: http://localhost:8083/campanha-ms/

Esses serviços estão documentados com o swagger. Para acessar:
Transacoes: http://localhost:8083/transacoes-ms/swagger-ui/index.html
Campanhas: http://localhost:8083/campanha-ms/swagger-ui/index.html

Obs: O serviço de "campanhas" possui testes. Para executá-los, após rodar o docker acesse a pasta /campanhas na linha de comando e execute "mvn test"

***<h3>Vídeo de execução do projetop</h3>***
Gravei um vídeo de execução, que pode ser visto em 
https://www.loom.com/share/8951c5d2f4a042afa3ffb1194d3f99bb?sid=0ae640af-a4f8-4601-9c19-1c2ecec41a30


Obs: Se houver qualquer problema com o vídeo acima, também salvei no drive, onde poderá ser feito o download em:
https://drive.google.com/file/d/1UV578aG8qI-ws1zWPLUfM_PdHuBSa-2V/view?usp=sharing


Obs: Por ter mais familiariadade com o swagger, optei por realizar os testes por lá. No entanto, também testei no postman e salvei algumas requisições, peço por favor que se atentem apenas as variáveis, salvei algumas na collection mas não tenho certeza se irão funcionar em outros ambientes. 
As requisições de autenticação/autorização não foi possível salvar na collection, mas podem ser testadas pelo swagger.


<h2>Como funciona</h2>

CAMPANHAS
http://localhost:8083/campanha-ms/

O serviço de campanhas possui um endpoint post /campanhas para gravar a campanha, que recebe um nome e um valor mínimo.
O endpoint /cliente/campanha/{id} cadastra um cliente em uma campanha, informando os dados do cliente manualmente.
Já o endpoint /cliente/campanha/{id}/arquivo irá buscar os dados da planilha massa-de-dados.xlsx, fazer a conversão das informações e gravar automaticamente os clientes e seus respectivos cartões contidos na planilha.

TRANSACOES
http://localhost:8083/transacoes-ms/

O serviço de transações possui um endpoint post /usuario que recebe uma informação de username e password e cria um usuário na aplicação, para que ele possa ter acesso às gravação de transações.

Após criar o usuário, deverá ser acessado o endpoint "usuario/login" informando o username e senha previamente cadastrados, e então será retornando o token de autenticação, para ser usado no endpoint de transacoes.

Obs: Foi criado no swagger um campo para enviar o token no serviço de transações, entretanto para os endpoints de usuario não é necessário, pois o usuário ainda não está logado, então esses endpoints estão livres para acesso. Já para o endpoint de transação "/transacao" será necessário enviar o token.

Ao acessar o endpoint post "/transacao" informando o token e os dados da transação(cpf/valor/numerocartao/tipo) será enviada uma mensagem para o serviço de CAMPANHAS através do rabbitmq. O serviço de campanhas irá gerar um número da sorte para o usuário, caso o valor da transação seja igual ou maior o valor mínimo da campanha.

RETORNANDO AO SERVIÇO DE CAMPANHAS..
Para realizar os sorteios, acesse o endpoint get "/sorteio/{id}/sorteio" informando o id da campanha a ser sorteada. Ele irá buscar os participantes de sorteios vinculados à campanha e irá retornar um registro aleatoriamente, retornando os dados de: numero da sorte, id da campanha, nome da campanha e cliente.

Obs: No serviço de campanhas, também existem endpoints para cadastrar manualmente um cliente e um cartão para um cliente, que foram especificados na documentação do swagger.