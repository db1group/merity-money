# Rodando com Docker

Para criar a imagem docker é necessário que o Docker esteja instalado e autenticado.

Criar a imagem docker rodar:
```
./mvnw verify jib:dockerBuild -DskipTests
```

Para rodar:
```
docker-compose -f others/docker/app.yml up -d
```

> jib/entrypoint.sh

Caso tenha o erro "bash: /entrypoint.sh: /bin/sh^M: bad interpreter: No such file or directory" 

Deve-se rodar no gitbash na pasta em que o arquivo entrypoint.sh está o comando abaixo
```    
sed -i -e 's/\r$//' entrypoint.sh
```

Isto é necessário porque o arquivo tem que estar no formato do Linux.

Após ajustar o arquivo criar a imagem novamente.

## Enviando a imagem para o repositório

Por enquanto está sendo utilizado um repositório privado para a imagem, criado no Docker Hub.

``` 
docker tag telecom:latest [NOME_TAG_DOCKER_HUB]

docker push [NOME_TAG_DOCKER_HUB]
``` 

Com isso a imagem irá estar disponível para implantação.

Puxa a imagem do repositório remoto para a máquina local:
``` 
docker pull [NOME_TAG_DOCKER_HUB]
``` 
Inicia o container para a imagem, inicia a aplicação de fato:
``` 
docker container run -it --name meritmoney01 -p 127.0.0.1:8081:8081 -e "SPRING_PROFILES_ACTIVE=devdocker" -v /logs/externalLogs/:/logs -d [NOME_TAG_DOCKER_HUB]
```
Exemplo:

``` 
docker container run -it --name meritmoney01 -p 127.0.0.1:8081:8081 -e "SPRING_PROFILES_ACTIVE=dev" -v /logs/externalLogs/:/logs -d meritmoney:latest 
``` 
Para validar se o container está rodando e pegar o nome do container, execute:
``` 
docker ps
``` 
Comando para parar a aplicação
``` 
docker stop meritmoney01
``` 
Comando para remover o container
``` 
docker rm meritmoney01
``` 

Comandos:
-p: Faz o bind para a porta local com a porta do servidor no Docker.
-d: Executa o container em background
--name: Nomeia o container, não é ideal quando subir em escala

## Subir para a DockerHub

https://docs.docker.com/docker-hub/publish/publish/

Criar a tag DockerHub com base na TAG criada usando o commando do maven
``` 
docker tag meritmoney:latest SEU_DOCKER_ID/meritmoney:latest
docker tag meritmoney:latest SEU_DOCKER_ID/meritmoney:latest
``` 

Enviar para a DockerHUb
``` 
docker push SEU_DOCKER_ID/meritmoney:latest
``` 

Na hora de executar a imagem

|Name|Value|
|----|-----|
|SPRING_PROFILES_ACTIVE|dev|
|USER_TIMEZONE|GMT-03|
|APP_START_SLEEP|5|
|WEBSITE_TIME_ZONE|E. South America Standard Time|
