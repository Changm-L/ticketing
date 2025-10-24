# API Documentation

## 공통 응답

### Data가 없는 응답

```json
{
  "error": false,
  "message": null
}
```

### Single Result

```json
{
  "error": false,
  "message": null,
  "data": {
    "foo": "bar"
  }
}
```

### List Result

```json
{
  "error": false,
  "message": null,
  "data": [
    {
      "foo": "bar"
    },
    {
      "bar": "foo"
    }
  ]
}
```

# 사용자

## 유저 API

### 토큰 발급 API

- `POST /v1/userJpaEntity/generate-token`
- response

```json 
{
  "error": false,
  "message": null,
  "data": {
    "user_id": 1,
    "token": "some_your_token_here"
  }
}
```

## 콘서트 API

- `GET /v1/concerts`
- require
  - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": [
    {
      "concertId": 1,
      "title": "콘서트 이름",
      "address": "안국역 4번 출구",
      "availableSeatCount": 50,
      "date": "2025-08-28"
    },
    {
      "concertId": 2,
      "title": "두번째 콘서트 이름",
      "address": "안국역 4번 출구",
      "availableSeatCount": 50,
      "date": "2025-08-30"
    }
  ]
}
```

## 좌석 API

### 예약 가능 좌석 조회

- `GET /v1/{concert_id}/seat`
- require
    - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": [
    {
      "concert_id": 1,
      "concert_date": "2025-08-27",
      "seat_id": 1,
      "seat_no": 1
    },
    ...
    {
      "concert_id": 1,
      "concert_date": "2025-08-27",
      "seat_id": 50,
      "seat_no": 50
    }
  ]
}
```
## 예약 API

### 예약 대기

- `POST /v1/{concert_id}/seat`
- require
  - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": 1
}
```

## 지갑 API

### 잔액 조회

- `GET /v1/walletJpaEntity`
- require
    - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": {
    "balance": 10000.00
  }
}
```

### 충전

- `POST /v1/walletJpaEntity/charge`
- require
    - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": 1
}
```
  
## 결제 API

### 결제

- `POST /v1/payment`
- require
    - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": 1
}
```

# 관리자

## 콘서트 API

### 등록

- `POST /v1/admin/concertJpaEntity`
- require
    - Authorization Header

### 목록 조회

- `GET /v1/admin/concerts`
- require
  - Authorization Header
- response

```json
{
  "error": false,
  "message": null,
  "data": [
    {
      "concertId": 1,
      "title": "콘서트 이름",
      "address": "안국역 4번 출구",
      "availableSeatCount": 50,
      "date": "2025-08-30",
      "status": "ACTIVE"
    },
    {
      "concertId": 2,
      "title": "두번째 콘서트 이름",
      "address": "안국역 4번 출구",
      "availableSeatCount": 50,
      "date": "2025-08-30",
      "status": "ACTIVE"
    }
  ]
}
```

### 상세 조회

- `POST /v1/admin/concertJpaEntity/{concert_id}`
- require
    - Authorization Header

## 지갑 히스토리 조회

- `GET /v1/admin/walletJpaEntity/{user_id}`
- require
    - Authorization Header
