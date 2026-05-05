# Contact API Spec

## Create Contact
Endpoint : POST /api/contact 

Request Header : 
- X-API-TOKEN = Token (Mandatory)

Request Body :
```json
{
  "firstName" : "Eko",
  "lastName" : "Bambang",
  "email" : "bambang@example.com",
  "phone" : "080808080"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Eko",
    "lastName": "Bambang",
    "email": "bambang@example.com",
    "phone": "080808080"
  }
}
```
Response Body (Failed) : 
```json
{
  "errors" : "Email format invalid, phone format invalid.."
}
```

## Update Contact
Endpoint : PUT /api/contacts/{idContact}

Request Header : 
- X-API-TOKEN = Token (Mandatory)

Request Body :
```json
{
  "firstName" : "Eko",
  "lastName" : "Bambang",
  "email" : "bambang@example.com",
  "phone" : "080808080"
}
```

Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Eko",
    "lastName": "Bambang",
    "email": "bambang@example.com",
    "phone": "080808080"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Contact not found"
}
```


## Get Contact
Endpoint : GET /api/contacts/{idContact}


Request Header : 
- X-API-TOKEN = Token (Mandatory)

Response Body (Success) :
```json
{
  "data": {
    "id": "random-string",
    "firstName": "Eko",
    "lastName": "Bambang",
    "email": "bambang@example.com",
    "phone": "080808080"
  }
}
```

Response Body (Failed) :
```json
{
  "errors" : "Contact is not Found"
}
```

## Search Contact
Endpoint : GET /api/contacts

Query Params :
- name : String contact fisrtName or contact last name, using like query , optional
- phone : String, contact phone, using like query , optional 
- email : String contact email, using like query , optional
- page : Integer, start from 0, default 0
- size : Integer, default 10

Request Header : 
- X-API-TOKEN = Token (Mandatory)

Response Body (Success) :
```json
{
  "data": [
    {
      "id": "random-string",
      "firstName": "Eko",
      "lastName": "Bambang",
      "email": "bambang@example.com",
      "phone": "080808080"
    }
  ],
  "paging": {
    "currentPage" : 0,
    "totalPage": 10,
    "size": 10
  }
}
```

Response Body (Failed) :
## Remove Contact
Endpoint : DELETE /api/contacts/{idContact}

Request Header : 
- X-API-TOKEN = Token (Mandatory)

Response Body (Success) :
```json
{
  "data" : "OK"
}
```

Response Body (Failed) : 
```json
{
  "errors" : "Contact not found"
}
```
