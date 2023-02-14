### LOGISTICA[^1]
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/amarojc/logistica/blob/main/LICENSE.md) 

# Sobre o projeto

O projeto logistica consiste em uma simples API de controle de entregas.

Um ou mais clientes podem solicitar quantas entregas desejarem e mencionar para qual destinatário será
realizado a entrega.<br>
Para cada entrega é cobrada uma taxa de serviço.<br>


A entrega possui possui 3 status, sendo: **PENDENTE**, **FINALIZADA** e **CANCELADA**.

**PENDENTE**: Quando um novo pedido de entrega é solicitado. <br>
**FINALIZADA**: Quando a entrega chega com sucesso ao seu destinatário. <br>
**CANCELADA**: Quando solicitado o cancelamento. <br><br>
Obsevação: Somente entregas PENDENTES podem ser FINALIZADAS ou CANCELADAS.<br>

Sempre que uma entrega sofrer uma mudança é registrado uma nova ocorrência, exemplo:
<br>
**Tabela Ocorrência**
id | entrega_id | descricao  | data_registro
-- | -----------| ---------- | -------------
1 | 11|Entrega Registrada					|2023-02-10 00:01:12
2 |	11|Entrega a caminho do destinatário	|2023-02-10 00:32:35
3 |	11|Tentativa de entrega não sucedida.	|2023-02-10 00:37:36
4 |	11|Entrega realizada com sucesso!		|2023-02-10 00:56:53
6 |	3 |Entrega cancelada.					|2023-02-10 13:21:42
 
