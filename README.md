# Di2win-desafio

criando conta (POST):
URI:http://localhost:8080/creatAccount
BODY:

primeira conta:
      {
        "name": "Teste1",
        "balance": 100.00,
        "birth": "2000-09-09T00:00:00",
        "document": "11450434452",
        "account": 123,
        "agency": 123,
        "accountType": "active"
    }
    
segunda conta:
    {
        "name": "Teste2",
        "balance": 100.00,
        "birth": "2000-09-09T00:00:00",
        "document": "65602501487",
        "account": 321,
        "agency": 123,
        "accountType": "active"
    }
    
realizando deposito (POST): 
URI:http://localhost:8080/transaction/deposito
BODY:
  {
      "value": 20,
      "reciverId": 1
  }
  
realizando saque (POST): 
URI:http://localhost:8080/transaction/deposito
BODY:
  {
      "value": 20,
      "reciverId": 1
  }
  
criando transação (POST): 
URI:http://localhost:8080/transaction/createTransaction
BODY:
  {
      "value": 10,
      "senderId": 2, 
      "reciverId": 1
  }
  
realizando consulta na conta (GET): 
URI:localhost:8080/checkAccount
BODY:
  {
      "document": 11450434452
  }

Bloqueio de conta (GET): 
URI:http://localhost:8080/blockAccount
BODY:
  {
      "document": 11450434452
  }

- Criar Tarefa 
```
$ http POST :8080/todos nome="Todo 1" descricao="Desc Todo 1" prioridade=1

[
  {
    "descricao": "Desc Todo 1",
    "id": 1,
    "nome": "Todo 1",
    "prioridade": 1,
    "realizado": false
  }
]
```
