package kr.hhplus.be.server.user.domain.auth.core.port.in;

import kr.hhplus.be.server.user.domain.auth.core.dto.ReissueResponse;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.ReIssueTokenCommand;

public interface ReIssueUseCase {
    ReissueResponse reissue(ReIssueTokenCommand command);
}
