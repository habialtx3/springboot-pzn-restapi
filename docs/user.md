# User Api Spec

## Register UserEndpoint : POST /api/users
Request Body :

```json
{
  "username": "",
  "password" : "",
  "name" : ""
}
```
Response Body : 
```json
{
  "data" : "OK"
}
```

Response Body (Failed)
```json
{
  "errors": "Username must not blank, ???"
}
```

## Login UserEndpoint : POST /api/auth/login

Request Body :

```json
{
  "username": "",
  "password" : ""
}
```

Response Body :
```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : "23232323"
  }
}
```

Response Body (Failed)
```json
{
  "errors": "Username must not blank, ???"
}
```

## Get User
Endpoint : GET /api/users/curent

Request Header : 
- X-API-TOKEN = Token (Mandatory)

Response Body :
```json
{
  "data" : {
    "username" : "bambang",
    "name"  : "bambang alter"
  }
}
```

Response Body (Failed)
```json
{
  "errors": "Unauthorized"
}
```

## Update User
Endpoint : PATCH /api/users/curent

Request Header :
- X-API-TOKEN = Token (Mandatory)

Request Body :
```json
{
  "name" : "Bambang Nig" ,
  "password" : "new password"
}
```

Response Body :
```json
{
  "data" : {
    "username" : "bambang",
    "name"  : "bambang alter"
  }
}
```

Response Body (Failed)
```json
{
  "errors": "Unauthorized"
}
```


## Logout User
Endpoint : DELETE /api/auth/logout

Request Header :
- X-API-TOKEN = Token (Mandatory)

Response Body (Success) : 
```json
{
  "data" : "OK"
}
```