#
### Modelo Conceitual
![DC](https://github.com/amarojc/logistica/blob/main/assets/diagrama-de-classes-logistica.png)

#
### Diagrama de Entidade e Relacionamento (DER)
![DER](https://github.com/amarojc/logistica/blob/main/assets/DER-Logistica.png)

#
### Tecnologias e versões utilizadas
- Java 17
- Spring Boot 2.7.7
- MySQL 8.0.31
- Docker 4.15.0
- Kubernetes 1.25.2

#
### Banco de dados

A aplicação cria automáticamente as tabelas no banco de dados quando são executadas pela primeira vez.<br>
A aplicação se comunica com o banco de dados algalog.

Caso queira alterar o nome do banco de dados, altere o arquivo application.properties do projeto.<br>
Utilizando o docker compose, o banco de dados também é criado automaticamente.

#
### Docker[^2] 

<br>

> **Criação do contêineres**

Utilizado o plugin spotify para gerar a imagem do Docker. Necessário habilitá-lo para que o mesmo possa ser utilizado nos projetos.<br><br>
Acesse a pasta do maven, no seu diretório, e procure pelo arquivo **settings.xml** . (tente em maven/conf/)<br>
Dentro de &lt;settings&gt; &lt;pluginGroups&gt; adicione a tag:<br><br>
```<pluginGroup>com.spotify</pluginGroup>```<br><br>
As configurações de criação da imagem esta no arquivo Dockerfile da aplicação.<br>
Para construir a imagem do Docker, basta executar os comandos direto do Maven na raiz:<br>
Você pode realizar via cmd ou abrir um terminal através da sua IDE, na pasta aonde se encontra o arquivo logistica-api<br>
exemplo: c:/projeto/logistica-api
<br>

> **Gerando o arquivo .jar do projeto**

```mvn clean install```

<br>

> **Gerando a imagem do Docker**

```mvn dockerfile:build```

<br>

> **Gerando um contêiner usando a imagem do mysql**

(Caso não tenha a imagem o Docker irá baixar direto do DockerHub (<https://hub.docker.com/_/mysql>)
e irá instalar direto no Docker local.<br><br>
```docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.31```

<br>

> **Executando os serviços em um contêiner Docker**

Sendo utilizado o docker-compose para inicializar todos os serviços de uma só vez.<br><br>
Abra um terminal e navegue até o caminho no diretório onde o arquivo foi criado e execute o comando: <br><br>
Na primeira vez que o comando for executado, o Docker irá criar o containers dentro do package logistica e logo após dar o start na aplicação.<br><br>
```docker-compose up```<br><br>
Parando os serviços<br><br>
```docker-compose down```

#
### Kubernetes[^3]

 <br>

> **Criação do Cluster**

Criando os elementos mysql-configuration no Cluster:<br>
Os arquivos de configurações estão no arquivo logistica/backend/**mysql-configuration**


Elementos | Comando | Função
--------- | ------ | --------
Deployment |  ```kubectl create -f mysql-deployment.yaml```  | Especificando como a máquina virtual deve ser criada. Definindo qual a imagem a ser utilizada, porta, recursos de máquinas e outras propriedades.
Services |  ```kubectl create -f mysql-service.yaml```  |  Um serviço é uma abstração que define um conjunto lógico de pods e uma política para acessá-los. Os Pods recebem um IP durante a sua criação e caso um Pod de um Deployment for reiniciado, o IP poderá mudar. Expondo o(s) Pod(s) em um endereço que não será alterado. 
Config Map |  ```kubectl create -f config-map.yaml```  |  Os Config Maps são formas de armazenar configurações necessárias para as aplicações de maneira simples dentro do cluster. No config-map criado estou armazenar os dados para a conexão com o MySQL.
User | ```kubectl create -f cluster-user.yaml```  |  Criando uma conta de serviço para o usuário com acesso total ao cluster. Atenção: Está ação não é uma boa prática para um cluster em produção, ela facilita bastante o gerenciamente do cluster em um ambiente de desenvolvimento.
Ingress | ```kubectl create -f logistica-ingress.yaml``` |  O Ingress expõe rotas HTTP e HTTPS de fora do cluster para serviços dentro do cluster. O Ingress redireciona um acesso ao cluster para o Service de uma aplicação. É uma forma de executar os serviços no cluster como se eles estivessem rodando normalmente em nossa máquina.


Você pode verificar se o Ingress foi corretamente criado  utilizando o comando: <br><br>
 ```kubectl get ingress . ```

<br>

> **Criando os elementos da API no Cluster:**

Os arquivos de configurações estão em ./backend/logistica-api/deploy

API | Elemento | Comando
----|--------- | ------
logistica-api | Deployment | ```kubectl create -f deployment.yaml``` 
logistica-api | Service | ```kubectl create -f service.yaml``` 


<br>

> **Acessando o Kubernetes Dashboard**

Abra o cmd e digite o comando ```kubectl proxy``` <br>
O comando cria um proxy para acessar a API do Kubernetes, incluindo o
dashboard. <br>
Importante: Esse comando abre um caminho apenas para acessar os serviços do próprio Kubernetes, mas não as aplicações. <br>

O dashboard agora está disponível para ser acessado na URL:<br>
http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/#/login

O acesso será via Token.<br>
Utilize o comando para gerar o token de acesso.<br>

```kubectl -n kubernetes-dashboard create token logistica-admin```

#
### Postman


o arquivo **Logistica.postman_collection.json** é uma collection do Postman que possui as chamadas para os serviços da aplicação. <br>
A collection está configurada para chamar os serviços já no Kubernetes. <br>
Para chamar na execução local, basta trocar o nome de **localhost** para **localhost:808X**.

<br><br>

[^1]: API:<br>
Algaworks
[^2]: Docker:<br>
Mysql docker: https://hub.docker.com/_/mysql<br>
Livro Back-end Java - Microsserviços, Spring Boot e Kubernetes de Eduardo Felipe Zambom Santana - 2021, Casa do Código / Alura<br>
[^3]: Kubernetes:<br>
Autenticação no Kubernetes: https://kubernetes.io/docs/reference/access-authn-authz/rbac/ <br>
Service no Kubernetes: https://kubernetes.io/docs/concepts/services-networking/service/ <br>
Ingress no Kubernetes: https://kubernetes.io/docs/concepts/services-networking/ingress/ <br>
Token: https://github.com/kubernetes/dashboard/blob/master/docs/user/access-control/creating-sample-user.md<br>
NGINX: https://docs.nginx.com/nginx-ingress-controller/configuration/ingress-resources/basic-configuration


