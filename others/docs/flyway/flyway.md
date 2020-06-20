# [Flyway - Versionamento da Base de Dados](https://flywaydb.org/)

Para controlar o versionamento da base de dados iremos utilizar o Flyway, que realiza a aplicação dos scripts evolutivos automaticamente.

A versão utilizada é a [6.4.4](https://github.com/flyway/flyway/releases/tag/flyway-6.4.4)

Versões recentes são compatíveis com a última versão do Oracle e só rodam em versões anteriores com a opção Paga.

## Como deverão ser postados os scripts - Local Scripts

**!Atenção!**: Os arquivos SQL devem estar com encoding UTF-8. Não é um comando SQL é o encoding do arquivo.

Os scripts serão gerenciados via GIT no mesmo repositório de código do SAN.
A estrutura atual fica em:

`.\src\main\resources\db.migrations`

## File Patterns:

**!Atenção!**: Os arquivos SQL devem estar com encoding UTF-8. Não é um comando SQL é o encoding do arquivo.

Deve ser seguido a convenção global de arquivos com 
![](versioning.png)

**Prefix:** V for versioned [(configurable)](https://flywaydb.org/documentation/commandline/migrate#sqlMigrationPrefix), U for undo (configurable) and R for repeatable migrations (configurable)

**Version:** Version with dots or underscores separate as many parts as you like (Not for repeatable migrations) must follow 2013.01.15.11.35.56 (year, month, day, hour, min e sec)

**Separator:** \_\_ (two underscores)(configurable)

**Description:** Underscores or spaces separate the words, maybe together with issue number

**Suffix:** .sql (configurable)

## Conceito de "idempotent sqls"

Todos os scripts de evolução devem implementarem o conceito de ?idempotent?:
"An idempotent function gives the same result even if it is applied several times."

Quando ele pode ser executado sempre sem prejuízo ou blocks. 
Na pratica um script sempre realizara alguma verificação antes de executar, e caso a verificação falhe (ex: tabela já existe), o mesmo não executa. 
Dessa forma o flyway faz o controle que a instrução rodou.

No exemplo abaixo, o script verifica se a tabela não existe, e apenas irá criar caso a condição seja atendida.

```sql
DECLARE v_exist NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_exist FROM user_tables WHERE table_name = 'TESTE_FLYWAY';
    IF v_exist = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE TESTE_FLYWAY (
            ID NUMBER(38) NOT NULL,
            DESCR VARCHAR2(80) NOT NULL,
            CONSTRAINT TESTE_FLYWAY_PK PRIMARY KEY(ID) ENABLE
        )';
    END IF;
END;
```

Pode ser utilizados outros mecanismos como "where conditions", "select for insert", etc.

## Quebras de arquivos

É sugerido que os arquivos sejam curtos e com apenas um contexto de alterações.
Ou seja, se um chamado tem várias sqls de contextos diferentes, deve ser quebrado em diferentes arquivos.

## Sempre evolutivo

Um Arquivo NUNCA pode ser editado (exceto caso de erros de script que impeçam totalmente a execução).
Após criado e executado com sucesso na base, caso seja necessário alterar ou desfazer uma ação, deve ser incluído um novo arquivo na sequência.

## Do not use especific database features

Make it independent of platform