```mermaid
---
title: Ticketing ERD
---
erDiagram
    user {
        bigint id PK
        string user_uuid
        datetime created_at
    }

    QUEUE_TOKEN {
        bigint id PK
        bigint user_id FK
        string jti_hash
        string status
        int position_hint
        int eta_seconds
        datetime issued_at
        datetime activated_at
        datetime expires_at
        datetime last_seen_at
    }

    concert {
        bigint id PK
        string title
        string status
        datetime created_at
    }

    concert_date {
        bigint id PK
        bigint concert_id FK
        datetime starts_at
        datetime ends_at
        boolean is_open
        datetime created_at
    }

    seat {
        bigint id PK
        bigint concert_date_id FK
        bigint user_id FK
        tinyint seat_no
        tinyint state
        datetime expires_at
        bigint hold_by_token_id
        int version
    }

    reservation {
        bigint id PK
        string user_uuid
        bigint queue_token_id FK
        bigint concert_date_id FK
        bigint seat_inventory_id FK
        tinyint seat_no
        tinyint status
        datetime expires_at
        datetime created_at
    }

    wallet {
        string user_uuid PK
        decimal balance
        int version
    }

    wallet_ledger {
        bigint id PK
        string user_uuid
        tinyint type
        decimal amount_signed
        decimal balance_after
        string ref_type
        bigint ref_id
        string idempotency_key
        datetime created_at
    }

    payment {
        bigint id PK
        string user_uuid
        bigint reservation_id FK
        decimal amount
        tinyint status
        datetime created_at
        datetime completed_at
        string idempotency_key
    }

    ticket {
        bigint id PK
        string user_uuid
        bigint concert_date_id FK
        bigint seat_inventory_id FK
        tinyint seat_no
        datetime issued_at
    }

%% 관계
    user ||--o{ QUEUE_TOKEN: ""
    concert ||--|{ concert_date: ""
    concert_date ||--|{ seat: ""
    seat ||--o{ reservation: ""
    QUEUE_TOKEN ||--o{ reservation: ""
    concert_date ||--o{ reservation: ""
    reservation ||--o{ payment: ""
    seat ||--|| ticket: ""
    concert_date ||--o{ ticket: ""
    user ||--|| wallet: ""
    user ||--o{ wallet_ledger: ""
    user ||--o{ reservation: ""
    user ||--o{ payment: ""
    user ||--o{ ticket: ""

```