package kr.hhplus.be.server.user.domain.auth.core.dto;

public record ReissueResponse(
        Access access,
        Refresh refresh
) {

    public static ReissueResponse of(TokenPair tokenPair) {
        return new ReissueResponse(
                new Access(tokenPair.access().token(), tokenPair.access().expiredAt()),
                new Refresh(tokenPair.refresh().token(), tokenPair.refresh().expiredAt())
        );
    }
}
