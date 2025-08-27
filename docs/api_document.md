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

- `POST /v1/user/generate-token`

## 좌석 API

### 예약 가능 좌석 조회

- `GET /v1/{concert_id}/seat`

## 예약 API

### 예약 대기

- `POST /v1/{concert_id}/seat`

## 지갑 API

### 잔액 조회

- `GET /v1/wallet`

### 충전

- `POST /v1/wallet/charge`

## 결제 API

### 결제

- `POST /v1/payment`

# 관리자

## 콘서트 API

### 등록

- `POST /v1/admin/concert`

### 상세 조회

- `POST /v1/admin/concert/{concert_id}`

## 지갑 히스토리 조회

- `GET /v1/admin/wallet/{user_id}`
