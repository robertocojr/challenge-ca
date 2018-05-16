# Boleto Facil

O objetivo dessa api e disponibilizar uma maneira simples e segura para cuidar de toda a gestão de boletos de microempresas.

# Features
* armazenar boletos;
* atualizar seu status (PENDING, PAID, CANCELED);
* listar todos;
* buscar por boleto com valor atualizado.

# Requisitos
* Java 8 (http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html);

# Instalação

<code>./gradlew clean build</code>

# Execução

<code>./gradlew bootRun</code>

# Ambiente de testes
O projeto está integrado com o heroku (https://challenge-roberto.herokuapp.com). Para utilizar o Postman baixe a collection (https://github.com/robertocojr/challenge-ca/blob/master/challenge-ca%20heroku.postman_collection.json)

* findAll: GET - https://challenge-roberto.herokuapp.com/rest/bankslips
* findById: GET - https://challenge-roberto.herokuapp.com/rest/bankslips/{id}
* add: POST - https://challenge-roberto.herokuapp.com/rest/bankslips
<br><code>{
 "due_date":"2018-07-01",
 "total_in_cents":"54213541",
 "customer":"José Silva",
 "status":"PENDING"
}</code>
* update: PUT - https://challenge-roberto.herokuapp.com/rest/bankslips/5fa9c313-c286-402f-8646-5688970c574c
<br><code>{
 "status":"PAID"
}</code>
