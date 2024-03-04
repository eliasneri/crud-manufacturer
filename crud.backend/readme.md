### Api-Manufacture

Esta API utiliza a versão 17 do Java, e está implementada para utilização de banco de dados PostgreSQL.

Para executar apenas a API

Certifique de tenha instalado o docker-compose em seu terminal.

```docker
$ docker-compose -version
docker-compose version 1.29.2, build unknown
```
Copie o conteúdo abaixo em um arquivo chamado docker-compose.yml
```docker
version: '3.8'

services:
  postgres:
    image: postgres:latest
    restart: no
    environment:
      POSTGRES_DB: crud-nec
      POSTGRES_USER: root
      POSTGRES_PASSWORD: 12345678
    ports:
      - "5432:5432"

  crud-backend:
    image: eliasneri/crud-backend-java17:latest
    restart: no
    ports:
      - "9598:9598"
    depends_on:
      - postgres
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 512M
```
Após salvar o arquivo conforme especificado, digite:
```bash
$ docker-compose up -d
```

Isto irá baixar as imagens necessárias para o funcionamento da API, bem como o container do banco de dados Postgres.


### Swagger: 
http://localhost:9598/manufacture/swagger-ui/index.html

### Coleção de Requisições (postman)
```json
{
  "info": {
    "_postman_id": "a5def83d-9fca-4cea-a28c-e6673d9c554d",
    "name": "NEC -Manufacturer",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
    "_exporter_id": "12415548"
  },
  "item": [
    {
      "name": "FindAll",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "localhost:9598/manufacture/find/all",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "find",
            "all"
          ]
        }
      },
      "response": []
    },
    {
      "name": "FindByID",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "localhost:9598/manufacture/find/2",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "find",
            "2"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Create",
      "request": {
        "method": "POST",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n  \"manufacturerName\": \"Empresa One LTDA.\",\r\n  \"manufacturerCNPJ\": \"12.345.678/0001-90\",\r\n  \"manufacturerFantasyName\": \"One Empresa de tudo um pouco\",\r\n  \"manufacturerSocialName\": \"One\",\r\n  \"manufacturerActive\": \"true\",\r\n  \"manufacturerSite\": \"www.one.com\",\r\n  \"manufacturerCountry\": \"brasil\",\r\n  \"manufacturerCity\": \"Americana\",\r\n  \"manufactureNeighbourhood\": \"centro\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "localhost:9598/manufacture/create",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "create"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Update",
      "request": {
        "method": "PUT",
        "header": [],
        "body": {
          "mode": "raw",
          "raw": "{\r\n  \"manufacturerName\": \"Empresa OneToOne LTDA Corp.\",\r\n  \"manufacturerCNPJ\": \"12.345.678/0001-90\",\r\n  \"manufacturerFantasyName\": \"One Empresa de tudo um pouco\",\r\n  \"manufacturerSocialName\": \"One\",\r\n  \"manufacturerActive\": \"true\",\r\n  \"manufacturerSite\": \"www.one.com\",\r\n  \"manufacturerCountry\": \"brasil\",\r\n  \"manufacturerCity\": \"Americana\",\r\n  \"manufactureNeighbourhood\": \"centro\"\r\n}",
          "options": {
            "raw": {
              "language": "json"
            }
          }
        },
        "url": {
          "raw": "localhost:9598/manufacture/update/1",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "update",
            "1"
          ]
        }
      },
      "response": []
    },
    {
      "name": "Delete",
      "request": {
        "method": "DELETE",
        "header": [],
        "url": {
          "raw": "localhost:9598/manufacture/delete/1",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "delete",
            "1"
          ]
        }
      },
      "response": []
    },
    {
      "name": "FindAll - Pageable",
      "request": {
        "method": "GET",
        "header": [],
        "url": {
          "raw": "localhost:9598/manufacture/find/allpages?page=0&size=20&sort=manufacturerName,asc",
          "host": [
            "localhost"
          ],
          "port": "9598",
          "path": [
            "manufacture",
            "find",
            "allpages"
          ],
          "query": [
            {
              "key": "page",
              "value": "0size=20"
            },
            {
              "key": "sort",
              "value": "manufacturerName,asc"
            }
          ]
        }
      },
      "response": []
    }
  ]
}
```
#### Dica: salve este conteúdo em um arquivo .json e importe como coleção no Postman.


Para demonstração os scripts SQL para uma possível conferência, uma vez que utilizando recursos de ORM, neste projeto o JPA, ele já fonece as queries básicas que foram utilizadas, exceto para buscar por CNPJ. <br/>
No entanto para fins complementares deixarei-os expostos. Neste caso o script se aplica a banco de dados POSTGRESQL.
```script
-- Create Database
create database crud_nec

-- Create Table manufacturer_base
create table manufacturer_base (
	manufacture_id serial4,
	manufacturer_name varchar(80),
	manufacturer_cnpj varchar(18),
	manufacturer_fantasy_name varchar(100),
	manufacturer_social_name varchar(80),
	manufacturer_active bool,
	manufacturer_site varchar(40),
	manufacturer_country varchar(40),
	manufacturer_city varchar(30),
	manufacturer_bairro varchar(30)
);

insert into manufacturer_base
	(manufacturer_name,	
	manufacturer_cnpj,
	manufacturer_fantasy_name,
	manufacturer_social_name, 
	manufacturer_active, 
	manufacturer_site, 
	manufacturer_country,
	manufacturer_city, 
	manufacturer_bairro)
values 
	('Empresa One LTDA.',
	REPLACE(REGEXP_REPLACE('12.345.678/0001-92', '[^0-9]', '', 'g'), ' ', ''),
	'One Empresa de tudo um pouco',
	'One',
	'true',
	'www.one.com',
	'brasil',
	'Americana',
	'Centro')
ON CONFLICT (manufacturer_cnpj) DO NOTHING;
	



-- FIND ALL
select
	manufacture_id,
	manufacturer_name,
	manufacturer_cnpj,
	manufacturer_fantasy_name,
	manufacturer_social_name,
	manufacturer_active,
	manufacturer_site,
	manufacturer_country,
	manufacturer_city,
	manufacturer_bairro
from manufacturer_base

-- FIND BY ID
select
	manufacture_id
	manufacturer_name,
	manufacturer_cnpj,
	manufacturer_fantasy_name,
	manufacturer_social_name,
	manufacturer_active,
	manufacturer_site,
	manufacturer_country,
	manufacturer_city,
	manufacturer_bairro
from manufacturer_base
where manufacture_id = 1

-- UPDATE
update manufacturer_base set 
	manufacturer_name = 'Empresa OneToOne LTDA Corp.',
	manufacturer_cnpj = REPLACE(REGEXP_REPLACE('12.345.678/0001-92', '[^0-9]', '', 'g'), ' ', ''),
	manufacturer_fantasy_name = 'One Empresa de tudo um pouco',
	manufacturer_social_name = 'One',
	manufacturer_active = true, 
	manufacturer_site = 'www.one.com',
	manufacturer_country = 'brasil',
	manufacturer_city = 'americana',
	manufacturer_bairro = 'centro'
where manufacture_id = 1

-- DELETE 
delete from manufacturer_base
where manufacture_id = 1;


-- FIND CNPJ EXISTS (COMPLEMENTS)
SELECT CASE WHEN COUNT(*) > 0 THEN 
   true 
ELSE 
	false 
END 
FROM manufacturer_base 
WHERE manufacturer_cnpj = REPLACE(REGEXP_REPLACE('12.345.678/0001-92', '[^0-9]', '', 'g'), ' ', '');
```

Enjoy!

Elias Neri - 02/2024